package be.vdab.scrumjava202406.bestellingen;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MagazijnPlaatsRepository {
    private final JdbcClient jdbcClient;

    public MagazijnPlaatsRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }



    List<MagazijnPlaats> findAllPlaatsById(long id) {
        String sql = """ 
                SELECT magazijnPlaatsId, artikelId, rij, rek, aantal
                FROM MagazijnPlaatsen
                WHERE artikelId = ? ;""";
        return jdbcClient.sql(sql)
                .param(id)
                .query(MagazijnPlaats.class)
                .list();
    }
}
