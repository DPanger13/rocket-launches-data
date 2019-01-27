# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.5.0](https://github.com/DPanger13/rocket-launches-data/tree/launches-v0.5.0) - 2018-01-25
### Added
- Initial release

## [0.6.0](https://github.com/DPanger13/rocket-launches-data/tree/launches-v0.6.0) - 2018-01-27
### Changed
- `LaunchModule` and Toothpick have been removed. Consumers should use their own dependency 
injection, if any. `ILaunchRepository` can now be created using its `create()` method.
