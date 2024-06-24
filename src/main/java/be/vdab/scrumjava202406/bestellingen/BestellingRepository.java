package be.vdab.scrumjava202406.bestellingen;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BestellingRepository {
    private final JdbcClient jdbcClient;

    public BestellingRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Bestelling> findOudsteBestelling() {
        var sql = """
                select b.bestelId,mp.rek,mp.rij,mp.aantal,a.naam,a.artikelId
                    from bestellijnen b
                    inner join artikelen a on b.artikelId = a.artikelId
                    inner join magazijnplaatsen mp on mp.artikelId = a.artikelId
                    where b.bestelId = (SELECT bestelId
                FROM bestellingen
                ORDER BY besteldatum ASC
                LIMIT 1);
  """;
        return jdbcClient.sql(sql)
                .query(Bestelling.class)
                .list();
    }
}
