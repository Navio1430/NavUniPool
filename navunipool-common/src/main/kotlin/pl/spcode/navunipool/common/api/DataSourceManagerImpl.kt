package pl.spcode.navunipool.common.api

import com.zaxxer.hikari.HikariDataSource
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import pl.spcode.navunipool.api.DataSourceManager
import pl.spcode.navunipool.api.DatabaseDriverType
import pl.spcode.navunipool.common.application.DataSourcesService
import pl.spcode.navunipool.common.application.toDriverType
import pl.spcode.navunipool.common.config.GeneralConfig

class DataSourceManagerImpl(val config: GeneralConfig, val dataSourcesService: DataSourcesService) :
  DataSourceManager {

  private val logger: Logger = LoggerFactory.getLogger(DataSourceManagerImpl::class.java)

  private val activeSourceMap = mutableMapOf<DatabaseDriverType, MutableSet<String>>()

  /**
   * Provides DataSource with one of the available types.
   *
   * @throws pl.spcode.navunipool.api.exception.DataSourceException
   */
  override fun provideDataSource(
    pluginName: String,
    availableDriverTypes: MutableList<DatabaseDriverType>,
  ): HikariDataSource {
    val config = config.getSqlDatabaseConfig(pluginName, availableDriverTypes)
    val driverType = config.toDriverType()

    activeSourceMap.getOrPut(driverType) { mutableSetOf() }.add(pluginName.lowercase())

    return dataSourcesService.getOrCreateDataSource(driverType)
  }

  override fun closeSource(pluginName: String) {
    activeSourceMap.forEach { (type, pluginNames) ->
      if (pluginNames.contains(pluginName.lowercase())) {
        pluginNames.remove(pluginName.lowercase())

        if (pluginNames.isEmpty()) {
          activeSourceMap.remove(type)
          dataSourcesService.closeDataSource(type)
        }
      }
    }
  }

  fun closeAllSources() {
    dataSourcesService.getAll().forEach {
      try {
        it.value.close()
      } catch (e: Exception) {
        logger.error("error occurred while trying to close '${it.key.name}' data source", e)
      }
    }
  }
}
