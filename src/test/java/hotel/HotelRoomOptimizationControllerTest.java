package hotel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
                .andExpect(jsonPath("$.premium", is(738)))
                .andExpect(jsonPath("$.economy", is(167)));
    }

    @Test
    public void getReportTest2() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/hotel/usageReport?premium=7&economy=5").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.premium", is(1054)))
                .andExpect(jsonPath("$.economy", is(189)));
    }

    @Test
    public void getReportTest3() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/hotel/usageReport?premium=2&economy=7").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.premium", is(583)))
                .andExpect(jsonPath("$.economy", is(189)));
    }

}
