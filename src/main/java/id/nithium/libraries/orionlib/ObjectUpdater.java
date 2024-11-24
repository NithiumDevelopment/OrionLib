package id.nithium.libraries.orionlib;

import id.nithium.libraries.orionlib.query.QueryGet;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ObjectUpdater<A> {

    private final OrionLib orionLib = OrionLib.getInstance();
    private final Connection connection;
    private final String tableName;
    private final A obj;

    private final Field[] allFields;
    private final Object primary;

    private final List<Field> existFields = new ArrayList<>();

    public ObjectUpdater(Connection connection, String tableName, A obj, Object primary) throws SQLException {
        this.connection = connection;
        this.tableName = tableName;
        this.obj = obj;
        this.primary = primary;

        allFields = obj.getClass().getDeclaredFields();
        System.out.println("Start checking rows... (May take awhile)");
        startCheck();
        setup();
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

                    field.setAccessible(true);
                    Object object1 = field.get(obj);
                    if (object != null) {
                        if (!object1.toString().equals(object.toString())) {
                            existFields.add(field);
                        }
                    }
                }
            }
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }

        System.out.println(stringBuilder);
        System.out.println("Exist field: " + existFields.toString());
        orionLib.debug("Done checking!");
    }

    public void setup() {
        List<Object> objects = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder("update " + tableName);
        for (Field field : existFields) {
            field.setAccessible(true);
            try {
                Object object = field.get(obj);
                objects.add(object);
                stringBuilder.append(" set ").append(field.getName()).append(" = ?, ");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        for (int i = 0; i < 2; i++) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }

        String primaryName = "";
        for (Field field : allFields) {
            field.setAccessible(true);

            try {
                Object object = field.get(obj);
                if (object.toString().equals(primary.toString())) {
                    primaryName = field.getName();
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        }

        stringBuilder.append(" where ").append(primaryName).append(" = ?");

        System.out.println(stringBuilder.toString());
        try (PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString())) {
            for (int i = 0; i < objects.size(); i++) {
                preparedStatement.setObject((i + 1), objects.get(i));
            }
            preparedStatement.setObject((objects.size() + 1), primary);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
