import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BrokenAppendTest {
    public static void main(String[] args) {
        try {
            String content = "This is the content to write into file";
            BufferedWriter bw = new BufferedWriter(new FileWriter("Data.txt", true));
            System.out.println("Done");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}