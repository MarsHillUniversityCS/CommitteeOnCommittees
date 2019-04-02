import java.io.FileReader;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class ExcelImporter {
	public static void main(String args[]){
		new ExcelImporter().readCSV("CoC_data.csv");
	}

	
	public void readCSV(String fn){
		
		try {
			Reader in = new FileReader(fn);
			for(CSVRecord record : CSVFormat.RFC4180.withHeader().parse(in)) {
				String firstName = record.get("First Name");			
				String lastName = record.get("Last name");			
				String dept = record.get("Dept");

				System.out.println(firstName + " " + lastName + " - *" + dept + "*");

				String sql = "INSERT INTO <table name> (first_name, last_name, field3...) " +
					" VALUES ('" + firstName + "', '" + lastName + "','"; 

				//create and then execute the sql statment
	
				//break;
	
			}

		} catch (Exception e){
			e.printStackTrace();
		}


	}
}
