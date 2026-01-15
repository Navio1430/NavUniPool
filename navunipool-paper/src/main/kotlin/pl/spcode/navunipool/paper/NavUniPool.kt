package pl.spcode.navunipool.paper

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import pl.spcode.navunipool.common.NavUniPoolAPIImpl
import pl.spcode.navunipool.common.config.ConfigLoader
import pl.spcode.navunipool.common.config.GeneralConfig

class NavUniPool : JavaPlugin() {

  val logger: Logger = LoggerFactory.getLogger(NavUniPool::class.java)

  var apiImpl: NavUniPoolAPIImpl? = null

  override fun onLoad() {
    logger.info("Loading NavUniPool...")
    try {
      val configLoader = ConfigLoader()
      val config = configLoader.load(GeneralConfig::class, dataFolder.resolve("general.yml"))

      apiImpl = NavUniPoolAPIImpl(config)
      apiImpl!!.setAsSingletonInstance()

      logger.info("NavUniPool Loaded")
    } catch (e: Exception) {
      logger.error("Error while loading NavUniPool. Shutting down the server...", e)
      Bukkit.getServer().shutdown()
    }
  }

  override fun onEnable() {
    logger.info("NavUniPool enabled!")
  }

  override fun onDisable() {
    apiImpl?.closeAllSources()
    logger.info("NavUniPool disabled!")
  }
}
