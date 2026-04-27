public class ElectiveComment extends CreditHourPrereq {

    public ElectiveComment(String prereq) {
        super(prereq);
    }

    public void addCourse(Course toAdd) {
        if (toAdd != null && toAdd.getCommentFurfilling() == null) {
            super.addCourse(toAdd);
        }
    }

    public void removeCourse(Course toRemove) {
        if (toRemove != null && toRemove.getCommentFurfilling() == this) {
            super.removeCourse(toRemove);
        }
    }
}