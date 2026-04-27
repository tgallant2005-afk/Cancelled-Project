//used to create objects based on "comment" in degree requirements (i.e. x electives or x ____ courses at 3000 level)

import java.util.ArrayList;

public class DegreeCommentProcessor {

    private static String[] SIGNIFIERS = { "level", "Faculty of" };

    // note: a CH < 0 means to check for Credit Hours in the passed comment
    public Criteria processComment(String passedComment, ArrayList<Course> options, int CH) {
        Criteria newComment = null;

        if (passedComment != null) {
            if (CH < 0) {
                CH = getCH(passedComment);
            }

            if (CH > 0) {

                newComment = processCreditHours(passedComment, options, CH);
            }
            // if (passedComment.contains("credit hours")) {
            // newComment = processCreditHours(passedComment, options);
            // }
        }

        return newComment;
    }

    // note: a CH < 0 means to check for Credit Hours in the passed comment
    private Criteria processCreditHours(String passedComment, ArrayList<Course> options, int CH) {
        Criteria newComment = null;
        boolean required = true; // if criteria is required
        int priority = 1; // priority of criteria for deciding what criteria a course should go to
        int setSize = 1; // for classes with multiple courses contained, setSize 1 = or, setSize (total
                         // courses) = and,
        // between (say x) is met if the first x are met, or the next x, etc for all
        // sets

        // int CH = getCH(passedComment);

        if (passedComment.charAt(passedComment.length() - 1) == ':') {
            // if the last character if the comment is a semi-colon, then it's one with
            // courses after
            newComment = new MultiCourseCHCriteria(passedComment, CH, required, priority, options,
                    setSize);
        } else {
            String signifier = getCommentSignifier(passedComment);
            if (signifier.equals(SIGNIFIERS[0])) {
                String levels = "";
                int levelIndex = passedComment.indexOf("000");
                int endIndex = passedComment.indexOf(SIGNIFIERS[0]);

                while (levelIndex != -1) {
                    levels = levels + passedComment.charAt(levelIndex - 1);
                    levelIndex = passedComment.indexOf("000", levelIndex + 1);
                    if (levelIndex < endIndex) {
                        if (levelIndex > 0) {
                            levels = levels + ",";
                        }
                    } else {
                        levelIndex = -1;
                    }
                }

                newComment = new LevelCHCriteria(passedComment, CH, required, priority, options, levels);
            }
        }

        return newComment;
    }

    // A lot of comments have some signifier/unique part that can be used to
    // identify it, like
    // credit hour comments have "credit hour", ones with X000 have "level", etc.
    // this method finds which one comes first since comments can contain multiple
    // comments (i.e. 24 credit hours of BIOL including 15 at 4000 level)
    // and returns it, allowing the processing methods to choose the correct
    // criteria class
    private String getCommentSignifier(String passedComment) {
        String signifier = null;
        int index;
        int tempIndex = 0;

        if (passedComment != null) {
            index = passedComment.length();

            for (int i = 0; i < SIGNIFIERS.length; i++) {
                tempIndex = passedComment.indexOf(SIGNIFIERS[i]);
                if (tempIndex > -1 && tempIndex < index) {
                    index = tempIndex;
                    signifier = SIGNIFIERS[i];
                }
            }
        }

        return signifier;
    }

    private int getCH(String passedComment) {
        int CH = 0;
        boolean found = false;
        String[] parts = passedComment.split(" ");

        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equalsIgnoreCase("credit") && i > 0) {
                CH = Integer.parseInt(parts[i - 1]);
            }
        }

        return CH;
    }
}