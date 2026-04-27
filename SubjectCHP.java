public abstract class SubjectCHP extends CreditHourPrereq {

    private String[] factors;
    // represents the "Factor" that this prereq needs, for EX: 15 credit hours of
    // microbiology courses, or from the faculty of arts

    public SubjectCHP() {
        super();

        subject = null;
    }
}