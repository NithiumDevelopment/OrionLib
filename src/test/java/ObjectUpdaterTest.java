import id.nithium.libraries.orionlib.ObjectUpdater;
import id.nithium.libraries.orionlib.OrionLib;
import id.nithium.libraries.orionlib.query.QueryGet;
import id.nithium.libraries.orionlib.query.QuerySet;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ObjectUpdaterTest {

    public static void main(String[] args) {
        OrionLib orionLib = OrionLib.getInstance();
        Connection connection = orionLib.createNewConnection("192.168.1.8", 3306, "orionlib", "root", "root");

        User user = new User("John", 30);
        try {
            ObjectUpdater<User> objectUpdater = new ObjectUpdater<>(connection, "usertest", user, user.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
