package be.vdab.scrumjava202406.bestellingen;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class BestelRepository {
    private final JdbcClient jdbcClient;

    public BestelRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    Optional<ArtikelId> findAndLockById(Integer artikelId) {
        var sql = """
                select artikelId
                from Artikelen
                where id = ?
                for update
                """;
        return jdbcClient.sql(sql).param(artikelId).query(ArtikelId.class).optional();
    }

    record ArtikelId(Integer id) {
    }

    void updateTotaleVoorraad(Integer artikelId, Integer voorraad) {
        var sql = """
                update Artikelen
                set voorraad = ?
                where artikelId = ?
                """;
        if (jdbcClient.sql(sql).params(voorraad, artikelId).update() == 0) {
            throw new ArtikelNietGevondenException(artikelId);
        }
    }
    record BestelId(int bestelId) {
    }

    Optional<BestelId> findAndLockByBesteId(int bestelId) {
        var sql = """
                select bestelId
                from bestellingen
                where bestelId = ?
                for update
                """;
        return jdbcClient.sql(sql).param(bestelId).query(BestelId.class).optional();
    }
    void updateBestellingStatus(int bestelId){
        String sql = """
                update bestellingen
                set bestellingsStatusId = 5
                where bestelId = ?
                """;
        if (jdbcClient.sql(sql).param(bestelId).update() == 0){
            throw new BestellingNietGevondenException(bestelId);
        }
    }
}