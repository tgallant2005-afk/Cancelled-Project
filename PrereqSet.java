//class holds a set of items representing a bracket of a courses prereqs

import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

public class PrereqSet extends PrereqSuper {
    public static final String[] TYPES = { "and", "or" };
    public static final String HOURS_PREREQ = "hours";
    private boolean[] prereqsMet;
    private ArrayList prereqs;
    // private String prereqType;

    public PrereqSet(Stack<Objects> prereqs, String type) {
        super(null);
        if (type != null && (type.equals(TYPES[0]) || type.equals(TYPES[1]))) {
            super.setPrereq(type);
        } else {
            System.out.println(
                    "one of the prereq sections was sent something other than an \"and\" or \"or\", defaulting to and");
            super.setPrereq(TYPES[0]);
        }

        if (prereqs != null) {
            this.prereqs = new ArrayList<PrereqSuper>();
            for (int i = 0; !prereqs.empty(); i++) {
                Object testing = prereqs.pop();
                if (testing instanceof String) {
                    String using = (String) testing;
                    // if the item of the stack passed in is a string, stores as a single prereq
                    if (using.contains(HOURS_PREREQ)) {
                        this.prereqs.add(new CreditHourPrereq(using));
                    } else {
                        this.prereqs.add(new SinglePrereq((String) testing));
                    }
                } else if (testing instanceof PrereqSuper) {
                    this.prereqs.add((PrereqSuper) testing);
                }
            }
        }

        prereqsMet = new boolean[this.prereqs.size()];
    }

    public boolean addPrereq(Course toAdd) {
        boolean added = false;

        PrereqSuper currItem = null;
        if (toAdd != null) {
            for (int i = 0; i < prereqs.size() && !added; i++) {
                currItem = prereqs.get(i);
                if (currItem != null) {
                    added = currItem.addPrereq(toAdd);
                    if (added) {
                        prereqsMet[i] = currItem.prereqMet();
                    }
                }
            }
        }

        return added;
    }

    public boolean removePrereq(Course toRemove) {
        boolean removed = false;
        PrereqSuper currItem = null;
        if (toRemove != null) {
            for (int i = 0; i < prereqs.size() && !removed; i++) {
                currItem = prereqs.get(i);
                if (currItem != null) {
                    removed = currItem.removePrereq(toRemove);
                    if (removed) {
                        prereqsMet[i] = currItem.prereqMet();
                    }
                }
            }
        }

        return removed;
    }

    public boolean prereqMet() {
        boolean met;
        if (super.getPrereq().equals(TYPES[0])) {
            met = true;
            for (int i = 0; i < prereqsMet.length && met; i++) {
                met = prereqsMet[i];
            }
        } else {
            // since the constructor checks to make sure only and or or are added,
            // this only runs if "or" is passed in
            met = false;
            for (int i = 0; i < prereqsMet.length && !met; i++) {
                met = prereqsMet[i];
            }
        }

        return met;
    }

}