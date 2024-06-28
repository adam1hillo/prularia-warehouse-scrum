package be.vdab.scrumjava202406.bestellingen;

import be.vdab.scrumjava202406.util.BestellingNietGevondenException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BestellingRepository {
    private final JdbcClient jdbcClient;

    BestellingRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    Optional<BestelIdDTO> findAndLockByBestelId(long bestelId) {
        var sql = """
                select bestelId
                from bestellingen
                where bestelId = ?
                for update
                """;
        return jdbcClient.sql(sql).param(bestelId).query(BestelIdDTO.class).optional();
    }

    void updateBestellingStatus(long bestelId) {
        String sql = """
                update bestellingen
                set bestellingsStatusId = 5
                where bestelId = ?
                """;
        if (jdbcClient.sql(sql).param(bestelId).update() == 0) {
            throw new BestellingNietGevondenException(bestelId);
        }
    }
}
