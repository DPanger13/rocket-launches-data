# Rocket Launches
Android library for rocket launch information

This library can be used in an Android app that requires information for upcoming rocket launches. It currently uses [Launch Library](https://launchlibrary.net/) as a provider. The project is split up into modules that can be individually imported as needed.

## Changelogs
Since this project is broken up into modules, there is no changelog for the whole project but rather one for each module. The individual changelogs can be found in their respective modules.

## Setup
### Initialization
Each module has an `Initializer` that needs called to do any setup for the module. Refer to the specific module's `Initializer` for more details.

### Logging
[Timber](https://github.com/JakeWharton/timber) is used in all modules for logging. By default, no logging is done. To add custom logging, `Tree`s can be planted according to Timber's documentation.
