package be.vdab.scrumjava202406.bestellingen;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class BestellingControllerTest {
    private final static String BESTELLINGEN_TABLE = "bestellingen";
    private final MockMvc mockMvc;
    private final JdbcClient jdbcClient;
    BestellingControllerTest(MockMvc mockMvc, JdbcClient jdbcClient) {
        this.mockMvc = mockMvc;
        this.jdbcClient = jdbcClient;
    }
}
