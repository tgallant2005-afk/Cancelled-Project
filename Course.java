import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Course implements DegreeRequir {
    private final static String FILE_WITH_COURSE_TYPES = "UofMSubjectToCode.txt";

    private String courseName = "";
    private String section = "";
    private String courseTitle = "";
    private int creditHours = 0;
    private String campus = "";
    private ArrayList<DateAndTime> dAndTS = null;
    private String instructor = "";
    private PrereqSet prereqs;
    private String[] catalogueInfo; // equivalent courses, mutually exclusive courses, and then course attributes
    private Criteria superCriteria = null;
    private Criteria criterFurfilling = null; // which, if any, non-specific requirement of a degree
    // the course is fulfilling
    private String courseSubject; // represents the subject the course is from, I.E. "microbiology",
    // "Accounting", etc.

    // data in components should be in order of:
    // course name, course section, title (which should include credit hours in
    // brackets),
    // campus, date course spans, time course is in the day, and then instructor, if
    // none, leave as blank

    public Course() {
        courseName = "";
        section = "";
        courseTitle = "";
        creditHours = 0;
        campus = "";
        dAndTS = null;
        instructor = "";
        prereqs = null;
        catalogueInfo = null;
        superCriteria = null;
        criterFurfilling = null;
        courseSubject = "";
    }

    public Course(String[] components, PrereqSet prereqs, String[] catalogueInfo) {
        if (setupCourseInfo(components)) {
            this.prereqs = prereqs;
            this.catalogueInfo = catalogueInfo;
        } else {
            Course();
            this.prereqs = null;
            this.catalogueInfo = null;
        }
    }

    public Course setCourseInfo(String[] components) {
        if (courseName.equals("") && !setupCourseInfo(components)) {
            DegreeRequir tempRequirHold = requirFurfilling;
            String[] tempCataInfo = catalogueInfo;
            String tempType = courseSubject;

            Course();
            criterFurfilling = tempRequirHold;
            catalogueInfo = tempCataInfo;
            courseSubject = tempType;
        }
    }

    private boolean setupCourseInfo(String[] components) {
        boolean validCourse = true;
        if (components != null && components.length >= 6) {
            validCourse = verifyCourseName(components[0]);
            validCourse = validCourse && verifySection(components[1]);
            validCourse = validCourse && setupCreditHours(components[2]);
            if (validCourse) {
                courseName = components[0];
                section = components[1];
                // don't have [2] here since setup credit hours both sets and verifies [2]
                campus = components[3];
                dAndTS = new ArrayList<DateAndTime>();
                DateAndTime newDAndT = validDateAndTime(components[4], components[5]);
                if (newDAndT != null) {
                    // if the date and time added was valid, then continue adding things
                    dAndTS.add(newDAndT);
                    instructor = components[6];
                    setcourseSubject();
                } else {
                    validCourse = false;
                    System.out.println("date and time in course data were invalid, date and time not being added");
                }

            } else {
                System.out.println("course data passed in was invalid, setting up default values");
            }

            // courseName = components[0];
            // setcourseSubject();
            // if (verifySection(components[1])) {
            // section = components[1];
            // }
            // }
            // if (componentsLength > 1 && verifySection(components[1])) {
            // section = components[1];
            // }
            // if (componentsLength > 2) {
            // int openBracIndex;
            // int closedBracIndex;
            // boolean validCR = true;
            // if (components[2] != null) {
            // openBracIndex = components[2].lastIndexOf("(");
            // closedBracIndex = components[2].lastIndexOf(")");
            // if (openBracIndex > 0 && closedBracIndex > openBracIndex) {
            // for (int i = openBracIndex; i < closedBracIndex && validCR; i++) {
            // validCR = Character.isDigit(components[2].charAt(i));
            // }
            // if (validCR) {
            // creditHours = Integer.parseInt(components[2].substring(openBracIndex + 1,
            // closedBracIndex));
            // courseTitle = components[2].substring(0, openBracIndex);
            // }
            // }
            // }
            // }
            // if (componentsLength > 3) {
            // campus = components[3];
            // }
            // if (componentsLength > 5) {
            // dAndTS = new ArrayList<DateAndTime>();
            // DateAndTime newDAndT = validDateAndTime(components[4], components[5]);
            // if (newDAndT != null) {
            // dAndTS.add(newDAndT);
            // }
            // }
            // if (componentsLength > 6) {
            // instructor = components[6];
            // }
        } else {
            validCourse = false;
        }

        return validCourse;
    }

    // get the credit hours from the given course title
    private boolean setupCreditHours(String title) {
        int openBracIndex;
        int closedBracIndex;
        boolean validCR = true;
        if (title != null) {
            openBracIndex = title.lastIndexOf("(");
            closedBracIndex = title.lastIndexOf(")");
            if (openBracIndex > 0 && closedBracIndex > openBracIndex) {
                for (int i = openBracIndex; i < closedBracIndex && validCR; i++) {
                    validCR = Character.isDigit(title.charAt(i));
                }
                if (validCR) {
                    creditHours = Integer.parseInt(title.substring(openBracIndex + 1, closedBracIndex));
                    courseTitle = title;
                }
            }
        }

        return validCR;
    }

    public boolean checkAttributes(String toCheck) {
        boolean exists = false;
        String[] attributes;

        if (toCheck != null && catalogueInfo.length >= 3) {
            attributes = catalogueInfo[2].split(",");
            for (int i = 0; i < attributes.length && !exists; i++) {
                exists = attributes[i].equalsIgnoreCase(toCheck);
            }
        }
    }

    private void setcourseSubject() {
        boolean typeFound = false;
        String[] nameComponents = courseName.split(" ");
        String currLine;
        String[] lineSplit;
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_WITH_COURSE_TYPES));
            currLine = br.readLine();
            while (currLine != null && !typeFound) {
                lineSplit = currLine.split("\t");
                if (lineSplit[1].equalsIgnoreCase(nameComponents[0])) {
                    courseSubject = lineSplit[0];
                    typeFound = true;
                }
            }

            if (!typeFound) {
                courseSubject = "";
            }
        } catch (Exception e) {
            System.out.println("An exception has occured: " + e);
        }
    }

    public addNewDateAndTime(String component1, String component2){
        if(component1 != null && component2 != null){
            DateAndTime newDAndT = validDateAndTime(component1, component2);
            if(newDAndT != null){
                dAndTS.add(newDAndT);
            }
        }
    }

    private DateAndTime validDateAndTime(String component1, String component2) {
        DateAndTime newDAndT = new DateAndTime(component1, component2);
        if (!newDAndT.ValidTime()) {
            newDAndT = null;
        }
        return newDAndT;
    }

    public int getCreditHours() {
        return creditHours;
    }

    private boolean verifySection(String sectionChecking) {
        boolean valid = false;
        String[] parts;
        String code;
        if (sectionChecking != null) {
            parts = sectionChecking.split(" ");
            if (parts.length == 2) {
                valid = (parts[0].length() == 3) && (Character.isLetter(parts[0].charAt(0)));
                valid = valid && (Character.isDigit(parts[0].charAt(1))) && (Character.isDigit(parts[0].charAt(1)));
                if (valid) {
                    code = parts[1].replaceAll("()", "");
                    valid = code.length() == 5;
                    for (int i = 0; i < 4 && valid; i++) {
                        Character.isDigit(code.charAt(i));
                    }
                }
            }
        }

        return valid;
    }

    public static boolean verifyCourseName(String toCheck) {

        boolean valid = false;
        String[] nameParts;
        if (toCheck != null) {
            nameParts = (toCheck.trim()).split(" ");
            if (nameParts.length == 2) {
                valid = ((nameParts[0].length() == 3 || nameParts[0].length() == 4) && (nameParts[1].length() == 4));
                if (valid) {
                    for (int i = 0; i < 4 && valid; i++) {
                        valid = Character.isDigit(nameParts[1].charAt(i));
                    }
                }
            }
        }

        return valid;
    }

    public String getCourseName() {
        return courseName;
    }

    // // returns the course code (BIOL, ACC, etc.)
    // public String getCourseCode() {
    // return courseName.split(" ")[0];
    // }

    // public String getCourseLevel(){

    // }

    public String getCourseSubject() {
        return courseSubject;
    }

    // A course is considered to be equal (i.e. return 0) if it shares the same name
    public int compareTo(Course otherCourse) {
        int returning;
        if (courseName == null) {
            returning = -1;
        } else if (otherCourse == null || otherCourse.getCourseName() == null) {
            returning = 1;
        } else {
            returning = courseName.compareToIgnoreCase(otherCourse.getCourseName());
        }

        return returning;
    }

    public Criteria getCriteriaFurfilling() {
        return criterFurfilling;
    }

    public boolean changeCriteriaFurfilling(Criteria toChange) {

        boolean replace = (toChange == null) || (toChange.criteriaMet(this) == toChange);
        // replace the current criteria if the passed in critera is either null
        // (means removing the current criteria) or if the new criteria is valid

        if (replace) {
            if (criterFurfilling != null) {
                // if the current criteria the class is fulfilling isn't null,
                // then remove it from that criteria
                criterFurfilling.removeCourse(this);
                superCriteria = null;
            }

            criterFurfilling = toChange;
            if (toChange != null) {
                superCriteria = toChange.getTopCriteria();
            }
        }

        return replace;
    }

}