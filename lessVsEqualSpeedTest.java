public class lessVsEqualSpeedTest {
    public static void main(String[] args) {
        long lessTest;
        long equalTest;
        long test = System.currentTimeMillis();

        for (int i = 0; i < 1000000000; i++) {
            boolean check = 1 < 0;
        }
        lessTest = System.currentTimeMillis() - test;

        test = System.currentTimeMillis();
        for (int i = 0; i < 1000000000; i++) {
            boolean check = 1 == 0;
        }
        equalTest = System.currentTimeMillis() - test;

        System.out.println("ms for 100k less than: " + lessTest);
        System.out.println("ms for 100k equals: " + equalTest);
    }
}