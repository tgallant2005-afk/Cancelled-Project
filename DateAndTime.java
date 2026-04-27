public class DateAndTime {
    public static final String DAYS = "MTWRF"; // days as represented by the uni
    public static final String[] MONTHS = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov",
            "Dec" };
    // shorthand months used by the uni
    public static final int[] DAYS_IN_MONTHS = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    private boolean[] days = new boolean[5];// 5 days, so set to five
    // days the class is had
    private String timeLength; // time the thing runs for (i.e. 12:00PM-2:30PM)
    private String span; // month span it runs for

    // input should be in format (with different numbers):
    // "(12:34 - 45:67) (days it happens)"
    // method takes in time and span, and does some verification, and then sends off
    // for both further verification and setting the private variables
    public DateAndTime(String daysAndTime, String span) {
        String[] inputParts;
        String modifiedInput;
        if (daysAndTime == null) {
            timeLength = "No time given";
        } else {
            inputParts = daysAndTime.split(" ");
            if (validTime(inputParts[0])) {
                timeLength = inputParts[0];
            }
            modifyDays(inputParts[1]);
        }

        if (span != null) {
            modifySpan(span);
        } else {
            this.span = "No month span given";
        }

    }

    public boolean ValidTime() {
        boolean valid = (this.timeLength.equals("No time given") || this.span.equals("No month span given"));
        return valid;
    }

    // expected input: 12:34-56:78 (different numbers)
    // validates whether string is a valid timespan
    private boolean validTime(String time) {
        boolean validTime = false;
        String[] timesCheck; // current timespan validating
        String[] timeParts; // holds the hour and minute parts of the time
        int[] hours = new int[2]; // array holding hours during validation
        int[] minutes = new int[2]; // array holding minutes during validation

        if (time != null && time.indexOf("-") != -1) {
            timesCheck = time.split("-");
            if (timesCheck.length == 2) {
                // if, after being split by "-", there are 2 sections
                for (int i = 0; i < 2; i++) {
                    if (timesCheck[i].indexOf(":") != -1) {
                        timeParts = timesCheck[i].split(":");
                        try {
                            // check each part for :, split by it,
                            // and then validate they are appropriate amounts of time
                            hours[i] = Integer.parseInt(timeParts[0]);
                            minutes[i] = Integer.parseInt(timeParts[1]);
                            validTime = 0 <= hours[i] && hours[i] <= 24;
                            validTime = validTime && (0 <= minutes[i] && minutes[i] <= 60);
                        } catch (NumberFormatException nfe) {
                            System.out.println("time passed in is not fully numbers");
                        }
                    }
                }

                // if in valid time structure, validates that the times themselves are within
                // the same day
                // (i.e. do not have 22:33-03:55, or a class that goes past midnight)
                if (validTime) {
                    validTime = validTime && (hours[0] <= hours[1]) && (minutes[0] <= minutes[1]);
                    if (validTime) {
                        this.timeLength = time;
                    }
                }
            }
        }

        return validTime;
    }

    // modifies and validates the days the "thing" happens at
    private modifyDays(String input){
        String daysSetting;
        if(daysSetting != null){
            daysSetting = input.replaceAll("() ", "");
            if(daysSetting.length() > 0 && daysSetting.length() < 6){
                //if, after removing all "(",")", and " ", there are 5 characters left
                for(int i = 0; i < daysSetting.length(); i++){
                    char day = daysSetting.charAt(i);
                    for(int j = 0; j < daysSetting.length(); j++){
                        //check if each day of the week exists in said given input, and then set the corresponding 
                        //day boolean in the days array to true
                        if(day = Character.toUpperCase(DAYS.charAt(j))){
                            days[i] = true;
                        }
                    }
                }
            }
        }
    }

    // modifies the span of months the thing lasts
    // input should be "month date - month date"
    private modifySpan(String span){
        String[] spanDates;
        String[] DateParts;
        boolean valid = true;
        boolean validMonth = false;
        boolean validDay = false;
        int dayAsInt;
        if(span != null && span.indexOf("-") != -1){
            spanDates = span.split("-");
            //split the date by "-"
            if(spanDates.length == 2){
                for(int i = 0; i < 2 && valid; i++){
                    //for each section of the date given, split by " ",
                    //then confirm the month exists and that the corresponding date is within the valid month
                    DateParts = spanDates[i].split(" ");
                    for(int j = 0; j < MONTHS.length && !validMonth; j++){
                        if(DateParts[0].equalsIgnoreCase(MONTHS[j])){
                            validMonth = true;
                            try {
                                dayAsInt = Integer.parseInt(DateParts[1]);
                                validDay = (0 < dayAsInt && dayAsInt < DAYS_IN_MONTHS[j]);
                            } catch (NumberFormatException nfe) {
                                System.out.println("days of span: \"" + span + "\" are not fully numbers");
                            }
                        }
                    }
                    valid = (validMonth && validDay);
                }
            }

            if(valid){
                this.span = span;
            }
        }
    }
}