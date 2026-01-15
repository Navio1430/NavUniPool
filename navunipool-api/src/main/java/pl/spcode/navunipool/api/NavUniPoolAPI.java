package pl.spcode.navunipool.api;

import com.zaxxer.hikari.HikariDataSource;
import java.util.List;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public abstract class NavUniPoolAPI {

  private final DataSourceManager dataSourceManager;

  protected NavUniPoolAPI(DataSourceManager dataSourceManager) {
    this.dataSourceManager = dataSourceManager;
  }

  private static NavUniPoolAPI instance;

  protected static void setInstance(NavUniPoolAPI instance) {
    NavUniPoolAPI.instance = instance;
  }

  public static NavUniPoolAPI getInstance() {
    return instance;
  }

  public void closeDataSource(JavaPlugin plugin) {
    dataSourceManager.closeSource(getPluginIdentifier(plugin));
  }

  public HikariDataSource provideDataSource(
      JavaPlugin plugin, List<DatabaseDriverType> availableTypes) {
    return dataSourceManager.provideDataSource(getPluginIdentifier(plugin), availableTypes);
  }

  private String getPluginIdentifier(JavaPlugin plugin) {
    return plugin.getName();
  }
}
