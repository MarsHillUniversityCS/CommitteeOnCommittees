package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

public class CreateDB {

    public final static String DB_FILE = System.getProperty("user.home")
            + System.getProperty("file.separator") +"CoCDatabase.db";

    private Connection conn = null;

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        CreateDB db = new CreateDB();
        db.createTables();

    }

    public CreateDB () {
        createTables();
    }

    public Connection getConnection() {
        //if (conn != null) return conn;

        try {
            if (conn != null) conn.close();
            // db parameters
            String url = "jdbc:sqlite:" + DB_FILE;
            // create a connection to the database
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
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
            String sql = "CREATE table IF NOT EXISTS CoCDatabaseFinal " +
                    "(id integer PRIMARY KEY, firstName text, lastName text, " +
                    "marriedTo integer, division text, department text, " +
                    "program text, yearHired integer, currentAssignment text, " +
                    "currentAssignmentID integer, representingCurrent text, " +
                    "representingCurrentUntil integer, semesterCurrent text, " +
                    "nextAssignment text, nextAssignmentID integer, representingNext text, " +
                    "representingNextUntil integer, semesterNext text, rank text, " +
                    "tenureStatus text, yearEligibleForTenure text, nextYearTenureStatus text, " +
                    "yearEligible integer, preferenceOne text, preferenceTwo text, " +
                    "preferenceThree text, preferenceFour text, preferenceFive text, IsActive boolean);";

            try {
                // Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement();

                // create a new table
                stmt.execute(sql);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

    }


    public ArrayList<Professor> getAllProfessors(){

        ArrayList<Professor> professor = new ArrayList<Professor>();

        if (conn == null)
            conn = getConnection();

        // SQL statement for selecting all professors
        String sql = "SELECT id, firstName, lastName, currentAssignment" +
                "FROM CoCDatabaseFinal";

        PreparedStatement preparedStatement;
        ResultSet resultSet;


        try {

            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String currentAssignment = resultSet.getString("currentAssignment");

                //Professor p = new Professor();
                //p.setProfID(resultSet.getInt("id"));
                //p.setProfFirstName(resultSet.getString("firstName"));



                professor.add(new Professor(id, firstName, lastName, currentAssignment));
            }

            // Connection conn = DriverManager.getConnection(url);
            //Statement stmt = conn.createStatement();

            // create a new table
            //stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return professor;

    }



    public ArrayList<Professor> getAllProfessorsInCommittee(String currentProfAssignment){

        ArrayList<Professor> professor = new ArrayList<Professor>();

        if (conn == null)
            conn = getConnection();

        // SQL statement for selecting all professors
        String sql = "SELECT id, firstName, lastName, currentAssignment" +
                "FROM CoCDatabaseFinal" +
                "WHERE currentAssignment = ?";

        PreparedStatement preparedStatement;
        ResultSet resultSet;


        try {

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(0, currentProfAssignment);

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String currentAssignment = resultSet.getString("currentAssignment");



                professor.add(new Professor(id, firstName, lastName, currentAssignment));
            }

            // Connection conn = DriverManager.getConnection(url);
            //Statement stmt = conn.createStatement();

            // create a new table
            //stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return professor;

    }



    public ArrayList<Professor> getAllProfessorsEligible(String currentProfAssignment){

        ArrayList<Professor> professor = new ArrayList<Professor>();

        if (conn == null)
            conn = getConnection();

        // SQL statement for selecting all professors
        String sql = "SELECT id, firstName, lastName, currentAssignment, marriedTo, division, semesterCurrent, representingCurrentUntil, preferenceOne, preferenceTwo" +
                "FROM CoCDatabaseFinal" +
                "WHERE currentAssignment = ?";

        PreparedStatement preparedStatement;
        ResultSet resultSet;


        try {

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(0, currentProfAssignment);

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){

                Professor p = new Professor();

                p.setProfID(resultSet.getInt("id"));
                p.setProfFirstName(resultSet.getString("firstName"));
                p.setProfLastName(resultSet.getString("lastName"));
                p.setProfCurrentAssignment(resultSet.getString("currentAssignment"));
                p.setProfMarried(resultSet.getInt("marriedTo"));
                p.setProfDivison(resultSet.getString("division"));
                p.setProfCurrentSemester(resultSet.getString("semesterCurrent"));
                p.setProfRepresentingCurrentUntil(resultSet.getInt("representingCurrentUntil"));
                p.setProfPreferenceOne(resultSet.getString("preferenceOne"));
                p.setProfPreferenceTwo(resultSet.getString("preferenceTwo"));


                professor.add(p);
            }

            // Connection conn = DriverManager.getConnection(url);
            //Statement stmt = conn.createStatement();

            // create a new table
            //stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return professor;

    }


    //ToDo: Get all of the professor info where ID = ID
    public Professor getProfessorInformationWithID(int ID){

        Professor p = new Professor();

        if (conn == null)
            conn = getConnection();

        // SQL statement for selecting all professors
        String sql = "SELECT * " +
                "FROM CoCDatabaseFinal" +
                "WHERE ID = ?";

        PreparedStatement preparedStatement;

        ResultSet resultSet;



        try {

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(0, ID);

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                p.setProfID(resultSet.getInt("id"));
                p.setProfFirstName(resultSet.getString("firstName"));
                p.setProfLastName(resultSet.getString("lastName"));
                p.setProfCurrentAssignment(resultSet.getString("currentAssignment"));
                p.setProfCurrentAssignmentID(resultSet.getInt("currentAssignmentID"));
                p.setProfCurrentRepresenting(resultSet.getString("representingCurrent"));
                p.setProfCurrentSemester(resultSet.getString("semesterCurrent"));
                p.setProfDept(resultSet.getString("department"));
                p.setProfDivison(resultSet.getString("division"));
                p.setProfHired(resultSet.getInt("yearHired"));
                p.setProfIsActive(resultSet.getBoolean("IsActive"));
                p.setProfMarried(resultSet.getInt("marriedTo"));
                p.setProfNextAssignment(resultSet.getString("nextAssignment"));
                p.setProfNextAssignmentID(resultSet.getInt("nextAssignmentID"));
                p.setProfNextRepresenting(resultSet.getString("representingNext"));
                p.setProfNextSemester(resultSet.getString("semesterNext"));
                p.setProfNextYearTenureStatus(resultSet.getString("nextYearTenureStatus"));
                p.setProfPreferenceOne(resultSet.getString("preferenceOne"));
                p.setProfPreferenceTwo(resultSet.getString("preferenceTwo"));
                p.setProfPreferenceThree(resultSet.getString("preferenceThree"));
                p.setProfPreferenceFour(resultSet.getString("preferenceFour"));
                p.setProfPreferenceFive(resultSet.getString("preferenceFive"));
                p.setProfProgram(resultSet.getString("program"));
                p.setProfRank(resultSet.getString("rank"));
                p.setProfRepresentingCurrentUntil(resultSet.getInt("representingCurrentUntil"));
                p.setProfRepresentingNextUntil(resultSet.getInt("representingNextUntil"));
                p.setProfTenure(resultSet.getInt("tenureStatus"));
                p.setProfYearEligibleTenure(resultSet.getString("yearEligibleForTenure"));


            }

            // Connection conn = DriverManager.getConnection(url);
            //Statement stmt = conn.createStatement();

            // create a new table
            //stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return p;

    }

}
