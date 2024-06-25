package be.vdab.scrumjava202406.bestellingen;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class bestelLijnenOverzichtDTORepository {
    private final JdbcClient jdbcClient;

    public bestelLijnenOverzichtDTORepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<bestelLijnenOverzichtDTO> findOudsteBestelling() {
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
                .query(bestelLijnenOverzichtDTO.class)
                .list();
    }
    List<BestellingLijnenArtikelNaam> bestellingLijnenArtikelNaam(){
        var sql = """
                  select b.bestelId,a.artikelId,a.naam,b.aantalBesteld,
                    from bestellijnen b
                    inner join artikelen a on b.artikelId = a.artikelId
                    where b.bestelId = (SELECT bestelId
                FROM bestellingen
                where bestellingsStatusId = 2
                ORDER BY besteldatum ASC
                LIMIT 1)
                """;
        return jdbcClient.sql(sql)
                .query(BestellingLijnenArtikelNaam.class)
                .list();
    }
}
