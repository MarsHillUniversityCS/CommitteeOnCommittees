package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDB {

    public final static String DB_FILE = System.getProperty("user.home")
            + System.getProperty("file.separator") +"CoCDatabase.db";

    private Connection conn = null;

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        CreateDB db = new CreateDB();
        db.createDB();
        db.createTables();

    }

    public Connection getConnection() {
        //if (conn != null) return conn;

        try {
            conn.close();
            // db parameters
            String url = "jdbc:sqlite:" + DB_FILE;
            // create a connection to the database
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void createDB() {

        try {
            // db parameters
            String url = "jdbc:sqlite:" + DB_FILE;
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }


    public void createTables() {
        createTableWarehouses();
        // createTableBarns();
    }


    private void createTableWarehouses() {
        if (conn == null)
            conn = getConnection();

        // SQLite connection string
        // String url = "jdbc:sqlite:C://sqlite/db/tests.db";

        // SQL statement for creating a new table
        String sql = "CREATE table CoCDatabaseFinal " +
                "(id integer PRIMARY KEY, firstName text, lastName text, " +
                "marriedTo integer, division text, department text, " +
                "program text, yearHired integer, currentAssignment text, " +
                "currentAssignmentID integer, representingCurrent text, " +
                "representingCurrentUntil integer, semesterCurrent text, " +
                "nextAssignment text, nextAssignmentID integer, representingNext text, " +
                "representingNextUntil integer, semesterNext text, rank text, " +
                "tenureStatus text, yearEligibleForTenure text, nextYearTenureStatus text, " +
                "yearEligible integer, preferenceOne text, preferenceTwo text, " +
                "preferenceThree text, preferenceFour text, preferenceFive text);";

        try {
            // Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();

            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}
