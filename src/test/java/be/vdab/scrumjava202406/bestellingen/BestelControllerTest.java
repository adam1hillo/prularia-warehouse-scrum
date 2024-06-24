package be.vdab.scrumjava202406.bestellingen;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@Sql("/bestellingen.sql")
@AutoConfigureMockMvc
class BestelControllerTest {
    private final static String BESTELLINGEN_TABLE = "bestellingen";
    private final MockMvc mockMvc;
    private final JdbcClient jdbcClient;

    public BestelControllerTest(MockMvc mockMvc, JdbcClient jdbcClient) {
        this.mockMvc = mockMvc;
        this.jdbcClient = jdbcClient;
    }

    private Integer idVanTest1Artikel() {
        return jdbcClient.sql("select artikelId from Artikelen where naam = 'test1'")
                .query(Integer.class)
                .single();
    }

    // TODO hier klopt iets niet
    @Test
    void patchWijzigtDeTotaleVoorraadVanHetArtikel() throws Exception {
        var id = idVanTest1Artikel();
        mockMvc.perform(patch("bestelling/updateVoorraad/{artikelId}", id)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("gewijzigd"))
                .andExpect(status().isOk());
        assertThat(JdbcTestUtils.countRowsInTableWhere(jdbcClient, BESTELLINGEN_TABLE,
                "naam = 'gewijzigd' and id =" + id)).isOne();
    }

    // TODO hier klopt ook iets niet
    @Test
    void patchVanOnbestaandArtikelMislukt() throws Exception {
        mockMvc.perform(patch("/bestelling/updateVoorraad/{artikelId}", Long.MAX_VALUE)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("gewijzigd"))
                .andExpect(status().isNotFound());
    }
}


