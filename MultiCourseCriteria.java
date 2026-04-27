//this class represents when multiple courses are needed for a criteria
// (i.e.  XXXX 1020 and [XXXX 2030 or XXXX 2032])
//it can do both only or's, only and's, and sets
// (i.e. either the first 2, or the next 2, etc) 

import java.util.ArrayList;

public class MultiCourseCriteria extends Criteria {

    private boolean[] metCourses;
    private String[] courses;
    private int setSize;
    private CourseOptions options;
    // used for setting if and, or, or pairs
    // the # represents how many course names are in a "group",
    // and the criteria is considered "met" if a group is met
    // setSize = 1 means or, since each course is a group
    // setSize = courses.length is an and, since all courses are 1 group
    // and any number between splits into multi-course groups

    public MultiCourseCriteria(String criteria, boolean isRequired, int priority, ArrayList<String> courses,
            int splits) {
        super(criteria, isRequired, priority);
        // setupCourses(courses);
        options = new CourseOptions(courses, splits);
        // metCourses = new boolean[this.courses.length];
        // if (0 < splits && splits < this.courses.length) {
        // setSize = splits;
        // } else {
        // System.out.println(splits + " is either -ve or larger then # of courses,
        // defaulting to 1 (or)");
        // setSize = 1;
        // }
    }

    private void setupCourses(ArrayList<String> courses) {
        if (courses != null) {
            for (int i = 0; i < courses.size(); i++) {
                if (!Course.verifyCourseName(courses.get(i))) {
                    courses.remove(i);
                    i--;
                }
            }

            this.courses = new String[courses.size()];
            for (int i = 0; i < courses.size(); i++) {
                this.courses[i] = courses.get(i);
            }
        }
    }

    private boolean meetCriteria(Course toCheck) {
        return options.canAdd(toCheck);
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

    // if remove is false, then we're adding the course, if true, removing
    private void changeCourseMet(Course toChange, boolean remove) {

        if (toChange != null) {
            for (int i = 0; i < courses.length; i++) {
                if (courses[i].equalsIgnoreCase(toChange.getCourseName())) {
                    metCourses[i] = !remove;
                }
            }
        }
    }

    public boolean addCourse(Course toAdd) {
        boolean added = false;

        if (meetCriteria(toAdd)) {
            added = super.addCourse(toAdd);
            options.addCourse(toAdd);
        }

        return added;
    }

    public boolean removeCourse(Course toRemove) {
        boolean removed = false;

        if (meetCriteria(toRemove)) {
            removed = super.removeCourse(toRemove);
            options.removeCourse(toRemove);
        }

        return removed;
    }

}