# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.1.0](https://github.com/DPanger13/rocket-launches-data/tree/base-v0.1.0) - 2018-01-25
### Added
- Initial release

## [0.2.0](https://github.com/DPanger13/rocket-launches-data/tree/base-v0.1.0) - 2018-01-26
### Changed
- Initialization of modules (ie, ThreeTen ABP) is no longer forced on the consumer. Initialization
of the base module, specifically, is done through `Initializer`. Each child module should have a 
class that extends `Initializer`, overrides `init()` (calls through to the super method), then does
their own initialization logic, if any.
