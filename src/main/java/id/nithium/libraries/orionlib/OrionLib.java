package id.nithium.libraries.orionlib;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import id.nithium.libraries.orionlib.query.QuerySet;
import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public class OrionLib {

    private static OrionLib orion;
    private HikariDataSource hikariDataSource;
    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Setter
    private boolean debug = false;

    public Connection createNewConnection(String host, int port, String database, String username, String password) {
        Connection connection = null;

        System.out.println("Connecting MySQL to \"" + host + ":" + port + "/" + database + "\" from OrionLib...");
        try {
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl("jdbc:mysql://" + host+ ":" + port + "/" + database);
            hikariConfig.setUsername(username);
            hikariConfig.setPassword(password);

            hikariDataSource = new HikariDataSource(hikariConfig);
            connection = hikariDataSource.getConnection();

            System.out.println("Connected to connection of " + host + ":" + port + "/" + database + " from OrionLib.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static OrionLib getInstance() {
        if (orion == null) {
            orion = new OrionLib();
        }

        return orion;
    }

    public void debug(String s) {
        if (debug) {
            System.out.println("(ORION DEBUG) " + s);
        }
    }
}