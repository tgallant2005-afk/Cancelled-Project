
//reads in a course from the U of M course catalogue
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class ClassReader {
    public static final String DEFAULT_URL = "https://catalog.umanitoba.ca/undergraduate-studies/course-descriptions/";
    private String websiteURL;
    private static final String[] DESC_SECTIONS = { "Prerequisite", "Equiv To", "Mutually Exclusive", "Attributes" };
    // sections that can exist under each course in the catalogue
    public static final String THREE_NUMBER_REGEX = ".*\\d.*\\d.*\\d.*";
    // regex to check if there is 3 or more numbers in string, used for checking if
    // course designation exists in string

    // sets the url to be reading from
    public ClassReader(String websiteURL) {
        if (websiteURL != null) {
            this.websiteURL = websiteURL;
        } else {
            this.websiteURL = DEFAULT_URL;
        }
    }

    // verify the class name/code given is valid
    // private boolean verifyName(String className) {
    // boolean valid = false;
    // String[] nameParts;
    // if (className != null) {
    // nameParts = (className.trim()).split(" ");
    // if (nameParts.length == 2) {
    // valid = ((nameParts[0].length() == 3 || nameParts[0].length() == 4) &&
    // (nameParts[1].length() == 4));
    // if (valid) {
    // for (int i = 0; i < 4 && valid; i++) {
    // valid = Character.isDigit(nameParts[1].charAt(i));
    // }
    // }
    // }
    // }

    // return valid;
    // }

    // changes the given URL reading in from
    public void changeWebsiteURL(String newURL) {
        if (newURL != null) {
            websiteURL = newURL;
        }
    }

    // verifies that the website title contains the course designation the user is
    // asking for (COMP, GEOL, etc.)
    // and if not modifies the URL to contain it
    private String verifyCourseURL(String courseDesig) {
        String newURL = websiteURL;
        if (newURL != null && courseDesig != null) {
            int slashIndex = (websiteURL.substring(0, websiteURL.length())).lastIndexOf("/");
            newURL = (websiteURL.substring(0, slashIndex));
            if (courseDesig.equalsIgnoreCase(newURL.substring(newURL.length() - courseDesig.length()))) {
                // if the url does properly end in the correct course name, return the "/" at
                // the end
                newURL += "/";
            } else {
                // otherwise, use the default and set it to the correct course name
                System.out.println("url stored did not end in " + courseDesig + "/, so modifying default");
                newURL = DEFAULT_URL + courseDesig + "/";
            }
        } else {
            System.out.println("either url or course designation passed in is NULL");
        }

        return newURL;
    }

    // given a class name and designation (i.e. GEOL 2350)
    public String[] readClass(String className) {
        String[] courseInfo = new String[4];
        String urlUsing;
        // array holding info of course in order of:
        // any prerequisite courses
        // any equivalent courses
        // mutually equivalent courses
        // any attributes of the course
        if (!PrereqSuper.validCourse(className)) {
            System.out.println("Class name entered, " + className + "is not a valid className");
        } else {
            String[] nameParts = className.split(" ");
            urlUsing = verifyCourseURL(nameParts[0]);
            try {
                URL test = new URL(urlUsing);
                URLConnection URLTest = test.openConnection();
                Scanner scnr = new Scanner(URLTest.getInputStream());
                String curr = scnr.nextLine();
                // connect to the website and start reading the html line by line
                int index = -1;
                while (index == -1 && curr != null) {
                    curr = scnr.nextLine();
                    index = curr.indexOf("<strong>" + className + "</strong>");
                    // read until the course section is found in the html
                }

                String classInfo = curr.substring(index);
                int attribIndex = classInfo.indexOf(DESC_SECTIONS[3]); // index of the attribute
                int nextClassInd = curr.indexOf("<strong>" + (className.substring(0, 4)));
                // each line of the html can include multiple classes, so done to make sure that
                // any section is actually taken from the proper class
                for (int i = 0; i < 3; i++) {
                    int infoIndex = classInfo.indexOf(DESC_SECTIONS[i]);
                    // index of the info looking for
                    if (nextClassInd == -1 || (-1 < infoIndex && infoIndex < nextClassInd)) {
                        courseInfo[i] = classInfo.substring(infoIndex,
                                (classInfo.substring(infoIndex)).indexOf("</span>"));

                        // if there is no next course in the classinfo data, or the next section is
                        // between this and the next course, then get said sections data
                    }
                }
            } catch (Exception e) {
                System.out.println("Exception has occured: " + e);
            }
        }

        return courseInfo;
    }
}