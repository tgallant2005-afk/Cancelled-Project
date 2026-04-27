//subclass of LimitCriteria that uses it as a class limit, 
//a counter goes up by 1 whenever a course is added, and if the limit > 0,
//won't count as meeting criteria if the count = limit
public class CountCriteria extends LimitCriteria {

    private int count = 0;

    public CountCriteria(String criteria, int limit, boolean required, int priority) {
        super(criteria, limit, required, priority);
    }

    public int getCount() {
        return count;
    }

    public Criteria criteriaMet(Course toCheck) {
        Criteria met = null;

        if (toCheck != null && super.getLimit() > count) {
            met = super.criteriaMet(toCheck);
            if (met == null) {
                met = this;
            }
        }

        return met;
    }

    public boolean addCourse(Course toAdd) {
        boolean added = false;

        if (super.getLimit() > count) {
            added = super.addCourse(toAdd);
            if (added) {
                count++;
            }
        }

        return added;
    }

    public boolean removeCourse(Course toRemove) {
        booelan removed = false;

        removed = super.removeCourse(toRemove);
        if (removed) {
            count--;
        }

        return removed;
    }
}