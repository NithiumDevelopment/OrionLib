package id.nithium.libraries.test.orionlib;

import id.nithium.libraries.orionlib.query.QueryGet;
import id.nithium.libraries.test.orionlib.object.UserTest;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class QueryGetTest {

    public static void main(String[] args) {
        QueryGet<UserTest> userTestQueryGet = new QueryGet<>(MainTest.getConnection(), "select id from orionlib where username = ?", resultSet -> {
            UserTest userTest = new UserTest(UUID.randomUUID(), "username123");

            while (resultSet.next()) {
                userTest.setUuid(UUID.fromString(resultSet.getString("id")));
            }

            return userTest;
        }, List.of("username123"));

        UserTest userTest = null;
        try {
            userTest = userTestQueryGet.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        if (userTest != null) {
            System.out.println("User: " + userTest.getUuid() + ", "  + userTest.getUsername());

            QuerySetTest.main(args);
        }
    }
}
