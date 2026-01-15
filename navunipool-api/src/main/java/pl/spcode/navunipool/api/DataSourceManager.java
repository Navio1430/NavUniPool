package pl.spcode.navunipool.api;

import com.zaxxer.hikari.HikariDataSource;
import java.util.List;
import pl.spcode.navunipool.api.exception.DataSourceException;

public interface DataSourceManager {

  HikariDataSource provideDataSource(
      String pluginName, List<DatabaseDriverType> availableDriverTypes) throws DataSourceException;

  void closeSource(String pluginName);
}
