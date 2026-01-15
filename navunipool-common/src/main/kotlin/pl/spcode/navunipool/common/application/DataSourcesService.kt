package pl.spcode.navunipool.common.application

import com.zaxxer.hikari.HikariDataSource
import pl.spcode.navunipool.api.DatabaseDriverType
import pl.spcode.navunipool.common.config.GeneralConfig
import pl.spcode.navunipool.common.config.SqlDatabaseConfig

class DataSourcesService(val config: GeneralConfig) {

  private val dataSources = mutableMapOf<DatabaseDriverType, HikariDataSource>()

  @Synchronized
  fun getOrCreateDataSource(driverType: DatabaseDriverType): HikariDataSource {
    val existingSource = dataSources[driverType]
    if (existingSource != null) {
      return existingSource
    }

    val newDataSource = connectToDataSource(driverType, driverType.toConfig(config))
    dataSources[driverType] = newDataSource
    return newDataSource
  }

  fun closeDataSource(driverType: DatabaseDriverType) {
    val source = dataSources.remove(driverType)
    source?.close()
  }

  private fun connectToDataSource(
    driverType: DatabaseDriverType,
    config: SqlDatabaseConfig,
  ): HikariDataSource {
    val dataSource = HikariDataSource()
    dataSource.poolName = "NavUniPool-${driverType.name}"

    dataSource.addDataSourceProperty("cachePrepStmts", "true")
    dataSource.addDataSourceProperty("prepStmtCacheSize", "250")
    dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
    dataSource.addDataSourceProperty("useServerPrepStmts", "true")

    dataSource.maximumPoolSize = config.poolSize
    dataSource.connectionTimeout = config.connectionTimeout
    dataSource.username = config.username
    dataSource.password = config.password

    val driverJdbcFormat = driverType.jdbcUrlFormat
    val jdbcUrl: String =
      when (driverType) {
        DatabaseDriverType.POSTGRESQL -> {
          driverJdbcFormat.format(
            config.hostname,
            config.port,
            config.database,
            DatabaseDriverType.sslParamForPostgreSQL(config.ssl),
          )
        }
        DatabaseDriverType.MYSQL -> {
          driverJdbcFormat.format(
            config.hostname,
            config.port,
            config.database,
            DatabaseDriverType.sslParamForMySQL(config.ssl),
          )
        }
      }

    dataSource.driverClassName = driverType.driverClassName
    dataSource.jdbcUrl = jdbcUrl

    return dataSource
  }

  fun getAll(): Map<DatabaseDriverType, HikariDataSource> {
    return dataSources
  }
}
