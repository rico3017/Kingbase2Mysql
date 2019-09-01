package com.bucr.util;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ColumnMetaData {

  private static Map<Integer, String> sqlTypes;

  static {
    sqlTypes = new HashMap<>();
    try {
      Class clazz = Class.forName("java.sql.Types");
      Field[] fields = clazz.getFields();
      for (Field f : fields) {
        sqlTypes.put(f.getInt(clazz), f.getName());
      }
    } catch (ClassNotFoundException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  private String columnName;
  private String dataType;
  private int columnSize;
  private int nullable;

  public ColumnMetaData(ResultSet resultSet) throws SQLException {
    columnName = resultSet.getString("COLUMN_NAME");
    dataType = sqlTypes.get(resultSet.getInt("DATA_TYPE"));
    columnSize = resultSet.getInt("COLUMN_SIZE");
  }

  public String generateSql() {
    StringBuilder builder = new StringBuilder();
    builder.append(columnName) .append(" ").append(dataType).append(" ");
    if (!dataType.toLowerCase().equals("date") && !dataType.toLowerCase().equals("boolean")) {
      builder.append("(").append(columnSize).append(")");
    }
    if (nullable == 0) {
      builder.append("NOT NULL");
    }
    builder.append(",");
    return builder.toString();
  }


  public String getColumnName() {
    return columnName;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  public String getDataType() {
    return dataType;
  }

  public void setDataType(String dataType) {
    this.dataType = dataType;
  }

  public int getColumnSize() {
    return columnSize;
  }

  public void setColumnSize(int columnSize) {
    this.columnSize = columnSize;
  }

  public int getNullable() {
    return nullable;
  }

  public ColumnMetaData setNullable(int nullable) {
    this.nullable = nullable;
    return this;
  }
}
