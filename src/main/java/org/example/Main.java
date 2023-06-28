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

/**
 * Demo of some parametrised queries representing filtering options for selecting jobs and
 * finding trades-people.
 */
public class Main {
    public static Connection connection = null;

    public static void main(String[] args) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:./sample.db");

            ResultSet plumbingJobsInWelly = getJobs("Plumbing", 90, "Wellington", 1, 5);
            printRS(plumbingJobsInWelly);

            ResultSet goodSparkiesInNapier = getTradesPeople("Electrical", 3, "Napier");
            printRS(goodSparkiesInNapier);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void printRS(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = rs.getString(i);
                System.out.print(columnValue + " " + rsmd.getColumnName(i));
            }
            System.out.println();
        }
    }

    // two main queries - job search from tradesperson's perspective, tradesperson search from client perspective
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

    public static ResultSet getTradesPeople(String specialty,int ratingAtOrAbove,String location) throws SQLException{
        Statement statement = connection.createStatement();
        String query =
            """
            SELECT *
            FROM TradesPerson TP NATURAL JOIN Person P
            NATURAL JOIN Specialty S
            WHERE S.Specialty = '%s' AND TP.avgRating >= %d  AND P.Location = '%s';
            """;
        query = String.format(query,specialty,ratingAtOrAbove,location);
        return statement.executeQuery(query);
    }

}