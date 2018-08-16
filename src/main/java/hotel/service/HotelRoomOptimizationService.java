package hotel.service;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import hotel.rest.model.Usage;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Service class to calculate the usage report. The class uses customer.json file from the resource folder as
 * the input data file for customer offer price information.
 */
@Service
public class HotelRoomOptimizationService {
    private Log log = LogFactory.getFactory().getInstance(HotelRoomOptimizationService.class);

    /**
     * Method generates the usage report
     * @param premium number of premium rooms available
     * @param economy number of economy rooms available
     * @return Usage report class
     */
    public Usage generateUsageReport(int premium, int economy){
        Gson gson = new Gson();
        try {
            URL url = getClass().getClassLoader().getResource("customer.json");
            if(url != null){
                File file = new File(url.getFile());
                JsonReader reader = new JsonReader(new FileReader(file));
                int[] offers = gson.fromJson(reader, int[].class);
                return calculateOptimalUsage(premium, economy, offers);
            } else{
                log.error("Failed to load JSON input resource. Please ensure that the json input file is" +
                        " present in the resource folder");
            }
        }catch (JsonIOException | JsonSyntaxException e){
            log.error("Failed to load JSON input resource. Please ensure that the json input file is" +
                    " present in the resource folder", e);
        } catch (IOException e){
            log.error("Exception occurred while reading the input file resource", e);
        }
        return new Usage(0, 0);
    }

    private Usage calculateOptimalUsage(int premium, int economy, int[] offers){

        List<Integer> economyCustomers = Arrays.stream(offers).filter(o -> o < 100)
                .boxed()
                .sorted(Collections.reverseOrder())
                .collect(toList());
        List<Integer> premiumCustomer = Arrays.stream(offers).filter(o -> o >= 100)
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
        Queue<Integer> pq = new PriorityQueue<>();
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
        return new Usage(Arrays.stream(premiumRooms).sum(), pq.stream().mapToInt(Integer::intValue).sum());
    }
}
