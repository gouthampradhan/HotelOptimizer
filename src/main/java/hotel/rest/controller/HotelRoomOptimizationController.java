package hotel.rest.controller;

import hotel.rest.model.Usage;
import hotel.service.HotelRoomOptimizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/hotel")
public class HotelRoomOptimizationController {

    @Autowired
    HotelRoomOptimizationService service;

    @GetMapping(path = "/usageReport")
    public Usage usageReport(@RequestParam(name = "premium", defaultValue = "0") int premium,
                             @RequestParam(name = "economy", defaultValue = "0")int economy) {
        return service.generateUsageReport(premium, economy);
    }
    
}
