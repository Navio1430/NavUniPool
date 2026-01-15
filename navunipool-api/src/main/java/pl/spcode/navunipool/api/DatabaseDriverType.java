package pl.spcode.navunipool.api;

public enum DatabaseDriverType {
  MYSQL("com.mysql.cj.jdbc.Driver", "jdbc:mysql://%s:%s/%s?sslMode=%s"),
  POSTGRESQL("org.postgresql.Driver", "jdbc:postgresql://%s:%s/%s?sslmode=%s");

  private final String driverClassName;
  private final String jdbcUrlFormat;

  DatabaseDriverType(String driverClassName, String jdbcUrlFormat) {
    this.driverClassName = driverClassName;
    this.jdbcUrlFormat = jdbcUrlFormat;
  }

  public String getDriverClassName() {
    return driverClassName;
  }

  public String getJdbcUrlFormat() {
    return jdbcUrlFormat;
  }

  public static String sslParamForMySQL(boolean enabled) {
    return enabled ? "REQUIRED" : "DISABLED";
  }

  public static String sslParamForPostgreSQL(boolean enabled) {
    return enabled ? "require" : "disable";
  }
}
