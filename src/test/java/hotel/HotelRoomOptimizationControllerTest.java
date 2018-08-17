package hotel;

import com.google.gson.Gson;
import hotel.rest.model.HotelRoomOptimizationRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HotelRoomOptimizationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getReportTest1() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/hotel/usageReport?premium=3&economy=3").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.premium.count", is(3)))
                .andExpect(jsonPath("$.premium.amount", is(738)))
                .andExpect(jsonPath("$.economy.count", is(3)))
                .andExpect(jsonPath("$.economy.amount", is(167)));

    }

    @Test
    public void getReportTest2() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/hotel/usageReport?premium=0&economy=0").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.premium.count", is(0)))
                .andExpect(jsonPath("$.premium.amount", is(0)))
                .andExpect(jsonPath("$.economy.count", is(0)))
                .andExpect(jsonPath("$.economy.amount", is(0)));
    }

    @Test
    public void getReportTest3() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/hotel/usageReport?premium=5&economy=0").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.premium.count", is(5)))
                .andExpect(jsonPath("$.premium.amount", is(954)))
                .andExpect(jsonPath("$.economy.count", is(0)))
                .andExpect(jsonPath("$.economy.amount", is(0)));
    }

    @Test
    public void getReportTest4() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/hotel/usageReport?premium=5&economy=2&priceMargin=99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.premium.count", is(5)))
                .andExpect(jsonPath("$.premium.amount", is(954)))
                .andExpect(jsonPath("$.economy.count", is(2)))
                .andExpect(jsonPath("$.economy.amount", is(68)));
    }

    @Test
    public void getReportTest5() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/hotel/usageReport?premium=10000000&economy=10000000&priceMargin=10000000").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.premium.count", is(0)))
                .andExpect(jsonPath("$.premium.amount", is(0)))
                .andExpect(jsonPath("$.economy.count", is(10)))
                .andExpect(jsonPath("$.economy.amount", is(1243)));
    }

    @Test
    public void getReportTest6() throws Exception {
        Gson gson = new Gson();
        HotelRoomOptimizationRequest request = new HotelRoomOptimizationRequest();
        List<Integer> offers = Arrays.asList(23, 45, 155, 374, 22, 99, 100, 101, 111, 209);
        request.setCustomerOffers(offers);
        request.setEconomy(3);
        request.setPremium(3);
        request.setPriceMargin(100);
        String json = gson.toJson(request);
        mvc.perform(MockMvcRequestBuilders.post("/hotel/usageReport")
                .content(json)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.premium.count", is(3)))
                .andExpect(jsonPath("$.premium.amount", is(738)))
                .andExpect(jsonPath("$.economy.count", is(3)))
                .andExpect(jsonPath("$.economy.amount", is(167)));
    }

    @Test
    public void getReportTest7() throws Exception {
        Gson gson = new Gson();
        HotelRoomOptimizationRequest request = new HotelRoomOptimizationRequest();
        List<Integer> offers = new ArrayList<>();
        request.setCustomerOffers(offers);
        request.setEconomy(3);
        request.setPremium(3);
        request.setPriceMargin(100);
        String json = gson.toJson(request);
        mvc.perform(MockMvcRequestBuilders.post("/hotel/usageReport")
                .content(json)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.premium.count", is(0)))
                .andExpect(jsonPath("$.premium.amount", is(0)))
                .andExpect(jsonPath("$.economy.count", is(0)))
                .andExpect(jsonPath("$.economy.amount", is(0)));
    }
}
