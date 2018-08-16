package hotel.service;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import hotel.helper.OptimalUsageCalculator;
import hotel.rest.model.Report;
import hotel.rest.model.Usage;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

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
     * @param priceMargin priceMargin which separates economy and premium. Set -1 to use default value.
     * @return Usage report class
     */
    public Report generateUsageReport(int premium, int economy, int priceMargin){
        try {
            URL url = getClass().getClassLoader().getResource("customer.json");
            if(url != null){
                File file = new File(url.getFile());
                JsonReader reader = new JsonReader(new FileReader(file));
                Gson gson = new Gson();
                int[] offers = gson.fromJson(reader, int[].class);
                return generateUsageReport(premium, economy, priceMargin, offers);
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
        return new Report(new Usage(0, 0), new Usage(0, 0));
    }

    /**
     *
     * Method generates the usage report
     * @param premium number of premium rooms available
     * @param economy number of economy rooms available
     * @param priceMargin priceMargin which separates economy and premium. Set -1 to use default value.
     * @param customerOffers price list of customer offer prices
     * @return Usage report class
     */
    public Report generateUsageReport(int premium, int economy, int priceMargin, int[] customerOffers){
        if(priceMargin < 0){
            return new OptimalUsageCalculator(premium, economy, customerOffers).calculate();
        } else return new OptimalUsageCalculator(premium, economy, customerOffers, priceMargin).calculate();
    }
}
