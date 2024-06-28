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

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@Transactional
@Sql({"/Artikelen.sql", "/Bestellingen.sql"})
@AutoConfigureMockMvc
class ArtikelControllerTest {
    private final static String ARTIKELEN_TABLE = "Artikelen";
    private final static String BESTELLINGEN_TABLE = "Bestellingen";
    private static final Path TEST_RESOURCES = Path.of("src/test/resources");

    private final MockMvc mockMvc;
    private final JdbcClient jdbcClient;

    public ArtikelControllerTest(MockMvc mockMvc, JdbcClient jdbcClient) {
        this.mockMvc = mockMvc;
        this.jdbcClient = jdbcClient;
    }

    private long idVanTest1Artikel() {
        return jdbcClient.sql("select artikelId from Artikelen where naam = 'test1'")
                .query(long.class)
                .single();
    }

    // grant insert on Artikelen to Javagebruiker --> in creeerDatabaseMetDataPrulariaPuntComMySQL2023.sql plaatsen
    @Test
    void patchWijzigtDeTotaleVoorraadVanHetArtikel() throws Exception {
        var jsonData = Files.readString(TEST_RESOURCES.resolve("correcteVoorraad.json"));
        var artikelId = idVanTest1Artikel();
        mockMvc.perform(patch("/bestelling/updateVoorraad/{id}/voorraad", artikelId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(JdbcTestUtils.countRowsInTableWhere(jdbcClient, ARTIKELEN_TABLE,
                "voorraad = 22 and artikelId = " + artikelId)).isOne();
    }

    // grant insert on Artikelen to Javagebruiker --> in creeerDatabaseMetDataPrulariaPuntComMySQL2023.sql plaatsen
    @Test
    void patchVanOnbestaandArtikelMislukt() throws Exception {
        mockMvc.perform(patch("/bestelling/updateVoorraad/{artikelId}", Long.MAX_VALUE)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("gewijzigd"))
                .andExpect(status().isNotFound());
    }


    private int idVanTest1BestelId() {
        return jdbcClient.sql("select bestelId from bestellingen where familienaam = 'test1'")
                .query(Integer.class)
                .single();
    }
    @Test
    void updateBestellingStatusToOnderweg_success() throws Exception {
        var bestelId = idVanTest1BestelId();
        mockMvc.perform(patch("/bestelling/updateStatusOnderweg/{id}",bestelId))
                .andExpect(status().isOk());
        assertThat(JdbcTestUtils.countRowsInTableWhere(jdbcClient, BESTELLINGEN_TABLE,
                "bestellingsStatusId = 5 and bestelId =" + bestelId)).isOne();
    }

    @Test
    void updateBestellingStatusToOnderweg_notFound() throws Exception {
        mockMvc.perform(patch("/bestelling/updateStatusOnderweg/{id}",Integer.MAX_VALUE))
                .andExpect(status().isNotFound());
    }
}


