package com.primeholding.hyperpoller.repositories;

import com.primeholding.hyperpoller.database.DatabaseConnection;
import com.primeholding.hyperpoller.entities.BaseEntity;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;

public class BaseRepository implements Repository<BaseEntity> {
    private static final String DATABASE_NAME = "hyperpoller.";
    private static final String PATH_TO_ENTITY_PACKAGE = "com.primeholding.hyperpoller.entity.";

    private int id;
    private DatabaseConnection databaseConnection;

    public BaseRepository() throws IOException {
        databaseConnection = new DatabaseConnection();
    }

    @Override
    public void insert(BaseEntity entity) throws JAXBException, IOException, ClassNotFoundException, SQLException, IllegalAccessException {
        if (!isExistingEntity(entity)) {
            StringBuilder fields = new StringBuilder();
            StringBuilder variables = new StringBuilder();
            Connection connection = getConnection();

            Field[] declaredFields = getFields(entity);
            String name;
            for (Field currentField : declaredFields) {
                name = currentField.getName();
                if (fields.length() > 1) {
                    fields.append(",");
                    variables.append(",");
                }
                fields.append(name);
                variables.append("?");
            }

            PreparedStatement statement = connection.prepareStatement(getQueryForInsertOperation(DATABASE_NAME + entity.getTableName(), fields, variables), Statement.RETURN_GENERATED_KEYS);
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

    public int getId() {
        return id;
    }

    private boolean isExistingEntity(BaseEntity entity) throws
            SQLException, IOException, ClassNotFoundException, IllegalAccessException {
        Connection connection = getConnection();
        Field[] declaredFields = getFields(entity);
        StringBuilder fields = getFieldsConcatenated(declaredFields);

        int length = getLength(declaredFields, entity);
        StringBuilder whereClause = getWhereClause(length, declaredFields, entity);
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(getQueryForSelectOperation(fields, DATABASE_NAME + entity.getTableName(), whereClause));

        if (!result.next()) {
            connection.close();
            return false;
        }
        connection.close();

        return true;
    }

    private int selectId(BaseEntity entity) throws
            IOException, ClassNotFoundException, SQLException, IllegalAccessException {
        Connection connection = getConnection();
        Field[] declaredFields = getFields(entity);
        int length = getLength(declaredFields, entity);

        StringBuilder whereClause = getWhereClause(length, declaredFields, entity);
        String selectQuery = "SELECT ID FROM " + DATABASE_NAME + entity.getTableName() + " WHERE " + whereClause;
        Statement statement = connection.createStatement();

        int id;
        try (ResultSet result = statement.executeQuery(selectQuery)) {
            result.first();
            id = result.getInt("id");
        }
        connection.close();

        return id;
    }

    private Field[] getFields(BaseEntity entity) throws ClassNotFoundException {
        Class className = Class.forName(PATH_TO_ENTITY_PACKAGE + entity.getClass().getSimpleName());

        return className.getDeclaredFields();
    }

    private void setObjects(Field[] declaredFields, BaseEntity entity, PreparedStatement statement) throws SQLException, IllegalAccessException {
        Object value;
        Field field;
        for (int i = 0; i < declaredFields.length; i++) {
            field = declaredFields[i];
            field.setAccessible(true);
            value = field.get(entity);
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
        Field field;
        StringBuilder whereClause = new StringBuilder();
        for (int i = 0; i < length; i++) {
            field = declaredFields[i];
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
        if (table.equals("carddetails")) {
            length -= 1;
        }

        return length;
    }

    private StringBuilder getFieldsConcatenated(Field[] declaredFields) {
        StringBuilder fields = new StringBuilder();
        String name;
        for (Field currentField : declaredFields) {
            name = currentField.getName();
            if (fields.length() > 1) {
                fields.append(", ");
            }
            fields.append(name);
        }

        return fields;
    }

    private Connection getConnection() throws IOException, ClassNotFoundException {
        return databaseConnection.getConnection();
    }
}
