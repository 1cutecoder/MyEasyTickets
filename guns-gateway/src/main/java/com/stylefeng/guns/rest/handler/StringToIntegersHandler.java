package com.stylefeng.guns.rest.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 *
 * Created by cute coder
 * 2019/5/26 21:03
 */


public class StringToIntegersHandler extends BaseTypeHandler<Integer[]> {


  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, Integer[] parameter, JdbcType jdbcType) throws SQLException {
    StringBuffer sb = new StringBuffer();
    sb.append("[");
    sb.append(parameter[0]);
    for (int j = 1; j < parameter.length; j++) {
      sb.append(",");
      sb.append(parameter[i]);
    }
    sb.append("]");
    ps.setString(i, sb.toString());
  }

  @Override
  public Integer[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
    String sb = rs.getString(columnName);
    Integer[] integers = null;
    if (sb.length() == 3) {
      String substring = sb.substring(1, 2);
      integers = new Integer[1];
      integers[0] = Integer.parseInt(substring);
      return integers;
    }
    if (sb != null) {
      String s = sb.substring(1, sb.length() - 1);
      String[] split = s.split(",");
      integers = new Integer[split.length];
      for (int i = 0; i < integers.length; i++) {
        integers[i] = Integer.parseInt(split[i]);
      }
    }

    return integers;
  }

  @Override
  public Integer[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    String sb = rs.getString(columnIndex);
    Integer[] integers = null;
    if (sb.length() == 3) {
      String substring = sb.substring(1, 2);
      integers = new Integer[1];
      integers[0] = Integer.parseInt(substring);
      return integers;
    }
    if (sb != null) {
      String s = sb.substring(1, sb.length() - 1);
      String[] split = s.split(",");
      integers = new Integer[split.length];
      for (int i = 0; i < integers.length; i++) {
        integers[i] = Integer.parseInt(split[i]);
      }
    }

    return integers;
  }

  @Override
  public Integer[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    String sb = cs.getString(columnIndex);
    Integer[] integers = null;
    if (sb.length() == 3) {
      String substring = sb.substring(1, 2);
      integers = new Integer[1];
      integers[0] = Integer.parseInt(substring);
      return integers;
    }
    if (sb != null) {
      String s = sb.substring(1, sb.length() - 1);
      String[] split = s.split(",");
      integers = new Integer[split.length];
      for (int i = 0; i < integers.length; i++) {
        integers[i] = Integer.parseInt(split[i]);
      }
    }

    return integers;
  }
}
