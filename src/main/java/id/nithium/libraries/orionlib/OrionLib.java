package id.nithium.libraries.orionlib;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class OrionLib {

    @Getter
    private static OrionLib orion;

    public Connection createNewConnection(String host, int port, String database, String username, String password) {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database,
                    username, password);
            System.out.println("Connected to connection of " + host + ":" + port + "/" + database + ".");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static OrionLib setInstance() {
        if (orion == null) {
            orion = new OrionLib();
        }

        return orion;
    }

}