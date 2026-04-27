public interface PrereqItem {

    // returns whether the prereq (singular or set) has been met
    public boolean prereqsMet();

    // recieves a string and checks if it is contained in the prereq. if so, sets
    // prereq as met
    public boolean checkPrereq(String toCheck);
}