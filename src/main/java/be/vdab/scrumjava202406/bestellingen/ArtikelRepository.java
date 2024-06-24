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

    // sergio scrijf maar jouw method hier
    Optional<Artikel> findById(long id) {
        String sql = """
               select artikelId, naam,prijs,gewichtInGram,voorraad,maximumVoorraad,maxAantalInMagazijnPlaats
               from artikelen
               where artikelId=?
               """;
        return jdbcClient.sql(sql)
                .param(id)
                .query(Artikel.class)
                .optional();
    }
}
