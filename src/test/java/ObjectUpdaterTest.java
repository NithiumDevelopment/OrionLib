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

        try {
            new QuerySet(connection, "create table if not exists usertest(name varchar(255), age int, primary key(name))", List.of());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        User user = new User("John", 30);

        try {
            QueryGet<User> userQueryGet = new QueryGet<>(connection, "select * from usertest where name = ?", resultSet -> {
                User user1 = null;
                while (resultSet.next()) {
                    user1 = new User(resultSet.getString("name"), resultSet.getInt("age"));
                }

                return user1;
            }, List.of("John"));
            System.out.println(userQueryGet.get().toString());

            ObjectUpdater<User> objectUpdater = new ObjectUpdater<>(connection, "usertest", user, user.getName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
