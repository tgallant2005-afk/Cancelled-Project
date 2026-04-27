//while some criteria are exclusive (i.e. a course cannot meet 2 seperate credit hour criteria)
//some (like fulfilling a written requirement) can be met by a course meeting other criteria,
//and this class represents those criteria

import java.util.ArrayList;

public abstract class RepeatCriteria extends Criteria {

    private ArrayList<Course> coursesMeeting;
    // courses meeting said criteria

    public RepeatCriteria(String criteria, boolean isRequired, int priority) {
        super(criteria, isRequired, priority);
    }

    public boolean addCourse(Course toAdd) {
        boolean added = super.addCourse(toAdd);

        if (added) {
            if (coursesMeeting == null) {
                coursesMeeting = new ArrayList<Course>();
            }
            coursesMeeting.add(toAdd);
        }

        return added;
    }

    public boolean removeCourse(Course toRemove) {
        boolean removed = super.addCourse(toRemove);

        if (removed && coursesMeeting != null) {
            coursesMeeting.remove(toRemove);
        }

        return removed;
    }
}