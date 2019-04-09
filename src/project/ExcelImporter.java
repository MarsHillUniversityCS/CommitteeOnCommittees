package project;

import java.io.FileReader;
import java.io.Reader;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class ExcelImporter {


	public static void main(String args[]){
		//new ExcelImporter().readCSV("/home/s000192926/IdeaProjects/CommitteeOnCommittees/resources/CoC_data.csv");
		new ExcelImporter().readCSV("./resources/CoC_data.csv");
	}
	
	public void readCSV(String fn){


		try {
			Reader in = new FileReader(fn);
			for(CSVRecord record : CSVFormat.RFC4180.withHeader().parse(in)) {

				String firstName = makeSQLSafe(record.get("First Name"));
				String lastName = makeSQLSafe(record.get("Last name"));
				String dept = makeSQLSafe(record.get("Dept"));
				int marriedTo = getNumericValueIfExists(record.get("Married To"));
				int ID = getNumericValueIfExists(record.get("ID"));
				String division = makeSQLSafe(record.get("Division"));
				String program = makeSQLSafe(record.get("Program"));
				int yearHired = getNumericValueIfExists(record.get("Year of Appt."));
				String currentAssignment = makeSQLSafe(record.get("Current Assignment"));
				int currentAssignmentID = getNumericValueIfExists(record.get(" #"));
				String representingCurrent = makeSQLSafe(record.get("RepresentingCurrent"));
				int representingCurrentUntil = getNumericValueIfExists(record.get("untilCurrent"));
				String semCurrent = makeSQLSafe(record.get("SemCurrent"));
				String nextAssignment = makeSQLSafe(record.get("Next Assignment"));
				int nextAssignmentID = getNumericValueIfExists(record.get("#"));
				String representingNext = makeSQLSafe(record.get("RepresentingNext"));
				int representingNextUntil = getNumericValueIfExists(record.get("untilNext"));
				String semNext = makeSQLSafe(record.get("SemNext"));
				String rank = makeSQLSafe(record.get("Rank"));
				String tenureStatus = makeSQLSafe(record.get("Tenure Status"));
				String yearEligibleForTenure = makeSQLSafe(record.get("Year Eligible for Tenure"));
				String nextYearTenureStatus = makeSQLSafe(record.get("Next Year Tenure Status (if different)"));
				int yearEligible = getNumericValueIfExists(record.get("Year Elegible (if applicable)"));
				String preferenceOne = makeSQLSafe(record.get("Pref1"));
				String preferenceTwo = makeSQLSafe(record.get("Pref2"));
				String preferenceThree = makeSQLSafe(record.get("Pref3"));
				String preferenceFour = makeSQLSafe(record.get("Pref4"));
				String preferenceFive = makeSQLSafe(record.get("Pref5"));


				//System.out.println(firstName + " " + lastName + " - *" + dept + "*");

				String sql = "INSERT INTO CoCDatabaseFinal (ID, firstName, lastName, marriedTo, division, department, program, yearHired, currentAssignment," +
						"currentAssignmentID, representingCurrent, representingCurrentUntil, semesterCurrent, nextAssignment, nextAssignmentID," +
						"representingNext, representingNextUntil, semesterNext, rank, tenureStatus, yearEligibleForTenure, nextYearTenureStatus, yearEligible," +
						"preferenceOne, preferenceTwo, preferenceThree, preferenceFour, preferenceFive, isActive)" +
					" VALUES ('" + ID + "', '" + firstName + "', '" + lastName + "', '" + marriedTo + "', '" + division + "', '" + dept + "', " +
						"'" + program + "', '" + yearHired + "', '" + currentAssignment + "', '" + currentAssignmentID + "', '" + representingCurrent + "', " +
						"'" + representingCurrentUntil + "', '" + semCurrent + "', '" +  nextAssignment + "', '" + nextAssignmentID + "', '" + representingNext + "', " +
						"'" + representingNextUntil + "', '" + semNext + "', '" + rank + "', '" + tenureStatus + "', '" + yearEligibleForTenure + "', " +
						"'" + nextYearTenureStatus + "', '" + yearEligible + "', '" + preferenceOne + "', '" + preferenceTwo + "', '" + preferenceThree + "', " +
						"'" + preferenceFour + "', '" + preferenceFive + "', '" + true + "');";


				//create and then execute the sql statement
	
				//break;
				try {
					CreateDB db = new CreateDB();
					Connection conn = db.getConnection();


					Statement stmt = conn.createStatement();

					// create a new table

					System.out.println(sql);
					stmt.execute(sql);
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}

	
			}

		} catch (Exception e){
			e.printStackTrace();
		}


	}

	private int getNumericValueIfExists(String numericText){

		try {

			return Integer.parseInt(numericText);

		} catch (NumberFormatException nfe){
			return -1;
		}
	}

	private String makeSQLSafe(String s){
		if (s.indexOf("'") != -1) {
			s = s.replace("'", "''");
		}



		return s;
	}
}
