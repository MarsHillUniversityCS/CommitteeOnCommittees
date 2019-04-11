package project;

public class Professor {

    private int profIsActive;
    private int profID;
    private String profFirstName;
    private String profLastName;
    private String profCurrentAssignment;


    public Professor(int ID, String firstName, String lastName, String currentAssignment, int isActive){
        profID = ID;
        profFirstName = firstName;
        profLastName = lastName;
        profCurrentAssignment = currentAssignment;
        profIsActive = isActive;


    }


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
}
