package id.nithium.libraries.orionlib.query;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class QueryGet<A> {
    private Connection connection;
    private String query;
    private SelectCall<A> selectCall;
    private List<Object> params;

    A a;

    public QueryGet(Connection connection, String query, SelectCall<A> selectCall, List<Object> params) {
        this.connection = connection;
        this.query = query;
        this.selectCall = selectCall;
        this.params = params;

        System.out.println("(IGNORE THIS) Executing query: \"" + query + "\"");
        setup();
    }

    private void setup() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject((i + 1), params.get(i));
            }

            System.out.println("(IGNORE THIS) Done executing query: \"" + query + "\"");
            a = selectCall.call(preparedStatement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public A get() {
        System.out.println("(IGNORE THIS) Getting object of " + a.toString());
        return a;
    }
}
