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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@Transactional
@Sql("/artikelen.sql")
@AutoConfigureMockMvc
class BestelControllerTest {
    private final static String ARTIKELEN_TABLE = "Artikelen";
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
        var artikelId = idVanTest1Artikel();
        var voorraad = 22;
        mockMvc.perform(patch("/bestelling/updateVoorraad/{id}/{voorraad}", artikelId, voorraad))
                .andExpectAll(status().isOk());
        assertThat(JdbcTestUtils.countRowsInTableWhere(jdbcClient, ARTIKELEN_TABLE,
                "voorraad = 22 and artikelId = " + artikelId)).isOne();
    }

    // TODO hier klopt ook iets niet: org.springframework.jdbc.datasource.init.ScriptStatementFailedException: Failed to execute SQL script statement #1 of class path resource [bestellingen.sql]: insert into Artikelen(ean, naam, beschrijving, prijs, gewichtInGram, bestelpeil, voorraad, minimumVoorraad, maximumVoorraad, levertijd, aantalBesteldLeverancier, maxAantalInMagazijnPlaats, leveranciersId) values ('0', 'test1', '0', 1.0, 1, 1, 1, 1, 1, 1, 1, 1, 1), ('1', 'test2', '1', 1.0, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    @Test
    void patchVanOnbestaandArtikelMislukt() throws Exception {
        mockMvc.perform(patch("/bestelling/updateVoorraad/{artikelId}", Long.MAX_VALUE)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("gewijzigd"))
                .andExpect(status().isNotFound());
    }
}


