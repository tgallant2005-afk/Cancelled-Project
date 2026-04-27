public abstract class CreditHourPrereq extends PrereqSuper implements DegreeComment {

    private int creditHours;

    public CreditHourPrereq() {
        super();

        creditHours = 0;
    }

    // note: string should start with the number of credit hours the course is
    public CreditHourPrereq(String prereq) {
        super(null);
        boolean validCH;

        if (prereq != null) {
            validCH = setupCreditHours(prereq);
            if (!validCH) {
                System.out.println(
                        "prereq entered was not longer than 1 word and/or did not start with it's credit hours, not setting as the representing prereq");
            }
        }
    }

    private boolean setupCreditHours(String prereq) {
        String setupPrereq = prereq.trim();
        boolean validCH = true;
        int spaceIndex = setupPrereq.indexOf(" ");

        for (int i = 0; i < spaceIndex; i++) {
            validCH = Character.isDigit(setupPrereq.charAt(i));
        }

        if (spaceIndex > -1 && validCH) {
            creditHours = Integer.parseInt(prereq.substring(0, spaceIndex));
        }

        return (spaceIndex > -1 && validCH);
    }

    public void setPrereq(String newPrereq) {
        if (super.getPrereq() == null) {
            if (setupCreditHours(newPrereq)) {
                super.setPrereq(newPrereq);
            }
        } else {
            System.out.println("a prereq cannot be edit after it has been set");
        }
    }

    // add a course to this comment's credit hours
    public void addCourse(Course courseAdding) {
        int creditHourChange = 0;
        if (courseAdding != null) {
            creditHourChange = courseAdding.getCreditHours();
            if (creditHourChange >= 0 && creditHourChange <= creditHours) {
                creditHours -= creditHourChange;
                courseAdding.changeCommentFurfilling(this);
            }
        }
    }

    public void removeCourse(Course courseRemoving) {
        int creditHourChange = 0;
        if (courseRemoving != null) {
            creditHourChange = courseRemoving.getCreditHours();
            if (creditHourChange >= 0) {
                creditHours += creditHourChange;
                courseRemoving.changeCommentFurfilling(null);
            }
        }
    }
}