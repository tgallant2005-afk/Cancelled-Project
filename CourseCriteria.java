//represents the criteria of a single course (i.e. HIST 2710)
public class CourseCriteria extends Criteria {

    boolean metCourse;

    public CourseCriteria(String criteria, boolean isRequired, int priority) {
        super(criteria, isRequired, priority);
        metCourse = false;
    }

    private boolean meetCriteria(Course toCheck) {
        boolean metCriteria = (toCheck != null && toCheck.getCourseName().equals(super.getCriteria()));

        return metCriteria;
    }

    public Criteria criteriaMet(Course toCheck) {
        Criteria met = null;

        if (meetCriteria(toCheck)) {
            met = super.criteriaMet(toCheck);

            if (met == null) {
                met = this;
            }
        }

        return met;
    }

    public boolean addCourse(Course toAdd) {
        boolean added = false;

        if (meetCriteria(toAdd) && !metCourse) {
            added = super.addCourse(toAdd);
            metCourse = true;
        }

        return added;
    }

    public boolean removeCourse(Course toRemove) {
        boolean removed = false;

        if (meetCriteria(toRemove) && metCourse) {
            removed = super.removeCourse(toRemove);
            metCourse = false;
        }

        return removed;
    }
}