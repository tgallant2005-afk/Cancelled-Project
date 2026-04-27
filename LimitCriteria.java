//represents criteria that can only happen a set number of times (i.e. a criteria of a certain course can only be met once)
//if needed/only wanting a count of how many courses, a limit of 0 will count as no limit
public abstract class LimitCriteria extends Criteria {

    private int limit;

    public LimitCriteria(String criteria, int criteriaLimit, boolean required, int priority) {
        super(criteria, required, priority);
        if (criteriaLimit < 0) {
            limit = 0;
        } else {
            limit = criteriaLimit;
        }

    }

    public int getLimit() {
        return limit;
    }

}