package com.wwpass.fidelity.demo.dao;

import com.wwpass.fidelity.demo.domain.DemoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcDemoUserDao implements DemoUserDao {

    private final JdbcOperations jdbcOperations;

    @Autowired
    public JdbcDemoUserDao(@Qualifier("jdbcTemplate") JdbcOperations jdbcOperations) {
        if (jdbcOperations == null) {
            throw new IllegalArgumentException("jdbcOperations cannot be null");
        }
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    @Transactional(readOnly = true)
    public DemoUser getUser(int id) {
        return jdbcOperations.queryForObject(USER_QUERY + "id = ?", USER_MAPPER, id);
    }

    @Override
    @Transactional(readOnly = true)
    public DemoUser getUserByUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("username cannot be null");
        }
        try {
            return jdbcOperations.queryForObject(USER_QUERY + "username = ?", USER_MAPPER, username);
        } catch (EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Override
    public int createUser(final DemoUser userToAdd) {
        if (userToAdd == null) {
            throw new IllegalArgumentException("userToAdd cannot be null");
        }
        if (userToAdd.getId() != null) {
            throw new IllegalArgumentException("userToAdd.getId() must be null when creating a "+User.class.getName());
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcOperations.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(
                        "insert into demo_users(username,password,nickname,role_id) values (?, ?, ?, ?)",
                        new String[] { "id" });
                ps.setString(1, userToAdd.getUsername());
                ps.setString(2, userToAdd.getPassword());
                ps.setString(3, userToAdd.getNickname());
                ps.setInt(4, userToAdd.getRoleId());
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    private static final String USER_QUERY = "select id,username,password,nickname,role_id from demo_users where ";

    private static final RowMapper<DemoUser> USER_MAPPER = new UserRowMapper("demo_users.");

    static class UserRowMapper implements RowMapper<DemoUser> {
        private final String columnLabelPrefix;

        public UserRowMapper(String columnLabelPrefix) {
            this.columnLabelPrefix = columnLabelPrefix;
        }

        public DemoUser mapRow(ResultSet rs, int rowNum) throws SQLException {
            DemoUser user = new DemoUser();
            user.setId(rs.getInt(columnLabelPrefix + "id"));
            user.setUsername(rs.getString(columnLabelPrefix + "username"));
            user.setPassword(rs.getString(columnLabelPrefix + "password"));
            user.setNickname(rs.getString(columnLabelPrefix + "nickname"));
            user.setRoleId(rs.getInt(columnLabelPrefix + "role_id"));
            return user;
        }
    };
}
