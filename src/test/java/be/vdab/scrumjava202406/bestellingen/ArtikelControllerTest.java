package be.vdab.scrumjava202406.bestellingen;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@Sql("/artikelen2.sql")
@AutoConfigureMockMvc
class ArtikelControllerTest {

    private final static String ARTIKELEN_TABLE = "Artikelen";
    private final MockMvc mockMvc;
    private final JdbcClient jdbcClient;

    public ArtikelControllerTest(MockMvc mockMvc, JdbcClient jdbcClient) {
        this.mockMvc = mockMvc;
        this.jdbcClient = jdbcClient;
    }

    private long idVanTest1Artikel() {
        return jdbcClient.sql("select artikelId from Artikelen where naam = 'test1'")
                .query(Long.class)
                .single();
    }

    @Test
    void findByIdMetEenBestaandeIdVindtDeArtikel() throws Exception {
        long id = idVanTest1Artikel();
        mockMvc.perform(get("/artikelen/{id}", id))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("artikelId").value(id),
                        jsonPath("naam").value("test1"));

    }

    @Test
    void findByIdMetEenOnbestaandeIdGeeftNotFound() throws Exception {
        mockMvc.perform(get("/artikelen/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }
}