## What is NavUniPool?
A project created for unifying connection pool management.

## Warning
**NavUniPool is a project that can change entirely, because it is only made as a prototype.**

## The problem
If your network contains a lot of pods which have a lot of connection pools
(connection pool per plugin) then the database management and optimizing can be a horrific experience.

## How does it work?
**NavUniPool works** as a plugin that you've direct access to from other plugins.
Each plugin which wants to acquire a connection to the database needs to get
a data source via NavUniPoolAPI instance.

## Supported databases
MySQL, PostgreSQL

## How to use?
Put **NavUniPool** as a required dependency into your `paper-plugin.yml` or `plugin.yml`.

```yaml
(paper-plugin.yml)
...
dependencies:
  server:
    NavUniPool:
      load: BEFORE
      required: true
      join-classpath: true
```

Then access **NavUniPoolAPI instance** in your plugin code.
Works for both Java and Kotlin.
```kotlin
(kotlin)
val navUniPool = NavUniPoolAPI.getInstance()
val dataSource = navUniPool.provideDataSource(plugin, listOf(DatabaseDriverType.POSTGRESQL, DatabaseDriverType.MYSQL))
// use dataSource as you like to
```

Close the data source:

**DO NOT CLOSE IT WITH ANY OTHER METHOD!**
```kotlin
navUniPool.closeDataSource(plugin)
```