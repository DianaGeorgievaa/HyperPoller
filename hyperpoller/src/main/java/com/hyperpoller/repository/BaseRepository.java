package com.hyperpoller.repository;

import com.hyperpoller.configuration.utils.DatabaseConfigurationConstants;
import com.hyperpoller.database.DatabaseConnection;
import com.hyperpoller.entity.BaseEntity;
import com.hyperpoller.entity.utils.DatabaseTablesConstants;

import java.lang.reflect.Field;
import java.sql.*;

public class BaseRepository implements Repository<BaseEntity> {
    private int id;
    private DatabaseConnection databaseConnection;

    public BaseRepository() {
        databaseConnection = new DatabaseConnection();
    }

    @Override
    public void insert(BaseEntity entity) throws IllegalAccessException, SQLException, ClassNotFoundException {
        if (!isExistingEntity(entity)) {
            StringBuilder fields = new StringBuilder();
            StringBuilder variables = new StringBuilder();
            Connection connection = getConnection();

            Field[] declaredFields = getFields(entity);
            for (Field currentField : declaredFields) {
                String name = currentField.getName();
                if (fields.length() > 1) {
                    fields.append(",");
                    variables.append(",");
                }
                fields.append(name);
                variables.append("?");
            }

            String tableName = DatabaseConfigurationConstants.DATABASE_NAME + entity.getTableName();
            PreparedStatement statement = connection.prepareStatement(getQueryForInsertOperation(tableName, fields, variables),
                    Statement.RETURN_GENERATED_KEYS);
            setObjects(declaredFields, entity, statement);
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();

            setIdAfterInsert(result);
            connection.close();
        } else {
            id = selectId(entity);
        }

        entity.setId(id);
    }

    private boolean isExistingEntity(BaseEntity entity) throws
            SQLException, ClassNotFoundException, IllegalAccessException {
        Connection connection = getConnection();
        Field[] declaredFields = getFields(entity);
        StringBuilder fields = getFieldsConcatenated(declaredFields);

        int length = getLength(declaredFields, entity);
        StringBuilder whereClause = getWhereClause(length, declaredFields, entity);

        ResultSet result;
        try (Statement statement = connection.createStatement()) {
            result = statement.executeQuery(getQueryForSelectOperation(fields,
                    DatabaseConfigurationConstants.DATABASE_NAME + entity.getTableName(), whereClause));
        }

        return result.next();
    }

    private int selectId(BaseEntity entity) throws
            ClassNotFoundException, SQLException, IllegalAccessException {
        Connection connection = getConnection();
        Field[] declaredFields = getFields(entity);
        int length = getLength(declaredFields, entity);

        StringBuilder whereClause = getWhereClause(length, declaredFields, entity);
        String selectQuery = "SELECT ID FROM " + DatabaseConfigurationConstants.DATABASE_NAME + entity.getTableName()
                + " WHERE " + whereClause;
        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(selectQuery)) {
            result.first();
            id = result.getInt("id");
        }

        return id;
    }

    private Field[] getFields(BaseEntity entity) throws ClassNotFoundException {
        Class className = Class.forName(DatabaseConfigurationConstants.PATH_TO_ENTITY_PACKAGE
                + entity.getClass().getSimpleName());

        return className.getDeclaredFields();
    }

    private void setObjects(Field[] declaredFields, BaseEntity entity, PreparedStatement statement) throws SQLException, IllegalAccessException {
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            field.setAccessible(true);
            Object value = field.get(entity);
            statement.setObject((i + 1), value);
        }
    }

    private String getQueryForInsertOperation(String tableName, StringBuilder fields, StringBuilder variables) {
        return "INSERT INTO " + tableName + "(" + fields.toString() + ") VALUES(" + variables.toString() + ")";
    }

    private void setIdAfterInsert(ResultSet result) throws SQLException {
        if (result.next()) {
            id = result.getInt(1);
        }
    }

    private String getQueryForSelectOperation(StringBuilder fields, String tableName, StringBuilder whereClause) {
        return "SELECT " + fields.toString() + " FROM " + tableName + " WHERE " + whereClause;
    }

    private StringBuilder getWhereClause(int length, Field[] declaredFields, BaseEntity entity) throws IllegalAccessException {
        StringBuilder whereClause = new StringBuilder();
        for (int i = 0; i < length; i++) {
            Field field = declaredFields[i];
            field.setAccessible(true);
            Object value = field.get(entity);
            whereClause.append(declaredFields[i].getName()).append("=").append("'").append(value).append("'");

            if (i != length - 1) {
                whereClause.append(" AND ");
            }
        }

        return whereClause;
    }

    private int getLength(Field[] declaredFields, BaseEntity entity) {
        int length = declaredFields.length;
        String table = entity.getTableName();
        if (table.equals(DatabaseTablesConstants.CARD_DETAILS_TABLE_NAME)) {
            length -= 1;
        }

        return length;
    }

    private StringBuilder getFieldsConcatenated(Field[] declaredFields) {
        StringBuilder fields = new StringBuilder();
        for (Field currentField : declaredFields) {
            String name = currentField.getName();
            if (fields.length() > 1) {
                fields.append(", ");
            }
            fields.append(name);
        }

        return fields;
    }

    private Connection getConnection() {
        return databaseConnection.getConnection();
    }
}
