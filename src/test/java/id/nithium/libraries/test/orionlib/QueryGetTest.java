package id.nithium.libraries.test.orionlib;

import id.nithium.libraries.orionlib.query.QueryGet;
import id.nithium.libraries.test.orionlib.object.UserTest;

import java.util.List;
import java.util.UUID;

public class QueryGetTest {

    public static void main(String[] args) {
        QueryGet<UserTest> userTestQueryGet = new QueryGet<>(MainTest.getConnection(), "select * from orionlib where username = ?", resultSet -> {
            UserTest userTest = new UserTest(UUID.randomUUID(), "username123");

            while (resultSet.next()) {
                userTest.setUuid(UUID.fromString(resultSet.getString("id")));
            }

            return userTest;
        }, List.of("username123"));

        UserTest userTest = userTestQueryGet.get();
        if (userTest != null) {
            System.out.println("User: " + userTest.getUuid() + ", "  + userTest.getUsername());

            QuerySetTest.main(args);
        }
    }
}
