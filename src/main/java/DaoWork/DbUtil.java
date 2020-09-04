package DaoWork;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/workshop2?serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "password";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    public static void create(String querry) {
        try (Connection conn = connect();
             Statement state = conn.createStatement()) {
            state.executeUpdate(querry);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
