package be.vdab.scrumjava202406.leveringen;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class InkomendeleveringslijnRepository {
private final JdbcClient jdbcClient;
InkomendeleveringslijnRepository (JdbcClient jdbcClient) {
    this.jdbcClient = jdbcClient;
}

public void create(Inkomendeleveringslijn inkomendeleveringslijn) {
    var sql = """
            insert into inkomendeleveringslijnen (inkomendeLeveringsId, artikelId, aantalGoedgekeurd, aantalTeruggestuurd, magazijnPlaatsId)
            values (?, ?, ?, ?, 0);
            """;
    jdbcClient.sql(sql)
            .params(inkomendeleveringslijn.getInkomendeLeveringsId(),
                    inkomendeleveringslijn.getArtikelId(),
                    inkomendeleveringslijn.getAantalGoedgekeurd(),
                    inkomendeleveringslijn.getAantalTeruggestuurd())
            .update();
}

}
