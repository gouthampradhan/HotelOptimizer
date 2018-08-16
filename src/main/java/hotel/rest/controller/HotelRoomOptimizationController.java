package hotel.rest.controller;

import com.google.common.primitives.Ints;
import hotel.rest.model.HotelRoomOptimizationRequest;
import hotel.rest.model.Usage;
import hotel.service.HotelRoomOptimizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/hotel")
public class HotelRoomOptimizationController {

    @Autowired
    HotelRoomOptimizationService service;

    @GetMapping(path = "/usageReport")
    public Usage usageReport(@RequestParam(name = "premium", defaultValue = "0") int premium,
                             @RequestParam(name = "economy", defaultValue = "0")int economy,
                             @RequestParam(name = "priceMargin", defaultValue = "-1")int priceMargin) {
        return service.generateUsageReport(premium < 0 ? 0 : premium, economy < 0 ? 0 : economy, priceMargin);
    }

    @PostMapping(path = "/usageReport")
    public Usage usageReport(@RequestBody HotelRoomOptimizationRequest request) {
        int[] offers = Ints.toArray(request.getCustomerOffers());
        return service.generateUsageReport(request.getPremium(), request.getEconomy(),
                request.getPriceMargin(), offers);
    }
}
