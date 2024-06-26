package be.vdab.scrumjava202406;

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
@Sql("/bestellingen.sql")
@AutoConfigureMockMvc
class BestellingControllerTest {
    private final static String BESTELLINGEN_TABLE = "bestellingen";

    private final JdbcClient jdbcClient;

    private final MockMvc mockMvc;
    BestellingControllerTest(JdbcClient jdbcClient, MockMvc mockMvc) {
        this.jdbcClient = jdbcClient;
        this.mockMvc = mockMvc;
    }

    @Test
    void findCountVindtHetJuisteAantalBestellingenMetStatusBetaald() throws Exception {
        mockMvc.perform(get("/bestellingen/aantal"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$")
                                .value(JdbcTestUtils.countRowsInTableWhere(jdbcClient, BESTELLINGEN_TABLE,
                                        "bestellingsStatusId = 2")));
    }
}
