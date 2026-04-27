import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

public class RequirTypes {

    private static String defaultURL = "https://catalog.umanitoba.ca/";
    private static BufferedWriter titleWriter;
    private static ArrayList titles = new ArrayList<String>();
    private static BufferedWriter contentWriter;
    private static ArrayList content = new ArrayList<String>();

    public static void main(String[] args) {

        try {
            // titleWriter = new BufferedWriter(new FileWriter("catalogueTitles.txt"));
            // titleWriter.write("\n");
            // contentWriter = new BufferedWriter(new FileWriter("catalogueContent.txt"));
            // contentWriter.write("\n");
            // int fff = 1 / 0;
            String urlUsing = "https://catalog.umanitoba.ca/undergraduate-studies/";
            URL test = new URL(urlUsing);
            URLConnection URLTest = test.openConnection();
            Scanner scnr = new Scanner(URLTest.getInputStream());
            String curr = scnr.nextLine();
            String testing = "";
            // connect to the website and start reading the html line by line
            while (curr != null) {
                curr = scnr.nextLine();
                // System.out.println("\nspace\n");
                if (curr.contains("isparent")) {
                    System.out.println("\nspace\n");
                    // curr = scnr.nextLine();
                    testing = curr.substring(curr.indexOf("href=\"") + 6, curr.lastIndexOf("\">"));
                    System.out.print(testing + "\n");
                    System.out.println("blank");
                    // curr = scnr.nextLine();
                    if (curr.contains("Faculty")) {
                        System.out.println("next line does contain faculty");
                        System.out.println(curr + "\n");
                        System.out.println("\n\n" + defaultURL + testing + "\n\n");
                        helperMethod(defaultURL + testing);
                        System.out.println("helper ran?");
                        // outputTitlesAndSections(defaultURL + testing);
                        // int aei = 1 / 0;
                    } else {
                        System.out.println(curr + "\n");
                    }
                }
                // read until the course section is found in the html
            }

            // titleWriter.flush();
            // contentWriter.flush();
            // titleWriter.close();
            // contentWriter.close();
        } catch (Exception e) {
            System.out.println("Exception happened: " + e);
        }
    }

    private static void helperMethod(String urlToUse) {
        try {
            URL newURL = new URL(urlToUse);
            URLConnection urlReading = newURL.openConnection();
            Scanner scnr = new Scanner(urlReading.getInputStream());
            boolean foundNextSections = false;

            /// int ccc = 1 / 0;
            System.out.println("empty space for testing");
            String nextLine = scnr.nextLine();
            while (nextLine != null) {
                // int bbb = 1 / 0;
                // System.out.println("the while runs?");
                if (foundNextSections) {
                    // int aei = 1 / 0;
                    if (nextLine.contains("</ul>")) {
                        foundNextSections = false;
                    }
                    if (nextLine.contains("href=")) {
                        int indexUsing = nextLine.indexOf("href=");
                        String nextURLPiece = nextLine.substring(indexUsing + 6,
                                nextLine.indexOf("\"", indexUsing + 8));
                        System.out.println(nextURLPiece);
                        // int aei = 1 / 0;
                        outputTitlesAndSections(defaultURL + nextURLPiece);
                    }
                }

                if (nextLine.contains("nav leveltwo")) {
                    System.out.println(nextLine);
                    foundNextSections = true;
                }
                nextLine = scnr.nextLine();
                if (nextLine == null) {
                    System.out.println("nextLine is null");
                }
            }
            System.out.println();
        } catch (Exception e) {
            System.out.println("there was an exception in helper:" + e);
        }
    }

    private static void outputTitlesAndSections(String urlUsing) {
        try {
            URL newURL = new URL(urlUsing);
            URLConnection urlReading = newURL.openConnection();
            Scanner scnr = new Scanner(urlReading.getInputStream());
            boolean lengthMatch = false;
            int lengthOfclcah = "courselistcomment areaheader".length();
            titleWriter = new BufferedWriter(new FileWriter("catalogueTitles.txt", true));
            contentWriter = new BufferedWriter(new FileWriter("catalogueContent.txt", true));

            String nextLine = scnr.nextLine();
            // System.out.println("method ran");
            while (nextLine != null) {
                // System.out.println("while is running");
                // System.out.println(nextLine);
                if (nextLine.contains("courselistcomment areaheader")) {
                    // System.out.println("areaheader if ran");
                    int index2 = nextLine.indexOf("courselistcomment areaheader");
                    // System.out.println("nextLine: " + nextLine);
                    // System.out.println(nextLine.substring(index2 + lengthOfclcah + 3,
                    // nextLine.indexOf("</", index2 + lengthOfclcah + 3)));
                    String whytfNotWork = (nextLine.substring(index2 + lengthOfclcah + 3,
                            nextLine.indexOf("</", index2 + lengthOfclcah + 3)));
                    // System.out.println(whytfNotWork);
                    titleWriter.append(whytfNotWork + "\n");
                    titleWriter.flush();
                    // titleWriter.append((nextLine.substring(index2 + lengthOfclcah + 3,
                    // nextLine.indexOf("</", index2 + lengthOfclcah + 3))));
                    // int eee = 1 / 0;
                } else {
                    int index = 0;
                    int index2 = 0;
                    int length = 0;
                    if (nextLine.contains("title=")) {
                        // System.out.println("title if runs");
                        index = nextLine.indexOf("title=");
                        length = "title=".length();
                        index2 = nextLine.indexOf("\"", index + length + 3);
                    } else if (nextLine.contains("courselistcomment")) {
                        index = nextLine.indexOf("courselistcomment");
                        length = "courselistcomment".length();
                        index2 = nextLine.indexOf("</span>", index);
                        System.out.println(nextLine);
                        // int ggg = 1 / 0;
                    }
                    if (length > 0) {
                        String courseName = nextLine.substring(index + length,
                                index2);
                        // System.out.println(courseName);
                        for (int i = 0; i < content.size() && !lengthMatch; i++) {
                            lengthMatch = (((String) content.get(i)).length() == courseName.length());
                        }

                        if (!lengthMatch) {
                            content.add(courseName);
                            contentWriter.append(courseName + "\n");
                            contentWriter.flush();
                            System.out.println("\n" + courseName + "\n" + nextLine);
                            // int ddd = 1 / 0;
                        } else {
                            lengthMatch = false;
                        }
                    }
                }
                nextLine = scnr.nextLine();
            }

            // titleWriter.flush();
            // contentWriter.flush();
            // titleWriter.close();
            // contentWriter.close();

        } catch (ArithmeticException AE) {
            System.out.println(AE);
            System.exit(0);
        } catch (Exception e) {
            System.out.println("there was an exception: " + e);
        }
    }
}