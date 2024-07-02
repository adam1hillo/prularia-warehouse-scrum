package be.vdab.scrumjava202406.leveringen;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
class InkomendeLeveringRepository {
    private final JdbcClient jdbcClient;

    InkomendeLeveringRepository (JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }


}
