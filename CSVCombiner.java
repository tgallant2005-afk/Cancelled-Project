
//takes x amount of csv files as input, and then orderes their data row by row following alphabetical order, starting from the item in the first col, then second, etc
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CSVCombiner {
    public static void main(String[] args) {
        // argument 1 is output file name, 2 is first file combining, 3rd is second.
        BufferedReader br;
        BufferedReader br2;
        BufferedWriter bw;
        Scanner scnr = new Scanner(System.in);
        String output;
        String input1; // strings representing the current step's output file and the first input file
        if (args.length >= 3) { // if given enough inputs
            try {
                if (args.length == 3) {
                    output = args[0];
                } else {
                    output = "temp.txt";
                } // if there are 3 inputs, set output to wanted output file, if more, then set
                  // output to a temp
                input1 = args[1];

                for (int i = 2; i < args.length; i++) {
                    // while there are files to combine from input
                    bw = new BufferedWriter(new FileWriter(output, true));
                    br = new BufferedReader(new FileReader(input1));
                    br2 = new BufferedReader(new FileReader(args[2]));
                    // create readers and writers, then sent to combination method
                    combineFiles(bw, br, br2);
                    input1 = output; // set the next first input to the current output
                    if (i + 2 == args.length) {
                        output = args[0];
                        // if the next input2 would be the last, set output to the wanted output file
                    } else {
                        // otherwise, alternate between temp files
                        if (i % 2 == 0) {
                            output = "temp2.txt";
                        } else {
                            output = "temp.txt";
                        }
                    }
                    bw.close();
                    br.close();
                    br2.close();
                    // close buffered writer and readers
                }

            } catch (Exception e) {
                System.out.println("Exception occured: " + e);
            }
        } else {
            System.out.println(
                    "Not enough files or names passed. format (without brackets): (outputFileName) (input1.whatever) (input2.whatever)");
        }

        scnr.close(); // close scanner after
    }

    private static void combineFiles(BufferedWriter bw, BufferedReader br, BufferedReader br2) throws IOException {
        String brNext;
        String br2Next;
        if (bw != null && br != null && br2 != null) {
            brNext = br.readLine();
            br2Next = br2.readLine();
            while (brNext != null && br2Next != null) {
                if (brNext.compareTo(br2Next) <= 0) {
                    bw.write(brNext);
                    if (brNext.compareTo(br2Next) == 0) {
                        br2Next = br.readLine();
                    }
                    brNext = br.readLine();
                } else {
                    bw.write(br2Next);
                    br2Next = br2.readLine();
                }
                bw.write("\n");
            }

            while (brNext != null) {
                bw.write(brNext);
                brNext = br.readLine();
            }

            while (br2Next != null) {
                bw.write(br2Next);
                br2Next = br2.readLine();
            }
        }
    }
}