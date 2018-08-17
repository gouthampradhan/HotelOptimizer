package hotel.rest.controller;

import com.google.common.primitives.Ints;
import hotel.rest.model.HotelRoomOptimizationRequest;
import hotel.rest.model.Report;
import hotel.rest.model.Usage;
import hotel.service.HotelRoomOptimizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * REST endpoint controller class. Accepts request parameters and invokes appropriate service.
 */
@RestController
@RequestMapping("/hotel")
public class HotelRoomOptimizationController {

    @Autowired
    private HotelRoomOptimizationService service;

    /**
     * GET end point to calculate usage report
     * @param premium number of premium rooms
     * @param economy number of economy rooms
     * @param priceMargin optional priceMargin
     * @return Report
     */
    @GetMapping(path = "/usageReport")
    public Report usageReport(@RequestParam(name = "premium", defaultValue = "0") int premium,
                              @RequestParam(name = "economy", defaultValue = "0")int economy,
                              @RequestParam(name = "priceMargin", defaultValue = "-1")int priceMargin) {
        return service.generateUsageReport(premium < 0 ? 0 : premium, economy < 0 ? 0 : economy, priceMargin);
    }

    /**
     * POST end point to accept request body and calculate the usage report
     * @param request HotelRoomOptimizationRequest
     * @return Report
     */
    @PostMapping(path = "/usageReport")
    public Report usageReport(@RequestBody HotelRoomOptimizationRequest request) {
        int[] offers = Ints.toArray(request.getCustomerOffers());
        return service.generateUsageReport(request.getPremium(), request.getEconomy(),
                request.getPriceMargin(), offers);
    }
}
