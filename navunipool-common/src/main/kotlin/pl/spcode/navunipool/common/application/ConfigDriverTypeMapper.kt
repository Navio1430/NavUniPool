package pl.spcode.navunipool.common.application

import pl.spcode.navunipool.api.DatabaseDriverType
import pl.spcode.navunipool.common.config.GeneralConfig
import pl.spcode.navunipool.common.config.SqlDatabaseConfig
import pl.spcode.navunipool.common.config.database.MySqlConfig
import pl.spcode.navunipool.common.config.database.PostgresConfig

fun SqlDatabaseConfig.toDriverType(): DatabaseDriverType {
  return when (this) {
    is PostgresConfig -> DatabaseDriverType.POSTGRESQL
    is MySqlConfig -> DatabaseDriverType.MYSQL
    else -> throw NotImplementedError()
  }
}

fun DatabaseDriverType.toConfig(generalConfig: GeneralConfig): SqlDatabaseConfig {
  return when (this) {
    DatabaseDriverType.MYSQL -> generalConfig.mysqlConfig
    DatabaseDriverType.POSTGRESQL -> generalConfig.postgresConfig
  }
}
