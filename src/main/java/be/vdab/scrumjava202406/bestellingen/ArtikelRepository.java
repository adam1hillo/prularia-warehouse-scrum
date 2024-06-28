package be.vdab.scrumjava202406.bestellingen;

import be.vdab.scrumjava202406.util.ArtikelNietGevondenException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;


@Repository
public class ArtikelRepository {
    private final JdbcClient jdbcClient;

    public ArtikelRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    void updateTotaleVoorraad(long artikelId, NieuweVoorraad voorraad) {
        var sql = """
                update Artikelen
                set voorraad = ?
                where artikelId = ?
                """;
        if (jdbcClient.sql(sql).params(voorraad.voorraad(), artikelId).update() == 0) {
            throw new ArtikelNietGevondenException(artikelId);
        }
    }
}