package hotel.helper;

import hotel.rest.model.Report;
import hotel.rest.model.Usage;

import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Class to calculate optimal usage of premium and economy rooms
 */
public class OptimalUsageCalculator {
    private final int premium, economy;
    private final int[] offers;
    private final int priceLimit;

    /**
     * Constructor
     * @param premium total number of premium rooms
     * @param economy total number of available economy rooms
     * @param offers array of offer price from customers
     */
    public OptimalUsageCalculator(final int premium, final int economy, final int[] offers){
        this.premium = premium;
        this.economy = economy;
        this.offers = offers;
        this.priceLimit = 100;
    }

    /**
     * Constructor
     * @param premium total number of premium rooms
     * @param economy total number of available economy rooms
     * @param offers array of offer price from customers
     * @param priceLimit priceMargin which separates economy and premium
     */
    public OptimalUsageCalculator(final int premium, final int economy, final int[] offers, final int priceLimit){
        this.premium = premium;
        this.economy = economy;
        this.offers = offers;
        this.priceLimit = priceLimit;
    }

    /**
     * Calculate optimal usage price
     * @return Usage
     */
    public Report calculate(){
        List<Integer> economyCustomers = Arrays.stream(offers).filter(o -> o < priceLimit)
                .boxed()
                .sorted(Collections.reverseOrder())
                .collect(toList());
        List<Integer> premiumCustomer = Arrays.stream(offers).filter(o -> o >= priceLimit)
                .boxed()
                .sorted(Collections.reverseOrder())
                .collect(toList());
        int[] premiumRooms = new int[premium];
        int currPremiumCount = 0, currEconomyCount = 0;
        for(int prePrice : premiumCustomer){
            if(currPremiumCount < premium){
                premiumRooms[currPremiumCount++] = prePrice;
            } else break;
        }
        Queue<Integer> pq = new ArrayDeque<>();
        for(int ecoPrice : economyCustomers){
            if(currEconomyCount < economy){
                pq.offer(ecoPrice);
                currEconomyCount++;
            } else if(currPremiumCount < premium){
                if(!pq.isEmpty()){
                    premiumRooms[currPremiumCount++] = pq.poll();
                    pq.offer(ecoPrice);
                } else{
                    premiumRooms[currPremiumCount++] = ecoPrice;
                }
            } else break;
        }
        return new Report(new Usage(currPremiumCount, Arrays.stream(premiumRooms).sum()),
                new Usage(currEconomyCount, pq.stream().mapToInt(Integer::intValue).sum()));
    }
}
