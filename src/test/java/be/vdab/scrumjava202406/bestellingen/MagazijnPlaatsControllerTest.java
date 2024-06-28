package be.vdab.scrumjava202406.bestellingen;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@Transactional
@Sql({"/cleanUp.sql", "/artikelen2.sql", "/magazijnPlaatsen.sql"})
@AutoConfigureMockMvc
class MagazijnPlaatsControllerTest {

    private final static String MAGAZIJNPLAATSEN_TABLE = "magazijnplaatsen";
    private final MockMvc mockMvc;
    private final JdbcClient jdbcClient;

    public MagazijnPlaatsControllerTest(MockMvc mockMvc, JdbcClient jdbcClient) {
        this.mockMvc = mockMvc;
        this.jdbcClient = jdbcClient;
    }

   private long idVanTest1Artikel() {
        return jdbcClient.sql("select artikelId from artikelen where naam = 'test1'")
                .query(Long.class)
                .single();
    }

    @Test
    void findAllVindtAlleMagazijnPlaatsenById() throws Exception {
    long id = idVanTest1Artikel();
        mockMvc.perform(get("/magazijnPlaatsen/{id}", id))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("length()")
                                .value(JdbcTestUtils.countRowsInTableWhere(jdbcClient, MAGAZIJNPLAATSEN_TABLE,
                                        "artikelId = " + id))
                );
    }

}