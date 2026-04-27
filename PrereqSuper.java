//implements PrereqItem
public abstract class PrereqSuper {

    // private static String courseFilename = "output.txt";
    private String prereq;
    // private boolean prereqMet;

    public PrereqSuper() {
        prereq = null;
    }

    public PrereqSuper(String prereq) {
        this.prereq = prereq;
        // prereqMet = false;
    }

    // set the requirement of the prereq if it's currently null
    public void setPrereq(String newPrereq) {
        if (prereq == null) {
            prereq = newPrereq;
        }
    }

    // public static void setCourseFilename(String toSet) {
    // if (toSet != null) {
    // courseFilename = toSet;
    // }
    // }

    // by default, will set prereqMet to true if there is no prereq, or the string
    // passed in matches
    // the prereq string

    // abstract method to add a course to the prereq
    public abstract boolean addPrereq(Course toAdd);
    // {
    // if (prereq == null) {
    // prereqMet = true;
    // } else if (toCheck != null) {
    // prereqMet = prereq.equalsIgnoreCase(toCheck);
    // }

    // return prereqMet;
    // }

    // abstract method to remove a course to the prereq
    public abstract boolean removePrereq(Course toRemove);

    public abstract boolean prereqMet();

    public String getPrereq() {
        return prereq;
    }

    // public void setPrereqMet(boolean setting) {
    // prereqMet = setting;
    // }

    // public boolean prereqsMet() {
    // return prereqMet;
    // }

    public static boolean validCourse(String toCheck) {
        boolean valid = false;
        String[] nameParts;
        if (toCheck != null) {
            nameParts = (toCheck.trim()).split(" ");
            if (nameParts.length == 2) {
                valid = ((nameParts[0].length() == 3 || nameParts[0].length() == 4) && (nameParts[1].length() == 4));
                if (valid) {
                    for (int i = 0; i < 4 && valid; i++) {
                        valid = Character.isDigit(nameParts[1].charAt(i));
                    }
                }
            }
        }

        return valid;
    }
}