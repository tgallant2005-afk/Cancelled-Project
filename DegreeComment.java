//represents "comments"/sections in degrees that aren't a course, like "certain number of elective hours"
//or x credit hours from ____ courses at 3000 level

public interface DegreeComment {

    public void addCourse(Course toAdd);

    public void removeCourse(Course toRemove);
}