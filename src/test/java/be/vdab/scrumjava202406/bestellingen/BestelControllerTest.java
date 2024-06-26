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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@Sql({"/bestellingen.sql","/bestelling.sql"})
@AutoConfigureMockMvc
class BestelControllerTest {
    private final static String BESTELLINGEN_TABLE = "bestellingen";
    private final static String BESTELLING_TABLE = "bestelling";
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

    // TODO hier klopt iets niet: org.springframework.jdbc.datasource.init.ScriptStatementFailedException: Failed to execute SQL script statement #1 of class path resource [bestellingen.sql]: insert into Artikelen(ean, naam, beschrijving, prijs, gewichtInGram, bestelpeil, voorraad, minimumVoorraad, maximumVoorraad, levertijd, aantalBesteldLeverancier, maxAantalInMagazijnPlaats, leveranciersId) values ('0', 'test1', '0', 1.0, 1, 1, 1, 1, 1, 1, 1, 1, 1), ('1', 'test2', '1', 1.0, 1, 1, 1, 1, 1, 1, 1, 1, 1)
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

    // TODO hier klopt ook iets niet: org.springframework.jdbc.datasource.init.ScriptStatementFailedException: Failed to execute SQL script statement #1 of class path resource [bestellingen.sql]: insert into Artikelen(ean, naam, beschrijving, prijs, gewichtInGram, bestelpeil, voorraad, minimumVoorraad, maximumVoorraad, levertijd, aantalBesteldLeverancier, maxAantalInMagazijnPlaats, leveranciersId) values ('0', 'test1', '0', 1.0, 1, 1, 1, 1, 1, 1, 1, 1, 1), ('1', 'test2', '1', 1.0, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    @Test
    void patchVanOnbestaandArtikelMislukt() throws Exception {
        mockMvc.perform(patch("/bestelling/updateVoorraad/{artikelId}", Long.MAX_VALUE)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("gewijzigd"))
                .andExpect(status().isNotFound());
    }
    @Test
    void updateBestellingStatusToOnderweg_success() throws Exception {
        mockMvc.perform(patch("/bestelling/updateStatusOnderweg/1"))
                .andExpect(status().isOk());

        var sql = "SELECT bestellingsStatusId FROM " + BESTELLING_TABLE + " WHERE bestelId = ?";
        var bestellingsStatusId = jdbcClient.sql(sql).param(1).query(Integer.class).optional().orElseThrow();

        assertEquals(5, bestellingsStatusId);
    }
}


