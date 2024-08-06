package be.vdab.scrumproject.leveringen;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
class InkomendeLeveringRepository {
    private final JdbcClient jdbcClient;

    InkomendeLeveringRepository (JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public long create (InkomendeLevering inkomendeLevering){
        var sql = """
                INSERT INTO inkomendeleveringen
                (leveranciersId, leveringsbonNummer, leveringsbondatum, leverDatum, ontvangerPersoneelslidId)
                VALUES (?, ?, ?, ?, ?)
                """;
        var keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql(sql)
                .params(inkomendeLevering.getLeveranciersId(),
                        inkomendeLevering.getLeveringsbonNummer(),
                        inkomendeLevering.getLeveringsbondatum(),
                        inkomendeLevering.getLeverDatum(),
                        InkomendeLevering.ontvangerPersoneelslidId)
                .update(keyHolder);
        return  keyHolder.getKey().longValue();
    }
}
