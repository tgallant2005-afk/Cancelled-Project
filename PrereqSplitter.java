//instances of this class take in a string of the html version of a courses list of prereques (produced
//in ClassReader class), and produce a set of and and or objects that represent said courses prereq requirements

import java.util.Objects;
import java.util.Stack;

public class PrereqSplitter {

    private int lastRecurEndBrac; // int that holds the index of the end bracket found by the last run
    // of splitPrereqRecur

    // method splits the string of prereqs into a string of specifically the prereqs
    // themselves
    public PrereqItem splitPrereqs(String prereqs) {
        String allPrereqs;
        int start;
        // Stack
        // char currChar;
        if (prereqs != null) {
            start = prereqs.indexOf(":");
            allPrereqs = prereqs.split(start);
            return splitPrereqRecur(allPrereqs);
            // brackets = new Stack<Character>();
            // for (int i = 0; i < allPrereqs.length(); i++) {
            // currChar = allPrereqs.charAt(i);
            // if (currChar == '[' || currChar == '(') {
            // brackets.push(currChar);
            // }
            // }
        }
    }

    // recursive function for splitting the prereq string
    // into the and and or objects,
    // goes recursively down each time it hits an opening bracket {( or [},
    // creating the and/or object
    // and returning back up when it reaches the closing bracket
    private PrereqItem splitPrereqRecur(String section) {
        Stack splits = new Stack<Integer>();
        int currSplit = 1;
        String prereqType; // either "or" if the prerequsites in this () (or entire thing) are or's,
        // (i.e. you only need one), or "and" if the prereques are and's (need all of
        // them)
        Stack prereqs = new Stack<Objects>();
        PrereqItem currPrereq; // holds the current item produced by a recursion down
        boolean endBracFound = false; // set to true when an end bracket is found in the string
        // boolean typeFound = false; // set to true if an and or an or is found in the
        // brackets
        char currChar;
        prereqItem currItem;

        if (section != null) {
            for (int i = 0; i < section.length() && !endBracFound; i++) {
                currChar = section.charAt(i);
                if (currChar == '(' || currChar == "[") {
                    currItem = splitPrereqRecur(section.substring((i + 1)));
                    if (currItem != null) {
                        prereqs.push(currPrereq);
                    }
                    i = lastRecurEndBrac;
                    // after recursively doing bracket section, set i to end so next loop is char
                    // after
                } else if (currChar == 'o' && section.length() > i + 1) {
                    if (section.charAt(i + 1) == 'f') {
                        prereqType = "of";
                        prereqs.push(section.substring(currSplit, i + 2));
                        currSplit = i + 2;
                    }
                } else if (currChar == 'a' && section.length() > i + 2) {
                    if (section.charAt(i + 1) == 'n' && section.charAt(i + 2) == 'd') {
                        prereqType = "and";
                        prereqs.push(section.substring(currSplit, i + 3));
                        currSplit = i + 3;
                    }
                } else if (currChar == ')' || currChar == ']') {
                    endBracFound = true;
                    lastRecurEndBrac = i;
                }
            }

            return new PrereqSet(prereqs, prereqType);
        }
    }
}