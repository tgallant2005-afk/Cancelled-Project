
//subclass of subjectCHCriteria that not only requires the same criteria as its superclass,
// but that said courses have to be of a certain level (3000 range, 4000, etc)

import java.util.ArrayList;

public class LevelCHCriteria extends SubjectCHCriteria {

    // holds level determining digits seperated by ","
    // i.e. if the levels were 3000 and 4000, it holds 3,4
    private String levels;

    public LevelCHCriteria(String criteria, int criteriaLimit, boolean required, int priority,
            ArrayList<String> subjects, String levels) {
        super(criteria, criteriaLimit, required, priority, subjects);

    }

    private void verifyLevels(String levels) {
        boolean alternate = false;// false if checking for digit, true if checking for ","
        boolean valid = true;

        if (levels != null) {
            for (int i = 0; i < levels.length() && valid; i++) {
                if (alternate) {
                    valid = (levels.charAt(i) == ',');
                } else {
                    valid = (Character.isDigit(levels.charAt(i)));
                }
            }
        }

        if (valid) {
            this.levels = levels;
        } else {
            this.levels = null;
        }
    }

    private boolean meetCriteria(Course toCheck) {
        String courseLevel;
        char level;
        boolean metCriteria = false;

        if (toCheck != null && levels != null) {
            courseLevel = toCheck.getCourseName().split(" ")[1];
            level = courseLevel.charAt(0);

            for (int i = 0; i < levels.length() && !metCriteria; i = i + 2) {
                metCriteria = (levels.charAt(i) == level);
            }
        }

        return metCriteria;
    }

    public Criteria criteriaMet(Course toCheck) {
        Criteria met = null;

        if (meetCriteria(toCheck)) {
            met = super.criteriaMet(toCheck);
            if (met != null) {
                met = this;
            }
        }

        return met;
    }

    public boolean addCourse(Course toAdd) {
        boolean added = false;

        if (meetCriteria(toAdd)) {
            added = super.addCourse(toAdd);
        }

        return added;
    }

    public boolean removeCourse(Course toRemove) {
        boolean removed = false;

        if (meetCriteria(toRemove)) {
            removed = super.removeCourse(toRemove);
        }

        return removed;
    }
}