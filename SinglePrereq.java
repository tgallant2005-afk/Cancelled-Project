public class SinglePrereq extends PrereqSuper {

    private boolean specialPrereq; // used for "special prereqs" like consent of department
    private boolean prereqMet = false;

    public SinglePrereq(String prereq) {
        super(prereq);
        if (prereq != null && !prereq.contains(ClassReader.THREE_NUMBER_REGEX)) {
            specialPrereq = false;
        } else {
            specialPrereq = true;
        }
    }

    public boolean addPrereq(Course toAdd) {
        boolean added = false;
        if (!specialPrereq && toAdd != null) {
            prereqMet = super.getPrereq().equalsIgnoreCase(toAdd.getCourseName());
            added = true;
        }

        return added;
    }

    public boolean removePrereq(Course toRemove) {
        boolean removed = false;
        if (!specialPrereq && toRemove != null) {
            if (super.getPrereq().equalsIgnoreCase(toRemove.getCourseName())) {
                prereqMet = false;
                removed = true;
            }
        }

        return removed;
    }

    // recieves a string and considers the prereq met if the passed in string is
    // contained
    // public boolean checkPrereq(Course toCheck) {

    // boolean prereqMet = false;
    // if (super.getPrereq() != null && super.validCourse(toCheck)) {
    // if (super.getPrereq().contains(toCheck)) {
    // prereqMet = super.checkPrereq(super.getPrereq());
    // }
    // }
    // return prereqMet;
    // }

    public boolean prereqMet() {
        // system somewhat ignores special prereqs for creating scedule, so if it is
        // special consider it met
        return specialPrereq || prereqMet;
    }
}