package connectDB;

import java.sql.*;

public class JDBCConnection {

    private static final String url = "jdbc:sqlserver://localhost:1433;databasename=QLTHUOC;encrypt=true;trustServerCertificate=true;";
    private static final String user = "sa";
    private static final String password = "sapassword";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private static PreparedStatement prepareStatement(Connection connection, String sql, Object... args) throws SQLException {
        PreparedStatement stmt = sql.trim().startsWith("{") ? connection.prepareCall(sql) : connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            stmt.setObject(i + 1, args[i]);
        }
        return stmt;
    }

    public static int update(String sql, Object... args) {
        try (Connection connection = getConnection();
             PreparedStatement stmt = prepareStatement(connection, sql, args)) {
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error executing update", e);
        }
    }

    public static ResultSet query(String sql, Object... args) {
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = prepareStatement(connection, sql, args);
            return stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Error executing query", e);
        }
    }
}
