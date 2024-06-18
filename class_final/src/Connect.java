import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    static Connection conn = null;
    public static void connect_database() throws SQLException{
        String url = "jdbc:sqlserver://ANRIUS\\SERVER9:1433;databaseName=injection;integratedSecurity=true;" + "encrypt=true;trustServerCertificate=true;";
        conn = DriverManager.getConnection(url);
    }
    public static Connection getConnection(){
        return conn;
    }
}