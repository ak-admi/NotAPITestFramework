package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseUtils {

    private static final String URL= ConfigReader.get("db.url");
    private static final String USER =ConfigReader.get("db.username");
    private static final String PASSWORD=ConfigReader.get("db.password");

    public static int getUserCount(){
        try{
            Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);

            Statement stmt= connection.createStatement();

            ResultSet rs= stmt.executeQuery("Select Count(*) From Users");

            rs.next();
            return rs.getInt(1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
