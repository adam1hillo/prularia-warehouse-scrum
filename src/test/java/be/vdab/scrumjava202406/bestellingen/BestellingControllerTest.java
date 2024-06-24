package be.vdab.scrumjava202406.bestellingen;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@Sql({"/bestellingen.sql", "/artikelen.sql", "/bestellijnen.sql"})
@AutoConfigureMockMvc
public class BestellingControllerTest {
    private final static String BESTELLINGEN_TABLE = "bestellingen";
    private final MockMvc mockMvc;
    private final JdbcClient jdbcClient;
    BestellingControllerTest(MockMvc mockMvc, JdbcClient jdbcClient) {
        this.mockMvc = mockMvc;
        this.jdbcClient = jdbcClient;
    }

    @Test
    void findCountVindtHetJuisteAantalBestellingen() throws Exception {
        mockMvc.perform(get("/bestellingen/aantal"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$")
                                .value(JdbcTestUtils.countRowsInTable(jdbcClient, BESTELLINGEN_TABLE)));
    }

    @Test
    void findVijfOudsteBestellingen() throws Exception {

    }

}
