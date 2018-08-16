package hotel.rest.model;

/**
 * Usage report class
 */
public class Usage {
    private final int premium;
    private final int economy;

    public Usage(final int premium, final int economy){
        this.premium = premium;
        this.economy = economy;
    }

    public int getPremium() {
        return premium;
    }

    public int getEconomy() {
        return economy;
    }
}
