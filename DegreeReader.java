import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

public class DegreeReader {

    private static final String defaultURL = "https://catalog.umanitoba.ca/";
    private URLConnection DegreeConnection;
    private ArrayList<DegreeRequir> degreeRequirements;

    public DegreeReader(String DegreeURL) {
        try {
            URL urlOfDegree;
            // Scanner scnr;
            // String curr;
            if (DegreeURL != null && DegreeURL.contains(defaultURL)) {
                urlOfDegree = new URL(DegreeURL);
                DegreeConnection = urlOfDegree.openConnection();
                // scnr = new Scanner(DegreeConnection.getInputStream());
                // curr = scnr.nextLine();

                // while (!curr.contains("<tbody>")) {
                // curr = scnr.nextLine();
                // }

                // do {
                // System.out.println(curr);
                // curr = scnr.nextLine();
                // } while (curr != null && (curr.contains("class=\"")));
            }
        } catch (Exception e) {
            System.out.println("exception found: " + e);
        }
    }

    public void readDegree() {
        Scanner scnr;
        String curr;
        String course;
        String courseName;
        int courseNameIndex;
        ClassReader courseCatalog = new ClassReader(ClassReader.DEFAULT_URL);
        PrereqSplitter prereqSplitter = new PrereqSplitter();
        degreeRequirements = new ArrayList<DegreeRequir>();
        String[] courseInfo;
        Course nextCourse;
        if (DegreeConnection != null) {
            scnr = new Scanner(DegreeConnection.getInputStream());
            curr = scnr.nextLine();

            while (!curr.contains("<tbody>")) {
                curr = scnr.nextLine();
            }

            curr = scnr.nextLine();
            do {
                courseNameIndex = curr.indexOf("this, \'");
                if (courseNameIndex != -1) {
                    course = curr.substring(courseNameIndex + 1, curr.indexOf("\'", courseNameIndex + 2));
                    courseName = (course.split(course))[0];
                    courseInfo = courseCatalog.readClass(courseName);
                    nextCourse = new Course(courseCatalog.readClass(courseName),
                            prereqSplitter.splitPrereqs(courseInfo[0]), nonPrereqInfo(courseInfo));
                }
                curr = scnr.nextLine();

            } while (curr != null && (curr.contains("class=\"")));
        }
    }

    // classReader returns a String[] of 4 items, but the first is only needed for
    // the creation of the courses prereq set, so this just makes a string[] not
    // containing the prereq string
    private String[] nonPrereqInfo(String[] courseInfo) {
        String[] NPCourseInfo = null;// nonPrereqCourseInfo
        if (courseInfo != null) {
            NPCourseInfo = new String[courseInfo.length - 1];
            for (int i = 0; i < NPCourseInfo.length; i++) {
                NPCourseInfo[i] = courseInfo[i + 1];
            }
        }

        return NPCourseInfo;
    }
}