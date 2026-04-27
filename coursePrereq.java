public abstract class CoursePrereq extends PrereqSuper {

    public CoursePrereq(String prereq) {
        super(prereq);
    }

    public boolean checkPrereq(String toCheck) {
        boolean prereqMet;
        if (super.getPrereq() == null) {
            prereqMet = true;
        } else if (toCheck != null) {
            prereqMet = super.getPrereq().equalsIgnoreCase(toCheck);
        }

        return prereqMet;
    }
}