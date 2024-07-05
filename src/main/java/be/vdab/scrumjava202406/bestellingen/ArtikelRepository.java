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

    public Optional<Artikel> findById(long id) {
        String sql = """
               select artikelId, ean, naam,beschrijving,prijs,gewichtInGram,bestelpeil,voorraad,minimumVoorraad,maximumVoorraad,levertijd,aantalBesteldLeverancier,maxAantalInMagazijnPlaats,leveranciersId
               from Artikelen
               where artikelId=?
               """;
        return jdbcClient.sql(sql)
                .param(id)
                .query(Artikel.class)
                .optional();
    }

    Optional<Artikel> findByEanLastFive(String eanLastFive) {
        var sql = """
               select artikelId, ean, naam,beschrijving,prijs,gewichtInGram,bestelpeil,voorraad,minimumVoorraad,maximumVoorraad,levertijd,aantalBesteldLeverancier,maxAantalInMagazijnPlaats,leveranciersId
               from Artikelen
               where ean like ?
               """;
        String like = "%" + eanLastFive;
        return jdbcClient.sql(sql)
                .param(like)
                .query(Artikel.class)
                .optional();
    }

    public void verhoogVoorraad(long artikelId, int voorraad) {
        String sql = """ 
                UPDATE Artikelen
                SET voorraad = voorraad + ?
                WHERE artikelId = ?;""";
        if (jdbcClient.sql(sql).params(voorraad, artikelId).update() == 0) {
            throw new ArtikelNietGevondenException(artikelId);
        }
    }
}
