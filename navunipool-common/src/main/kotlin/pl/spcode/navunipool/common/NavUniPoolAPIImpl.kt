package pl.spcode.navunipool.common

import pl.spcode.navunipool.api.NavUniPoolAPI
import pl.spcode.navunipool.common.api.DataSourceManagerImpl
import pl.spcode.navunipool.common.application.DataSourcesService
import pl.spcode.navunipool.common.config.GeneralConfig

class NavUniPoolAPIImpl
private constructor(private val manager: DataSourceManagerImpl, private val config: GeneralConfig) :
  NavUniPoolAPI(manager) {

  constructor(config: GeneralConfig) : this(createManager(config), config) {
    config.validateConfig()
  }

  companion object {
    private fun createDataSourcesService(config: GeneralConfig): DataSourcesService =
      DataSourcesService(config)

    private fun createManager(config: GeneralConfig): DataSourceManagerImpl =
      DataSourceManagerImpl(config, createDataSourcesService(config))
  }

  fun setAsSingletonInstance() {
    NavUniPoolAPI.setInstance(this)
  }

  fun closeAllSources() {
    manager.closeAllSources()
  }
}
