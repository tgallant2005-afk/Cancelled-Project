//this abstract class represents a criteria to be met by a course,
//returning true if the course meets said criteria, false if not

import java.util.ArrayList;

public abstract class Criteria {

    private String criteria; // what the criteria actually is
    private boolean required; // is this criteria required for whatever has it?
    private Criteria superCriteria; // holds the criteria the current criteria is a sub-criteria of, if any
    private ArrayList<Criteria> subCriteria; // set of sub-criteria that need to be met for
    // criteria (i.e. 18 credit hours of microbiology with x hours at 4000 level)
    // 18 of micro is main, x 4000 level is sub
    private ArrayList<Course> courses; // set of courses that belong to this criteria
    private int priority; // priority of current criteria, since courses can only belong to 1 criteria
    // sub will be

    public Criteria() {
        criteria = null;
        required = false;
        superCriteria = null;
        subCriteria = null;
        priority = 0;
    }

    public Criteria(String criteria, boolean isRequired, int priority) {
        this.criteria = criteria;
        required = isRequired;
        this.priority = priority;
        superCriteria = null;
        subCriteria = null;
    }

    public String getCriteria() {
        return criteria;
    }

    public int getPriority() {
        return priority;
    }

    public void addSubCriteria(Criteria toAdd) {
        if (toAdd != null) {
            if (subCriteria == null) {
                subCriteria = new ArrayList<Criteria>();
            }
            subCriteria.add(toAdd);
            toAdd.superCriteria = this;
        }
    }

    public boolean addCourse(Course toAdd) {
        boolean added = criteriaMet(toAdd);
        if (courses == null) {
            courses = new ArrayList<Course>();
        }
        courses.add(toAdd);
        added = toAdd.changeCriteriaFurfilling(this);

        return added;
    }

    public boolean removeCourse(Course toRemove) {
        boolean removed = false;
        if (toRemove != null && toRemove.getCriteriaFurfilling() == this) {
            toRemove.changeCriteriaFurfilling(null);
            if (courses != null) {
                courses.remove(toRemove);
            }
            removed = true;
        }

        return removed;
    }

    public Criteria criteriaMet(Course toCheck) {
        Criteria returning = null;
        if (toCheck != null && subCriteria != null) {
            for (int i = 0; i < subCriteria.size() && (returning == null); i++) {
                returning = subCriteria.get(i).criteriaMet(toCheck);
            }
        }

        return returning;
    }

    // returns the top criteria that the current criteria is a subcriteria (or
    // subsub, etc) of
    public Criteria getTopCriteria() {
        Criteria top = this;
        if (superCriteria != null) {
            top = superCriteria.getTopCriteria();
        }

        return top;
    }

}