package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

public class CreateDB {

    //Tells the program where to save the database file.
    public final static String DB_FILE = System.getProperty("user.home")
            + System.getProperty("file.separator") +"CoCDatabase.db";

    private Connection conn = null;

    //main function that creates a database and tables.
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        CreateDB db = new CreateDB();
        db.createTables();

    }

    public CreateDB () {
        createTables();
    }


    //Get a connection to the database
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


    //Calls createTableProfessor
    public void createTables() {
        createTableProfessor();
        // createTableBarns();
    }


    //Create a table in the database for professors to be stored in.
    private void createTableProfessor() {
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

    //Get all professors
    //@return a list of professors
    public ArrayList<Professor> getAllProfessors(){

        ArrayList<Professor> professor = new ArrayList<Professor>();

        if (conn == null)
            conn = getConnection();

        // SQL statement for selecting all professors
        String sql = "SELECT id, firstName, lastName, currentAssignment,representingCurrentUntil " +
                "FROM CoCDatabaseFinal";

        PreparedStatement preparedStatement;
        ResultSet resultSet;


        try {

            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                //int id = resultSet.getInt("id");
                //String firstName = resultSet.getString("firstName");
                //String lastName = resultSet.getString("lastName");
                //String currentAssignment = resultSet.getString("currentAssignment");
                //int until = resultSet.getInt("representingCurrentUntil");


                Professor p = new Professor();
                p.setProfID(resultSet.getInt("id"));
                p.setProfFirstName(resultSet.getString("firstName"));
                p.setProfLastName(resultSet.getString("lastName"));
                p.setProfCurrentAssignment(resultSet.getString("currentAssignment"));
                p.setProfRepresentingCurrentUntil(resultSet.getInt("representingCurrentUntil"));



                professor.add(p);
            }

            // Connection conn = DriverManager.getConnection(url);
            //Statement stmt = conn.createStatement();

            // create a new table
            //stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return professor;

    }



    //Get all of the professors who are in a committee
    //@param currentProfAssignment
    //@return professor list
    public ArrayList<Professor> getAllProfessorsInCommittee(String currentProfAssignment){

        ArrayList<Professor> professor = new ArrayList<Professor>();

        if (conn == null)
            conn = getConnection();

        // SQL statement for selecting all professors
        String sql = "SELECT id, firstName, lastName, currentAssignment, representingCurrentUntil " +
                "FROM CoCDatabaseFinal " +
                "WHERE currentAssignment = ?";

        PreparedStatement preparedStatement;
        ResultSet resultSet;


        try {

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, currentProfAssignment);

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){

                Professor p = new Professor();
                p.setProfID(resultSet.getInt("id"));
                p.setProfFirstName(resultSet.getString("firstName"));
                p.setProfLastName(resultSet.getString("lastName"));
                p.setProfCurrentAssignment(resultSet.getString("currentAssignment"));
                p.setProfRepresentingCurrentUntil(resultSet.getInt("representingCurrentUntil"));



                professor.add(p);
            }

            // Connection conn = DriverManager.getConnection(url);
            //Statement stmt = conn.createStatement();

            // create a new table
            //stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return professor;

    }


    //Get a list of all eligible professors for upcoming committees
    //@Param currentProfAssignment, representingUntil
    //@Return professor
    public ArrayList<Professor> getAllProfessorsEligible(String currentProfAssignment, int representingUntil){

        ArrayList<Professor> professor = new ArrayList<Professor>();

        if (conn == null)
            conn = getConnection();

        // SQL statement for selecting all professors
        String sql = "SELECT id, firstName, lastName, currentAssignment, marriedTo, division, semesterCurrent, " +
                    "representingCurrentUntil, preferenceOne, preferenceTwo " +
                    "FROM CoCDatabaseFinal " +
                    "WHERE currentAssignment <> ? AND representingCurrentUntil <> ? AND currentAssignment <> 'Sabbatical' " +
                    "AND currentAssignment <> 'DeanHSS' AND currentAssignment <> 'DeanMNS' AND currentAssignment <> 'DeanGSP' AND currentAssignment <> 'DeanPP' " +
                    "AND currentAssignment <> 'FacChair' AND currentAssignment <> 'AthRep' AND tenureStatus <> 'DT-15' AND tenureStatus <> 'V' " +
                    "AND tenureStatus <> 'DT-AT'";

        PreparedStatement preparedStatement;
        ResultSet resultSet;


        try {

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, currentProfAssignment);
            //preparedStatement.setString(2, semester);
            preparedStatement.setInt(2, representingUntil);

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



    //Get information on a single professor
    //@param ID
    //@return Professor p
    public Professor getProfessorInformationWithID(int ID){

        Professor p = new Professor();

        if (conn == null)
            conn = getConnection();

        // SQL statement for selecting all professors
        String sql = "SELECT * " +
                "FROM CoCDatabaseFinal " +
                "WHERE ID = ?";

        PreparedStatement preparedStatement;

        ResultSet resultSet;



        try {

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, ID);

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
            e.printStackTrace();
        }

        return p;

    }

    //Fill the array list of potential committees
    public ArrayList<String> getCommittees(){
        ArrayList<String> committees = new ArrayList<String>();

        committees.add("AASFA");
        committees.add("CoC");
        committees.add("Curr");
        committees.add("FERC");
        committees.add("Admin");
        committees.add("P&P");
        committees.add("FacChair");
        committees.add("DeanFA");
        committees.add("DeanGSP");
        committees.add("VALC");
        committees.add("DeanMNS");
        committees.add("DeanPP");
        committees.add("DeanHSS");
        committees.add("FacPers");
        committees.add("Sabbatical");
        committees.add("AthRep");
        committees.add("Athl");
        committees.add("DirAdvising");
        committees.add("FacSec");
        committees.add("Lib");
        committees.add("RelLife");
        committees.add("StudLife");
        committees.add("DeanSON");

        return committees;

    }

}
