package next.dao;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import next.model.User;

@Slf4j
public class UserDao {
    public void insert(User user) {
        new JdbcTemplate().update("INSERT INTO USERS VALUES (?, ?, ?, ?);", (pstmt) -> {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
        });
    }

    public void update(User user) {
        new JdbcTemplate().update("UPDATE users SET password = ?, name = ?, email = ? WHERE userId = ?;", user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }

    public List<User> findAll() {
        JdbcTemplate<User> jdbcTemplate = new JdbcTemplate<>();

        return jdbcTemplate.query("SELECT userId, password, name, email FROM USERS;",
                (rs) -> new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"), rs.getString("email")));
    }

    public User findByUserId(String userId) {
        JdbcTemplate<User> jdbcTemplate = new JdbcTemplate<>();

        return jdbcTemplate.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userId = ?;",
                (rs) -> new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"), rs.getString("email")),
                (pstmt) -> pstmt.setString(1, userId));
    }
}
