package hotel.rest.model;

/**
 * Usage report class
 */
public class Usage {
    private final int count;
    private final int amount;

    public Usage(final int count, final int amount){
        this.count = count;
        this.amount = amount;
    }

    public int getCount() {
        return count;
    }

    public int getAmount() {
        return amount;
    }
}
