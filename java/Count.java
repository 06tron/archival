public class Count {

    private String type;
    private int count;

    public Count(String type) {
        this.type = type;
        count = 0;
    }

    public boolean incr() {
        count++;
        return true;
    }

    public String toString() {
        return type + ": " + count;
    }

}
