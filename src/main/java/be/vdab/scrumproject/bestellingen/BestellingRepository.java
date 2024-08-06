package be.vdab.scrumproject.bestellingen;

import be.vdab.scrumproject.util.BestellingNietGevondenException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BestellingRepository {
    // Status 2 betekent betaald
    private static final int BETAALD_STATUS_ID = 2;
    private final JdbcClient jdbcClient;

    public BestellingRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    List<Long> findVijfOudsteBestellingen() {
        var sql = "select bestelId from Bestellingen where bestellingsStatusId = ? order by besteldatum limit 5";
        return jdbcClient.sql(sql).param(BETAALD_STATUS_ID).query(Long.class).list();
    }

    BestellingAantalArtikelTotalGewicht aantalArtikelenTotaleGewichtPerBestelling(long bestelId) {
        var sql = """
                select sum(Bestellijnen.aantalBesteld) as aantalArtikelen, sum(Bestellijnen.aantalBesteld*gewichtInGram) as totaleGewicht
                from Bestellijnen join Artikelen on Bestellijnen.artikelId = Artikelen.artikelId
                where bestelId = ?
                group by bestelId
                """;

        return jdbcClient.sql(sql).param(bestelId).query(BestellingAantalArtikelTotalGewicht.class).single();
    }

    long countBestellingKlaarOmGepickt() {
        var sql = """
                SELECT COUNT(Bestellingen.bestelId) as bestelingTotal FROM Bestellingen
                WHERE bestellingsStatusId = ?;
                """;
        return (long) jdbcClient.sql(sql).param(BETAALD_STATUS_ID).query().singleValue();
    }
        Optional<BestelIdDTO> findAndLockByBestelId(long bestelId) {
            var sql = """
                select bestelId
                from Bestellingen
                where bestelId = ?
                for update
                """;
            return jdbcClient.sql(sql).param(bestelId).query(BestelIdDTO.class).optional();
        }
        void updateBestellingStatus(long bestelId) {
            String sql = """
                update Bestellingen
                set bestellingsStatusId = 5
                where bestelId = ?
                """;
            if (jdbcClient.sql(sql).param(bestelId).update() == 0) {
                throw new BestellingNietGevondenException(bestelId);
            }
        }
}
