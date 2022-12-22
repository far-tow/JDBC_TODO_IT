package se.lexicon.dao.dataBase;


import se.lexicon.exeptions.DBConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    //Write the url & credentials to Database
    private static final String URL = "jdbc:mysql://localhost:3306/todoit";
    private static final String uName = "root";
    private static final String pWord = "12345678";

    //Establish Connection object
    public static Connection getConnection() throws DBConnectionException {
        try {
            return DriverManager.getConnection(URL, uName, pWord);
        } catch (SQLException e) {
            throw new DBConnectionException("Database connection failed!");
        }

    }


}
