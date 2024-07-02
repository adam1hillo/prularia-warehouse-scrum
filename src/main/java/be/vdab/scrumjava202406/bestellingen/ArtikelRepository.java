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

    void updateTotaleVoorraadPerArtikel(long artikelId, AanpassingVoorraadMetAantal aantal) {
        var sql = """
                update Artikelen
                set voorraad = voorraad - ?
                where artikelId = ?
                """;
        if (jdbcClient.sql(sql).params(aantal.aantal(), artikelId).update() == 0) {
            throw new ArtikelNietGevondenException(artikelId);
        }
    }

    void updateMagazijnVoorraad(RijRekNieuweAantal rijRekNieuweAantal) {
        var sql = """
                UPDATE MagazijnPlaatsen
                SET aantal = aantal - ?
                WHERE rij = ? AND rek = ?
                """;

        jdbcClient.sql(sql).params(rijRekNieuweAantal.aantal(), String.valueOf(rijRekNieuweAantal.rij()), rijRekNieuweAantal.rek()).update();
    }
}