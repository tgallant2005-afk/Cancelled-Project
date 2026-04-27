//this class represents criteria that are met if the course passed in is of the set subject
public class SubjectCriteria extends Criteria {

    public SubjectCriteria(String criteria, boolean required) {
        super(criteria, required);
    }

    public Criteria criteriaMet(Course toCheck) {
        Criteria returning = null;
        boolean met = false;
        if (toCheck != null && super.getCriteria() != null) {
            met = super.getCriteria().equalsIgnoreCase(toCheck.getCourseSubject());
        }

        if (met) {
            returning = this;
        }

        return returning;
    }

}