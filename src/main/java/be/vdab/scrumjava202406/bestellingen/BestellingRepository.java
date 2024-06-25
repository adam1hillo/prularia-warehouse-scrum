package be.vdab.scrumjava202406.bestellingen;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class BestellingRepository {
    // Status 2 betekent betaald
    private static final int BETAALD_STATUS_ID = 2;
    private final JdbcClient jdbcClient;

    public BestellingRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    long countBestellingKlaarOmGepickt() {
        var sql = """
                SELECT COUNT(Bestellingen.bestelId) as bestelingTotal FROM Bestellingen
                WHERE bestellingsStatusId = ?;
                """;
        return (long) jdbcClient.sql(sql).param(BETAALD_STATUS_ID).query().singleValue();
    }
}
