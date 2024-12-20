package be.vdab.scrumproject.bestellingen;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
//@Sql("/artikelen2.sql")
@Sql("/artikelen3.sql")
@Sql("/magazijnPlaatsen2.sql")
@AutoConfigureMockMvc
class ArtikelControllerTest {

    private final static String ARTIKELEN_TABLE = "Artikelen";
    private final static String MAGAZIJN_TABLE = "MagazijnPlaatsen";
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
        System.out.println(artikelId);
        System.out.println(jsonData);
        mockMvc.perform(patch("/artikelen/updateVoorraad/{id}/aantal", artikelId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(JdbcTestUtils.countRowsInTableWhere(jdbcClient, ARTIKELEN_TABLE,
                "voorraad = 20 and artikelId = " + artikelId)).isOne();
    }

    // grant insert on Artikelen to Javagebruiker --> in creeerDatabaseMetDataPrulariaPuntComMySQL2023.sql plaatsen
    @Test
    void findByIdMetEenBestaandeIdVindtDeArtikel() throws Exception {
        long id = idVanTest1Artikel();
        mockMvc.perform(get("/artikelen/{id}", id))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("artikelId").value(id),
                        jsonPath("naam").value("test1"));

    }

    // grant insert on Artikelen to Javagebruiker --> in creeerDatabaseMetDataPrulariaPuntComMySQL2023.sql plaatsen
    @Test
    void patchVanOnbestaandArtikelMislukt() throws Exception {
        var jsonData = Files.readString(TEST_RESOURCES.resolve("correcteVoorraad.json"));
        mockMvc.perform(patch("/bestelling/updateVoorraad/{artikelId}/aantal", Long.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData))
                .andExpect(status().isNotFound());
    }
    @Test
    void patchVanVoorraadKleinerDanNulMislukt() throws Exception {
        var jsonData = Files.readString(TEST_RESOURCES.resolve("voorraadKleinerDanNul.json"));
        mockMvc.perform(patch("/bestelling/updateVoorraad/{artikelId}/voorraad", Long.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData))
                .andExpect(status().isNotFound());
    }
    @Test
    void findByIdMetEenOnbestaandeIdGeeftNotFound() throws Exception {
        mockMvc.perform(get("/artikelen/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    void patchWijzigtDeMagazijnPlaatsVoorraad() throws Exception {
        var jsonData = Files.readString(TEST_RESOURCES.resolve("CorrecteRijRekNieuweAantal.json"));
        mockMvc.perform(patch("/artikelen/updateVoorraad/plaats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(JdbcTestUtils.countRowsInTableWhere(jdbcClient, MAGAZIJN_TABLE,
                "rij = 'A' and rek = '3' and aantal=0")).isOne();
        assertThat(JdbcTestUtils.countRowsInTableWhere(jdbcClient, MAGAZIJN_TABLE,
                "rij = 'A' and rek = '2' and aantal=5")).isOne();
        assertThat(JdbcTestUtils.countRowsInTableWhere(jdbcClient, MAGAZIJN_TABLE,
                "rij = 'B' and rek = '4' and aantal=0")).isOne();
    }
}