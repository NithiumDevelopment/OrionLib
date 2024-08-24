package id.nithium.libraries.test.orionlib;

import id.nithium.libraries.orionlib.OrionLib;
import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;

public class MainTest {

    @Getter
    @Setter
    private static MainTest instance;

    @Getter
    private static OrionLib orionLib;

    @Getter
    private static Connection connection;

    public static void main(String[] args) {
        setInstance(new MainTest());

        orionLib = OrionLib.setInstance();
        connection = orionLib.createNewConnection("localhost", 3306, "orionlib", "root", null);

        QueryGetTest.main(args);
    }
}
