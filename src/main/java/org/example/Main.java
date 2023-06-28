package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.transform.Result;

public class Main {
    public static Connection connection = null;

    public static void main(String[] args) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:./sample.db");
            ResultSet resultSet = getJobs("Plumbing", 90, "Wellington", 1, 5);

            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = resultSet.getString(i);
                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
                }
                System.out.println();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // two main queries - job search from tradesp perspective, tradesp search from client perspective
    public static ResultSet getJobs(String specialty,double maxCost,String location, int requiredRating,int yearsExpReq)
        throws SQLException {
        Statement statement = connection.createStatement();
        String query =
            """
            SELECT *
            FROM Job J JOIN Specialty S ON J.SId = S.SId
            JOIN Client C ON J.CId= C.CId
            WHERE S.Specialty='%s' AND %f <= maxCost AND Location = '%s' AND ratingRequired >= %d AND yearsExpRequired >= %d;
            """;
        query = String.format(query,specialty,maxCost,location,requiredRating,yearsExpReq);
        return statement.executeQuery(query);
    }

    public static void getTradesPeople(String specialty,int ratingAtOrAbove,String location) {
    }

}