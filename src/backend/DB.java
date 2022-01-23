package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {

    private static final String url = "jdbc:mysql://localhost";
    private static final int port = 3306;
    private static final String username = "root";
    private static final String password = "0262";
    private static final String dbName = "cs360DB";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url + ":" + port + "/" + dbName + "?characterEncoding=UTF-8", username, password);
    }

    public static void closeConnection(Statement statement, Connection connection) {
        if(statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
