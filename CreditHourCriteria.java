//this represents a credit hour based version of the limit criteria, where the limit represents

public class CreditHourCriteria extends LimitCriteria {

    private int totalCH = 0;

    public CreditHourCriteria(String criteria, int limit, boolean required, int priority) {
        super(criteria, limit, required, priority);
    }

    public int getTotalCH() {
        return totalCH;
    }

    public Criteria criteriaMet(Course toCheck) {
        Criteria met = null;

        if (toCheck != null && super.getLimit() > totalCH + toCheck.getCreditHours()) {
            met = super.criteriaMet(toCheck);

            if (met == null) {
                met = this;
            }
        }

        return met;
    }

    public boolean addCourse(Course toAdd) {
        boolean added = false;

        if (toAdd != null && super.getLimit() > totalCH + toAdd.getCreditHours()) {
            added = super.addCourse();

            if (added) {
                totalCH += toAdd.getCreditHours();
            }
        }

        return added;
    }

    public boolean removeCourse(Course toRemove) {
        boolean removed = false;

        removed = super.removeCourse(toRemove);

        if (removed) {
            totalCH -= toRemove.getCreditHours();
        }

        return removed;
    }

}