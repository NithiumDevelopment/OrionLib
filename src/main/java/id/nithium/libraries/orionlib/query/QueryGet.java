package id.nithium.libraries.orionlib.query;

import id.nithium.libraries.orionlib.OrionLib;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class QueryGet<A> {

    private OrionLib orionLib = OrionLib.getInstance();
    private Connection connection;
    private String query;
    private SelectCall<A> selectCall;
    private List<Object> params;

    A a;

    public QueryGet(Connection connection, String query, SelectCall<A> selectCall, List<Object> params) throws SQLException {
        this.connection = connection;
        this.query = query;
        this.selectCall = selectCall;
        this.params = params;

        orionLib.debug("Executing query: \"" + query + "\"");
        orionLib.debug("Params: " + params.toString());
        setup();
    }

    private void setup() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject((i + 1), params.get(i));
            }

            orionLib.debug("Done executing query: \"" + query + "\"");
            a = selectCall.call(preparedStatement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<A> get() {
        orionLib.debug("Getting object of " + a);
        return Optional.of(a);
    }
}
