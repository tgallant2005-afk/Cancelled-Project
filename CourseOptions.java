import java.util.ArrayList;

public class CourseOptions {
    private boolean[] met;
    private String[] options;
    private int separation = 1; // for a number x, will consider every x courses to be needed together
    // i.e. if x = 2, then your options are the first 2 courses stored, then the
    // next 2, etc.
    // a 1 will consider only one course needed, I.e. an OR of the courses
    // and a # >= total courses in options considers all courses needed, I.e. an AND
    // of the courses

    public CourseOptions(ArrayList<String> courses, int seperation) {
        setSeparation(seperation);
        Course current;
        boolean found = false;
        int compareResult;

        if (courses != null) {

            for (int i = 0; i < courses.size(); i++) {
                if (!Course.verifyCourseName(courses(i))) {
                    courses.remove(i);
                    i--;
                }
            }

            options = new String[courses.size()];
            met = new boolean[courses.size()];

            options[0] = courses.get(0);
            for (int i = 1; i < courses.size(); i++) {
                current = courses.get(i);
                for (int j = i; j > 0 && !found; j--) {
                    compareResult = current.compareTo(options[j - 1]);
                    if (compareResult < 0) {
                        options[j] = options[j - 1];
                    } else {
                        found = true;
                        if (compareResult > 0) {
                            options[j] = current;
                        }
                    }

                    if (!found) {
                        options[0] = current;
                    }
                    found = false;
                }
            }
        }

    }

    // sets the separation value. if invalid value added (< 1), remains unchanged
    public void setSeparation(int newVal) {
        if (newVal < 1) {
            System.out.println(newVal + "is under 1, separation remaining unchanged");
        } else {
            separation = newVal;
        }
    }

    public boolean canAdd(Course toCheck) {
        boolean addable = false;
        String name;
        int index;

        if (toCheck != null) {
            name = toCheck.getCourseName();
            index = BinarySearch(name);
            if (index != -1 && name.compareTo(options[index]) == 0) {
                addable = (!met[index]);
            }
        }

        return addable;
    }

    public boolean addCourse(Course toAdd) {
        boolean added = false;
        String name;
        int index;

        if (toAdd != null) {
            name = toAdd.getCourseName();
            index = BinarySearch(name);

            if (index != -1 && name.compareTo(options[index] == 0)) {
                added = true;
                met[index] = true;
            }
        }

        return added;
    }

    public boolean removeCourse(Course toRemove) {
        boolean removed = false;
        String name;
        int index;

        if (toRemove != null) {
            name = toRemove.getCourseName();
            index = BinarySearch(name);

            if (index != -1 && name.compareTo(options[index] == 0)) {
                removed = true;
                met[index] = false;
            }
        }
    }

    public boolean metOptions() {
        boolean metOptions = false;
        boolean currentSet = true;

        for (int i = 0; i < met.length && !metOptions; i = i + separation) {
            for (int j = i; j < met.length && j < i + separation && currentSet; j++) {
                currentSet = currentSet && met[j];
            }

            metOptions = currentSet;
            currentSet = true;
        }

        return metOptions;
    }

    // adds a course as an option if not already included
    public void addOption(String option) {
        int index;
        int compareResult = 0;
        String[] newStrArray;
        boolean[] newBoolArray;

        if (Course.verifyCourseName(option)) {

            index = BinarySearch(option);

            if (index != -1) {
                compareResult = option.compareTo(options[index]);
                if (compareResult == 0) {
                    index = -1;
                } else {
                    // dividing result by absolute of itself will give either -1 or 1,
                    // if -1 then result was -ve and so course adding should be infront in array
                    // (index - 1), and if +ve should be after (index + 1)
                    index = index + (compareResult / (int) Math.abs(compareResult));
                }
            }

            if (index != -1) {

                newStrArray = new String[options.length + 1];
                newBoolArray = new boolean[options.length + 1];
                for (int j = 0; j < index; j++) {
                    newStrArray[j] = options[j];
                    newBoolArray[j] = met[j];
                }
                newStrArray[index] = option;
                for (int k = index + 1; k < newArray.length; k++) {
                    newStrArray[k] = options[k - 1];
                    newBoolArray[k] = met[k - 1];
                }

                options = newStrArray;
                met = newBoolArray;
            }
        }
    }

    public void removeOption(String option) {
        int index;
        String[] newStrArray;
        boolean[] newBoolArray;

        if (Course.verifyCourseName(option)) {
            index = BinarySearch(option);

            if (index != -1 && option.compareTo(options[index]) == 0) {
                newStrArray = new String[options.length + 1];
                newBoolArray = new boolean[options.length + 1];
                for (int j = 0; j < index; j++) {
                    newStrArray[j] = options[j];
                    newBoolArray[j] = met[j];
                }
                for (int k = index; k < newArray.length; k++) {
                    newStrArray[k] = options[k + 1];
                    newBoolArray[k] = met[k + 1];
                }

                options = newStrArray;
                met = newBoolArray;
            }
        }
    }

    // returns the index that the course COULD be at, aka when the low and high meet
    // this is done so that both the add and remove methods can use it, with the
    // delete continuing if a compare to with the course at that index is 0, add
    // continues if not 0
    private int BinarySearch(String toFind) {
        int low = 0;
        int high = options.length - 1;
        int middle;
        int compareResult;
        int index = -1;

        if (toFind != null) {
            while (low < high && index == -1) {
                middle = (low + high) / 2;
                compareResult = toFind.compareTo(options[middle]);
                if (compareResult < 0) {
                    high = middle - 1;
                } else if (compareResult > 0) {
                    low = middle + 1;
                } else {
                    index = middle;
                }
            }

            if (low == high) {
                index = low;
            }

        }

        return index;
    }

    // // using compareTo, finds index of where course would be in array
    // private int findCompareIndex(Course toFind) {
    // int compareResult = 1;
    // int index = -1;
    // if (toFind != null) {
    // for (int i = 0; i < options.length && compareResult > 0; i++) {
    // compareResult = toFind.compareTo(options[i]);
    // index = i;
    // }

    // if (compareResult > 0) {
    // index++;
    // }
    // }
    // return index;
    // }
}