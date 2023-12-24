package next.dao;

import core.jdbc.ConnectionManager;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JdbcTemplate<T> {

  public <T> List<T> query(String sql, RowMapper<T> rowMapper, PreparedStatementSetter pstmtSetter) throws RuntimeException {
    ResultSet rs = null;
    try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql); ) {
      pstmtSetter.setValues(pstmt);
      rs = pstmt.executeQuery();

      List<T> queryResult = new ArrayList<T>();
      while (!rs.next()) {
        queryResult.add(rowMapper.mapRow(rs));
      }

      return queryResult;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      if (rs != null) {
        try {
          rs.close();
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }

  public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... values) throws RuntimeException {
    ResultSet rs = null;
    try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql); ) {

      setValues(pstmt, values);
      rs = pstmt.executeQuery();

      List<T> queryResult = new ArrayList<T>();
      while (!rs.next()) {
        queryResult.add(rowMapper.mapRow(rs));
      }

      return queryResult;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      try {
        if (rs != null) {
          rs.close();
        }
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public T queryForObject(String sql, RowMapper<T> rowMapper, PreparedStatementSetter pss) {
    List<T> queryResult = query(sql, rowMapper, pss);

    if (queryResult.isEmpty()) {
      return null;
    }

    return queryResult.get(0);
  }

  public void update(String sql, PreparedStatementSetter pstmtSetter) {

    try (Connection  con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
      pstmtSetter.setValues(pstmt);

      log.debug("result :{} row(s) updated", pstmt.executeUpdate());
    } catch(SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void update(String sql, Object... values) {

    try (Connection  con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
      setValues(pstmt, values);

      log.debug("result :{} row(s) updated", pstmt.executeUpdate());
    } catch(SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private void setValues(PreparedStatement pstmt, Object... values) throws SQLException {
    for (int i = 0; i < values.length; i++) {
      pstmt.setObject(i + 1, values[i]);
    }
  }
}
