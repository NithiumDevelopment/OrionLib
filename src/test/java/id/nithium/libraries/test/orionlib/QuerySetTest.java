package id.nithium.libraries.test.orionlib;

import id.nithium.libraries.orionlib.query.QuerySet;

import java.util.List;
import java.util.UUID;

public class QuerySetTest {

    public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();

        new QuerySet(MainTest.getConnection(), "update orionlib set id = ? where username = ?", List.of(uuid.toString(), "username123"));
        System.out.println("Changed user id of username123 to " + uuid);
    }
}
