package id.nithium.libraries.orionlib;

import id.nithium.libraries.orionlib.query.QuerySet;
import lombok.Cleanup;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ObjectUpdater<A> {

    private final OrionLib orionLib = OrionLib.getInstance();
    private final Connection connection;
    private final String tableName;
    private final A obj;

    private final Field[] allFields;
    private String query;
    private List<Field> existsFieldsInRow = new ArrayList<>();

    public ObjectUpdater(Connection connection, String tableName, A obj) {
        this.connection = connection;
        this.tableName = tableName;
        this.obj = obj;

        allFields = obj.getClass().getDeclaredFields();
        orionLib.debug("Start checking rows... (May take awhile)");
        startCheck();
    }

    public void startCheck() {
        StringBuilder stringBuilder = new StringBuilder("select ");
        for (Field field : allFields) {
            stringBuilder.append(field.getName()).append(", ");
        }
        for (int i = 0; i < 2; i++) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        stringBuilder.append(" from ").append(tableName);

        try (PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString())) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                for (Field field : allFields) {
                    Object object = resultSet.getObject(field.getName());
                    existsFieldsInRow.add(field);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        query = stringBuilder.toString();
        orionLib.debug("Done checking!");
    }

    public void setup() {
        List<Object> fields = new ArrayList<>();
        for (Field field : obj.getClass().getDeclaredFields()) {
            assert field.getName().startsWith("get");

            fields.add(field);
        }

        if (new QuerySet(connection, query, fields).next()) {

        }
    }
}
