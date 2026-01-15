package pl.spcode.navunipool.common.config

import eu.okaeri.configs.ConfigManager
import eu.okaeri.configs.OkaeriConfig
import eu.okaeri.configs.yaml.snakeyaml.YamlSnakeYamlConfigurer
import java.io.File
import kotlin.reflect.KClass

class ConfigLoader {

  fun <T : OkaeriConfig> load(configClass: KClass<T>, configFile: File): T {
    val configInstance =
      ConfigManager.create(configClass.java) {
        it.configure { opt ->
          opt.configurer(YamlSnakeYamlConfigurer())
          opt.bindFile(configFile)
        }
        it.saveDefaults()
        it.load(true)
      }

    return configInstance
  }
}
