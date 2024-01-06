package com.mateus98.caffeine.contrato;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ContratoRepository {

    final JdbcTemplate jdbcTemplate;

    public List<Contrato> readAll() {
        return jdbcTemplate.query("SELECT * FROM Contrato", new BeanPropertyRowMapper<>(Contrato.class));
    }
    public Contrato read(Long id) {
        String sql = "SELECT * FROM Contrato WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Contrato.class), id);
        }catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    public Long save(Contrato contrato) {
        String sql = "INSERT INTO Contrato (tipo, assinadoEm) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, contrato.getTipo());
            ps.setDate(2, Date.valueOf(contrato.getAssinadoEm()));
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }


    public void update(Contrato contrato) {
        String sql = "UPDATE Contrato SET tipo = ?, assinadoEm = ? WHERE id = ?";
        jdbcTemplate.update(sql,  contrato.getTipo(), contrato.getAssinadoEm(), contrato.getId());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM Contrato WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }


}
