//subclass of credit hour criteria that not only requires a certain amount of credit hours be met, 
//but that said credit hours come from a certain subject (acc, MATH, BIOL, etc)

import java.util.ArrayList;

public class SubjectCHCriteria extends CreditHourCriteria {

    private ArrayList<String> subjects;
    private boolean FaculOrCour; // if true, checks for faculty, otherwise checks for course code
    // (i.e. uses .getCourseSubject of course if true, otherwise uses course code in
    // name (BIOL, ACC, etc))

    public SubjectCHCriteria(String criteria, int criteriaLimit, boolean required, int priority,
            ArrayList<String> subjects, boolean FaculOrCour) {
        super(criteria, criteriaLimit, required, priority);

        this.subjects = subjects;
        this.FaculOrCour = FaculOrCour;
    }

    private boolean meetCriteria(Course toCheck) {
        String courseSubj;
        boolean metCriteria = false;

        if (subjects != null && toCheck != null) {
            if (FaculOrCour) {
                courseSubj = toCheck.getCourseSubject();
            } else {
                courseSubj = toCheck.getCourseName().split(" ")[0];
            }

            for (int i = 0; i < subjects.size() && !metCriteria; i++) {
                metCriteria = (courseSubj.equals(subjects.get(i)));
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