package id.nithium.libraries.orionlib.query;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SelectCall<T> {

    T call(ResultSet resultSet) throws SQLException;
}
