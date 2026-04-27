import java.io.BufferedReader;
import java.io.FileReader;

public class FacultyAndTypeSplitTest {
    public static void main(String[] args) {
        boolean typeFound = false;
        // String[] nameComponents = courseName.split(" ");
        String currLine;
        String[] lineSplit;

        try {
            BufferedReader br = new BufferedReader(new FileReader("UofMSubjectToCode.txt"));
            currLine = br.readLine();
            while (currLine != null && !typeFound) {
                lineSplit = currLine.split("\t");
                for (int i = 0; i < lineSplit.length; i++) {
                    System.out.println(lineSplit[i]);
                }
                currLine = br.readLine();
            }
            Thread.sleep(1000);

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}