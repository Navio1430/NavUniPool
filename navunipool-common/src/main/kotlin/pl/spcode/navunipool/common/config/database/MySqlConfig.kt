package pl.spcode.navunipool.common.config.database

import eu.okaeri.configs.OkaeriConfig
import eu.okaeri.configs.annotation.Comment
import pl.spcode.navunipool.common.config.SqlDatabaseConfig

class MySqlConfig : OkaeriConfig(), SqlDatabaseConfig {

  @Comment(
    "Plugins which should use this specific database config.",
    "There's only 1 to 1 relationship.",
    "There can't be more than one data source per plugin.",
  )
  override var boundPlugins: List<String> = listOf()

  @Comment("Connection pool size") override var poolSize: Int = 5

  @Comment(
    "Connection timeout in milliseconds.",
    "This is the maximum time to wait for a connection from the pool.",
  )
  override var connectionTimeout: Long = 30000

  @Comment("Database username that should be used.") override var username: String = "admin"

  @Comment("Password to authenticate with the provided username.")
  override var password: String = "password"

  @Comment("Hostname (ip/domain) of the database.") override var hostname: String = "mysql"

  @Comment("Database name.") override var database: String = "default"

  @Comment("Port number of the database server.") override var port = 3306

  @Comment("Should SSL be enabled?") override var ssl: Boolean = false
}
