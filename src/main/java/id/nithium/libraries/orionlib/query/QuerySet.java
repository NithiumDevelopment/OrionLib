package id.nithium.libraries.orionlib.query;

import id.nithium.libraries.orionlib.OrionLib;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class QuerySet {

    private OrionLib orionLib = OrionLib.getInstance();
    private Connection connection;
    private String query;
    private List<Object> params;

    @Getter
    private boolean next = false;

    public QuerySet(Connection connection, String query, List<Object> params) {
        this.connection = connection;
        this.query = query;
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
            preparedStatement.executeUpdate();
            next = true;
            orionLib.debug("Done executing query: \"" + query + "\"");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean next() {
        return next;
    }
}
