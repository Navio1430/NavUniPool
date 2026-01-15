package pl.spcode.navunipool.common.config

interface SqlDatabaseConfig {

  var boundPlugins: List<String>

  var poolSize: Int

  var connectionTimeout: Long

  var username: String

  var password: String

  var hostname: String

  var database: String

  var port: Int

  var ssl: Boolean
}
