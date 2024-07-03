package be.vdab.scrumjava202406.leveringen;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LeveranciersRepository {
    private final JdbcClient jdbcClient;

    public LeveranciersRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<LeverancierIdNaam> findAll() {
        var sql = """
                select leveranciersId, naam from leveranciers
                """;
        return jdbcClient.sql(sql)
                .query(LeverancierIdNaam.class)
                .list();
    }
}
