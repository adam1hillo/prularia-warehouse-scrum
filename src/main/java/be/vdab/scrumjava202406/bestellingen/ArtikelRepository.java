package be.vdab.scrumjava202406.bestellingen;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class ArtikelRepository {
    private final JdbcClient jdbcClient;

    public ArtikelRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    void updateTotaleVoorraad(long artikelId, int voorraad) {
        var sql = """
                update Artikelen
                set voorraad = ?
                where artikelId = ?
                """;
        if (jdbcClient.sql(sql).params(voorraad, artikelId).update() == 0) {
            throw new ArtikelNietGevondenException(artikelId);
        }
    }
    record BestelId(long bestelId) {
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
            throw new ArtikelNietGevondenException(bestelId);
        }
    }
}