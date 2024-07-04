package be.vdab.scrumjava202406.bestellingen;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@Transactional
@Sql("/Bestellingen.sql")
@AutoConfigureMockMvc
public class BestllingControllerTablet2Test {
    private final static String BESTELLINGEN_TABLE = "Bestellingen";
    private final MockMvc mockMvc;
    private final JdbcClient jdbcClient;

    public BestllingControllerTablet2Test(MockMvc mockMvc, JdbcClient jdbcClient) {
        this.mockMvc = mockMvc;
        this.jdbcClient = jdbcClient;
    }

    private int idVanTest1BestelId() {
        return jdbcClient.sql("select bestelId from Bestellingen where familienaam = 'test1'")
                .query(Integer.class)
                .single();
    }
    @Test
    void updateBestellingStatusToOnderweg_success() throws Exception {
        var bestelId = idVanTest1BestelId();
        mockMvc.perform(patch("/bestellingen/updateStatusOnderweg/{id}",bestelId))
                .andExpect(status().isOk());
        assertThat(JdbcTestUtils.countRowsInTableWhere(jdbcClient, BESTELLINGEN_TABLE,
                "bestellingsStatusId = 5 and bestelId =" + bestelId)).isOne();
    }

    @Test
    void updateBestellingStatusToOnderweg_notFound() throws Exception {
        mockMvc.perform(patch("/bestellingen/updateStatusOnderweg/{id}",Integer.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

}
