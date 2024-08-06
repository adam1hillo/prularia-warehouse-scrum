package be.vdab.scrumproject.bestellingen;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BestelLijnenRepository {
    private final JdbcClient jdbcClient;

    public BestelLijnenRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<MagazijnplaatsAantalMetArtikelId> findAllMagazijnplaatsPerArtikelAndBestelId(long bestelId) {
        var sql = """
                              select mp.rek,mp.rij,mp.aantal,a.artikelId
                                  from Bestellijnen b
                                  inner join Artikelen a on b.artikelId = a.artikelId
                                  inner join MagazijnPlaatsen mp on mp.artikelId = a.artikelId
                                  where b.bestelId = ?
                """;
        return jdbcClient.sql(sql)
                .param(bestelId)
                .query(MagazijnplaatsAantalMetArtikelId.class)
                .list();
    }

    List<BestelLijnenArtikelNaam> findOudsteBestelLijnenMetArtikelNaamEnId(){
        var sql = """
                  select b.bestelId,a.artikelId,a.naam,b.aantalBesteld
                    from Bestellijnen b
                    inner join Artikelen a on b.artikelId = a.artikelId
                    where b.bestelId = (SELECT bestelId
                FROM Bestellingen
                where bestellingsStatusId = 2
                ORDER BY besteldatum
                LIMIT 1)
                """;
        return jdbcClient.sql(sql)
                .query(BestelLijnenArtikelNaam.class)
                .list();
    }
}
