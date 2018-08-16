package hotel.rest.model;

import java.util.List;

public class HotelRoomOptimizationRequest {
    private List<Integer> customerOffers;
    private int premium;
    private int economy;
    private int priceMargin = -1;

    public List<Integer> getCustomerOffers() {
        return customerOffers;
    }

    public void setCustomerOffers(List<Integer> customerOffers) {
        this.customerOffers = customerOffers;
    }

    public int getPremium() {
        return premium;
    }

    public void setPremium(int premium) {
        this.premium = premium;
    }

    public int getEconomy() {
        return economy;
    }

    public void setEconomy(int economy) {
        this.economy = economy;
    }

    public int getPriceMargin() {
        return priceMargin;
    }

    public void setPriceMargin(int priceMargin) {
        this.priceMargin = priceMargin;
    }
}
