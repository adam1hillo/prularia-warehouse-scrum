package be.vdab.scrumjava202406.bestellingen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MagazijnPlaatsRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final JdbcClient jdbcClient;

    public MagazijnPlaatsRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }



    public List<MagazijnPlaats> findAllPlaatsById(long id) {
        String sql = """ 
                SELECT magazijnPlaatsId, artikelId, rij, rek, aantal
                FROM magazijnplaatsen
                WHERE artikelId = ? ;""";
        return jdbcClient.sql(sql)
                .param(id)
                .query(MagazijnPlaats.class)
                .list();
    }

    public MagazijnPlaats findById(long id) {
        String sql = """ 
                SELECT magazijnPlaatsId, artikelId, rij, rek, aantal
                FROM magazijnplaatsen
                WHERE magazijnPlaatsId = ? ;""";
        return jdbcClient.sql(sql)
                .param(id)
                .query(MagazijnPlaats.class)
                .single();
    }

    public long findFirstFreePlaceId(){
        String sql = """ 
                SELECT magazijnPlaatsId
                FROM magazijnplaatsen
                WHERE aantal = 0
                LIMIT 1 ;""";
        return jdbcClient.sql(sql)
                .query(Long.class)
                .single();
    }
    public NieuweMagazijnPlaats findFirstFreePlace(long magazijnPlaatsId){
        String sql = """ 
                SELECT magazijnPlaatsId,rij,rek,aantal
                FROM magazijnplaatsen
                WHERE magazijnPlaatsId = ?
                LIMIT 1 ;""";
        return jdbcClient.sql(sql)
                .param(magazijnPlaatsId)
                .query(NieuweMagazijnPlaats.class)
                .single();
    }

    public Optional<MagazijnPlaats> checkFreeSpaceCanAddedToPlace(long id, int maxAantalInMagazijnPLaats){
        String sql = """ 
                SELECT magazijnPlaatsId, artikelId, rij, rek, aantal
                FROM magazijnplaatsen
                WHERE artikelId = ? AND aantal != ?;""";
        return jdbcClient.sql(sql)
                .params(id,maxAantalInMagazijnPLaats)
                .query(MagazijnPlaats.class)
                .optional();
    }

    public void updateAantal(long magazijnPlaatsId, int aantal){
        String sql = """ 
                UPDATE magazijnplaatsen
                SET aantal = aantal + ?
                WHERE magazijnPlaatsId = ?;""";
        if (jdbcClient.sql(sql).params(aantal, magazijnPlaatsId).update() == 0) {
            throw new MagazijnPlaatsNietGevondenException();
        }
    }
    public MagazijnPlaats updateAantalAndId(long magazijnPlaatsId, long artikelId, int aantal){
        String sql = """ 
                UPDATE magazijnplaatsen
                SET aantal = aantal + ?, artikelId = ?
                WHERE magazijnPlaatsId = ?;""";
        System.out.println(sql);
        if (jdbcClient.sql(sql).params(aantal, artikelId, magazijnPlaatsId).update() == 0) {
            throw new MagazijnPlaatsNietGevondenException();
        }
        return findById(magazijnPlaatsId);
    }
}
