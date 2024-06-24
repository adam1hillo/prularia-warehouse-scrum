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

    Optional<ArtikelId> findAndLockById(Integer id) {
        var sql = """
                select id
                from Artikelen
                where id = ?
                for update
                """;
        return jdbcClient.sql(sql).param(id).query(ArtikelId.class).optional();
    }

    record ArtikelId(Integer id) {
    }

    void updateTotaleVoorraad(Integer id, Integer voorraad) {
        var sql = """
                update Artikelen
                set voorraad = ?
                where id = ?
                """;
        if (jdbcClient.sql(sql).params(voorraad, id).update() == 0) {
            throw new ArtikelNietGevondenException(id);
        }
    }
}