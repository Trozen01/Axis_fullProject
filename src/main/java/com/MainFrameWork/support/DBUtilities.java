package com.MainFrameWork.support;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author VISHNU
 *
 */
public class DBUtilities {

    //Get the connection object
    public static Connection getConection(String sUser, String sPassword, String sJDBCUrl) throws IOException {

        Connection connection = null;
        try {
            // Load the JDBC driver
            String driverName = "oracle.jdbc.driver.OracleDriver";
            Class.forName(driverName);

            //Logger.printInfo("Creating DB connection with user " + sUser + " and JDBC URL " + sJDBCUrl);
            connection = DriverManager.getConnection(sJDBCUrl, sUser, sPassword);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return connection;
    }

    //For list result
    public static List<Object> getListResult(Connection connection, String sQuery) {

        //Logger.printSQL(sQuery);
        List<Object> table_map = new LinkedList<Object>();

        try {
            // Create a result set containing all data from my_table
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sQuery);
            ResultSetMetaData rsmd = rs.getMetaData();
            int noOfColumns = rsmd.getColumnCount();
            // Fetch each row from the result set

            while (rs.next()) {
                // Get the data from the row using the column index
                ArrayList row = new ArrayList();
                if (noOfColumns == 1) {
                    table_map.add(rs.getString(1));
                } else {
                    for (int i = 1; i <= noOfColumns; i++) {
                        row.add(rs.getObject(i));
                    }
                    table_map.add(row);
                }
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
           // Logger.printError(e.getLocalizedMessage());
        	e.printStackTrace();
        }
        return table_map;
    }

    //For single row-column result
    public static Object getUnitResult(Connection connection, String sQuery) throws SQLException {

        Object result = null;
        //Logger.printSQL(sQuery);
        // Create a result set containing all data from my_table
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sQuery);

        // Fetch each row from the result set
        while (rs.next()) // Get the data from the row using the column index
        {
            result = rs.getObject(1);
        }
        rs.close();
        stmt.close();
        return result;
    }

    //For update
    public static int updateQuery(Connection connection, String sQuery) throws SQLException {

        int iRows = -1;
        //Logger.printSQL(sQuery);
        // Create a result set containing all data from my_table
        Statement stmt;
        connection.setAutoCommit(true);
        stmt = connection.createStatement();
        iRows = stmt.executeUpdate(sQuery);
        connection.commit();
        stmt.close();
        return iRows;
    }

    //Generate where condition
    public static String generateCondition(String criteria, String elementData, String sType) {

        // This condition is used to escape some wildcards and special characters
        if (sType.equalsIgnoreCase("wildCardsString")) {

            //Replace ' with '' to avoid syntax errors
            elementData = elementData.replaceAll("'", "''");

            // "'%' like a char and not like a wildcard"
            elementData = elementData.replaceAll("\\%", "!%");

            // "_" Single Char in SQL
            elementData = elementData.replace("_", "!_");

            /*
             * To handle wild card characters
             * 1. "*" Multiple char sequence
             * 2. "?" Single Char
             */
            elementData = elementData.replaceAll("\\*", "%");
            elementData = elementData.replaceAll("\\?", "_");

            //If the data ends with %, then leave as it is if not add it
            if (!elementData.matches(".*%")) {
                elementData = elementData + "%";
            }

          return criteria + " upper('" + elementData + "') escape '!'";
        }


        if (sType.equalsIgnoreCase("staticString")) {

            //Replace ' with '' to avoid syntax errors
            elementData = elementData.replaceAll("'", "''");

            // "_" Single Char in SQL

            elementData = elementData.replace("_", "!_");

            return criteria + ("'" + elementData + "'") + " escape '!'";
        }

        if (sType.equalsIgnoreCase("string")) {

            //Replace ' with '' to avoid syntax errors
            elementData = elementData.replaceAll("'", "''");

            /*
             * To handle wild card characters
             * 1. "*" Multiple char sequence
             * 2. "?" Single Char
             */
            elementData = elementData.replaceAll("\\*", "%");
            elementData = elementData.replaceAll("\\?", "_");

            //If the data ends with %, then leave as it is if not add it
            if (!elementData.matches(".*%")) {
                elementData = elementData + "%";
            }

            return criteria + " upper('" + elementData + "')";
        }

        if (sType.equalsIgnoreCase("number")) {
            return criteria + elementData;
        }

        if (sType.equals("multiSelectString")) {

            String[] options = elementData.split(";");
            String sAllOptions = "";
            String option = "";
            //Generate condition like school in (school1,school2,school3...)
            for (int i = 0; i < options.length; i++) {
                option = "'" + options[i] + "'";
                if (i == (options.length - 1)) {
                    sAllOptions = sAllOptions + option;
                } else {
                    sAllOptions = sAllOptions + option + ",";
                }
            }
            criteria = criteria + "(" + sAllOptions + ")";
            return criteria;
        }
        return null;
    }

