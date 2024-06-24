package be.vdab.scrumjava202406.bestellingen;

import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.Optional;

public class ArtikelRepository {
    private final JdbcClient jdbcClient;

    public ArtikelRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }
    Optional<Artikel>  findById(long id){
        var sql= """
                select artikelId,naam,prijs,gewichtInGram,voorraad,maximumVoorraad,maxAantalInMagazijnPlaats
                from artikelen
                where artikelId=?
                """;
        return jdbcClient.sql(sql)
                .param(id)
                .query(Artikel.class)
                .optional();

    }
}
