package pl.spcode.navunipool.common.config

import eu.okaeri.configs.OkaeriConfig
import eu.okaeri.configs.annotation.Comment
import pl.spcode.navunipool.api.DatabaseDriverType
import pl.spcode.navunipool.api.exception.DataSourceConfigError
import pl.spcode.navunipool.common.config.database.MySqlConfig
import pl.spcode.navunipool.common.config.database.PostgresConfig

class GeneralConfig : OkaeriConfig() {

  //  @Comment("Priority used to pick datasource.")
  //  var priorityList: List<DatabaseDriverType> = listOf(
  //      DatabaseDriverType.POSTGRESQL,
  //      DatabaseDriverType.MYSQL,
  //      DatabaseDriverType.SQLITE,
  //      DatabaseDriverType.H2_FILE
  //  )

  @Comment("POSTGRESQL data source configuration") var postgresConfig = PostgresConfig()

  @Comment("MYSQL data source configuration") var mysqlConfig = MySqlConfig()

  private fun getSources(): List<SqlDatabaseConfig> {
    return listOf(this.postgresConfig, this.mysqlConfig)
  }

  /** @throws DataSourceConfigError if plugin was bound more than 1 time. */
  fun validateConfig() {
    val sources = getSources()

    // change each plugin name to lowercase
    sources.forEach { source -> source.boundPlugins = source.boundPlugins.map { it.lowercase() } }

    val boundPluginsSet = mutableSetOf<String>()

    sources.forEach {
      it.boundPlugins.forEach { pluginName ->
        if (boundPluginsSet.contains(pluginName)) {
          throw DataSourceConfigError("Plugin '$pluginName' can't be bound to more than 1 source.")
        }
        boundPluginsSet.add(pluginName)
      }
    }
  }

  /** @throws pl.spcode.navunipool.api.exception.DataSourceNotConfigured */
  fun getSqlDatabaseConfig(
    pluginName: String,
    availableDriverTypes: MutableList<DatabaseDriverType>,
  ): SqlDatabaseConfig {
    val sources = getSources()

    sources.forEach { source ->
      if (source.boundPlugins.contains(pluginName.lowercase())) {
        return source
      }
    }

    throw DataSourceConfigError(
      "Plugin '$pluginName' needs to be bound to one of the source types: ${availableDriverTypes.joinToString(", ") { it.name }}."
    )
  }
}
