import java.util.ArrayList;

public class MultiCourseCHCriteria extends CreditHourCriteria {

    private CourseOptions options;

    public MultiCourseCHCriteria(String criteria, int limit, boolean required, int priority, ArrayList<String> courses,
            int selection) {
        super(criteria, limit, required, priority);
        options = new CourseOptions(courses, selection);
    }

    public Criteria criteriaMet(Course toCheck) {
        Critera met = null;

        if (options.canAdd(toCheck)) {
            met = super.criteriaMet(toCheck);
            if (met != null) {
                met = this;
            }
        }

        return met;
    }

    public boolean addCourse(Course toAdd) {
        boolean added = false;

        if (criteriaMet(toAdd) == this) {
            options.addCourse(toAdd);
            super.addCourse(toAdd);
            added = true;
        }

        return added;
    }

    public boolean removeCourse(Course toRemove) {
        boolean removed = false;

        if (criteriaMet(toRemove) == this) {
            options.addCourse(toRemove);
            super.addCourse(toRemove);
            removed = true;
        }

        return removed;
    }

}