package project;

import java.util.ArrayList;

public class Professor {

    private int profIsActive;
    private int profID;
    private String profFirstName;
    private String profLastName;
    private String profCurrentAssignment;

    ArrayList<String> TABLE_CONSTANTS = new ArrayList<String>();

    public Professor(int ID, String firstName, String lastName, String currentAssignment){
        profID = ID;
        profFirstName = firstName;
        profLastName = lastName;
        profCurrentAssignment = currentAssignment;
    }

    public Professor(){}

    public int getProfIsActive() {
        return profIsActive;
    }

    public void setProfIsActive(int profIsActive) {
        this.profIsActive = profIsActive;
    }

    public int getProfID() {
        return profID;
    }

    public void setProfID(int profID) {
        this.profID = profID;
    }

    public String getProfFirstName() {
        return profFirstName;
    }

    public void setProfFirstName(String profFirstName) {
        this.profFirstName = profFirstName;
    }

    public String getProfLastName() {
        return profLastName;
    }

    public void setProfLastName(String profLastName) {
        this.profLastName = profLastName;
    }

    public String getProfCurrentAssignment() {
        return profCurrentAssignment;
    }

    public void setProfCurrentAssignment(String profCurrentAssignment) {
        this.profCurrentAssignment = profCurrentAssignment;
    }

    public Object[] getTableInfo(){

        Object[] info = new Object[4];
        int i = 0;
        info[i++] = getProfID();
        info[i++] = getProfFirstName();
        info[i++] = getProfLastName();
        info[i++] = "2018";

        return info;

    }

    public void fillStringConstants(){
        TABLE_CONSTANTS.add("First Name");
    }
}
