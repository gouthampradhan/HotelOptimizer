package hotel.rest.model;

public class Report {
    private final Usage premium;
    private final Usage economy;
    public Report(Usage premium, Usage economy){
        this.premium = premium;
        this.economy = economy;
    }

    public Usage getPremium() {
        return premium;
    }

    public Usage getEconomy() {
        return economy;
    }
}
