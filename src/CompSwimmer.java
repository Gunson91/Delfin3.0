public class CompSwimmer extends Member {

    private double pb;
    private String discipline;

    public CompSwimmer(int memberID, String fName, String lName, int birthYear, String discipline, double pb) {
        this.discipline = discipline;
        this.pb = pb;
        setMemberID(memberID);
        setFName(fName);
        setLName(lName);
        setBirthYear(birthYear);
    }

    public double getPb() {
        return pb;
    }

    public String getDiscipline() {
        return discipline;
    }
}