    public static String generateConditionExtended(String criteria, Object elementData, String sType) {

        if (sType.equalsIgnoreCase("staticString")) {

            //Replace ' with '' to avoid syntax errors
            elementData = ((String) (elementData)).replaceAll("'", "''");

            /*
             * To handle wild card characters
             * 1. "*" Multiple char sequence
             * 2. "?" Single Char
             */
            elementData = ((String) (elementData)).replaceAll("\\*", "%");
            elementData = ((String) (elementData)).replaceAll("\\?", "_");

            return criteria + ("'" + elementData + "'");
        }

        if (sType.equalsIgnoreCase("string")) {

            //Replace ' with '' to avoid syntax errors
            elementData = ((String) (elementData)).replaceAll("'", "''");

            /*
             * To handle wild card characters
             * 1. "*" Multiple char sequence
             * 2. "?" Single Char
             */
            elementData = ((String) (elementData)).replaceAll("\\*", "%");
            elementData = ((String) (elementData)).replaceAll("\\?", "_");

            //If the data ends with %, then leave as it is if not add it
            if (!((String) (elementData)).matches(".*%")) {
                elementData = elementData + "%";
            }

            return criteria + " upper('" + elementData + "')";
        }

        if (sType.equalsIgnoreCase("number")) {
            return criteria + elementData;
        }

        if (sType.equals("multiSelectString")) {

            String[] options = ((String) (elementData)).split(";");
            String sAllOptions = "";
            String option = "";
            //Generate condition like school in (school1,school2,school3...)
            for (int i = 0; i < options.length; i++) {
                option = "'" + options[i] + "'";
                if (i == (options.length - 1)) {
                    sAllOptions = sAllOptions + option;
                } else {
                    sAllOptions = sAllOptions + option + ",";
                }
            }
            criteria = criteria + "(" + sAllOptions + ")";
            return criteria;
        }

        if (sType.equals("multiSelectInteger")) {
            ArrayList<Integer> values = (ArrayList<Integer>) (elementData);
            String sAllOptions = "";
            String option = "";
            //Generate condition like school in (school1,school2,school3...)
            for (int i = 0; i < values.size(); i++) {
                option = "" + values.get(i);
                if (i == (values.size() - 1)) {
                    sAllOptions = sAllOptions + option;
                } else {
                    sAllOptions = sAllOptions + option + ",";
                }
            }
            criteria = criteria + "(" + sAllOptions + ")";
            return criteria;
        }

        // Added by wdelacruz
        //Conditional added to handle SQL function values
        if (sType.equals("stringFunction")) {
            //Replace ' with '' to avoid syntax errors
            elementData = ((String) (elementData)).replaceAll("'", "''");
            //Replacing the flag VALUE with data
            criteria = criteria.replaceAll("VALUE", "'" + (String) elementData + "'");
            return criteria;
        }

        return null;
    }
    
    //Execute Batch (batch_sql - multiple sql should be seperated by ; )
    public static void execute_batch(Connection con, String batch_sql) throws Exception {
        
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        con.setAutoCommit(false);
        for(String sql : batch_sql.split(";")) 
            stmt.addBatch(sql.trim());
        
        stmt.executeBatch();
        con.commit();
    }

    public static void main(String[] args) throws IOException, SQLException {

        Connection con = getConection("kalkulus", "kalkulus", "jdbc:oracle:thin:@10.10.11.103:1521:dbtest1");
        String query = "select visa_commission from acq_merchant_master where merchant_code = '120311000222'";
		//String query = "select visa_commission from acq_movement_txns where merchant_code = '120311000222'";
		String vc = DBUtilities.getUnitResult(con, query).toString();
		//int i = Integer.parseInt(rowCount);
        //String vc = "123456789";
        //System.out.println(vc.substring(0, 6));
    }
}
