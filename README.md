# Rocket Launches
Android library for rocket launch information

This library can be used in an Android app that requires information for upcoming rocket launches. It currently uses [Launch Library](https://launchlibrary.net/) as a provider. The project is split up into modules that can be individually imported as needed.

## Changelogs
Since this project is broken up into modules, there is no changelog for the whole project but rather one for each module. The individual changelogs can be found in their respective modules.

## Dependency Injection
[Toothpick](https://github.com/stephanenicolas/toothpick) is used for dependency injection. Modules are provided so necessary dependencies (usually `*Repository`) can be injected. Setup and use of Toothpick can be found at the linked GitHub page.

## Dates/Times
[ThreeTen Android Backport](https://github.com/JakeWharton/ThreeTenABP) is used for dates and times. Setup should be followed according to the instructions on the linked GitHub page.

## Logging
[Timber](https://github.com/JakeWharton/timber) is used for logging. Logging can be customized by following initialization steps outlined in the linked GitHub page.
