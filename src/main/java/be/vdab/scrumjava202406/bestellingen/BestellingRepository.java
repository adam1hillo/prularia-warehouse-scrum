package be.vdab.scrumjava202406.bestellingen;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BestellingRepository {
    private static final int BETAALD_STATUS_ID = 2;
    private final JdbcClient jdbcClient;

    public BestellingRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    List<Long> findVijfOudsteBestellingen() {
        var sql = "select bestelId from Bestellingen where bestellingsStatusId = ? order by besteldatum desc limit 5";
        return jdbcClient.sql(sql).param(BETAALD_STATUS_ID).query(Long.class).list();
    }

    /**
     * @param bestelId
     * @return gewicht in gramme
     */
    BestellingAantalArtikelTotalGewicht totaleGewichtBestelling(long bestelId) {
        var sql = """
                select sum(Bestellijnen.aantalBesteld) as aantalArtikelen,sum(Bestellijnen.aantalBesteld*gewichtInGram) as totaleGewicht
                from Bestellijnen join Artikelen on Bestellijnen.artikelId = Artikelen.artikelId
                where bestelId = ?
                group by bestelId
                """;

        return jdbcClient.sql(sql).param(bestelId).query(BestellingAantalArtikelTotalGewicht.class).single();
    }
}
