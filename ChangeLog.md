# Change Log
All notable changes to this project will be documented in this file.

## [X.X.X] - yyyy-mm-dd
 
### Added
 
### Changed
 
### Fixed

## [2.0.3](https://github.com/cgjeffries/SER516-Team-Boston/releases/tag/2.0.3) - 2024-04-18
 
### Added
- New service for scope change
 
## [2.0.2](https://github.com/cgjeffries/SER516-Team-Boston/releases/tag/2.0.2) - 2024-04-17
 
### Added
- New service for task excess 

## [2.0.1](https://github.com/cgjeffries/SER516-Team-Boston/releases/tag/2.0.1) - 2024-04-17
 
### Added
- New service for task inertia

## [1.2.0](https://github.com/cgjeffries/SER516-Team-Boston/releases/tag/1.2.0) - 2024-04-08
 
### Added
- Router Microservice which is responsible for routing requests from a client to the appropriate microservice.
- library for the native java code to interface with the router microservice easily
- the PBHealth microservice which contains the logic that was in the native implementation of PBHealth
- Microservice for Scope Change
 
### Changed
- Restructure to a monorepo in preparation for refactoring into microservices
- The native java client now utilizes the deployed PBHealth microservice rather than its own native implementation.

### Fixed

## [1.1.6](https://github.com/cgjeffries/SER516-Team-Boston/releases/tag/1.1.6) - 2024-04-08
 
### Added
- Microservice for Scope Change


## [1.1.5](https://github.com/cgjeffries/SER516-Team-Boston/releases/tag/1.1.5) - 2024-04-08
 
### Changed
- The native java client now utilizes the deployed PBHealth microservice rather than its own native implementation.


## [1.1.4](https://github.com/cgjeffries/SER516-Team-Boston/releases/tag/1.1.4) - 2024-04-08
 
### Added
- PBHealth microservice which contains the logic that was in the native implementation of PBHealth


## [1.1.3](https://github.com/cgjeffries/SER516-Team-Boston/releases/tag/1.1.3) - 2024-04-08
 
### Added
- library for the native java code to interface with the router microservice easily


## [1.1.2](https://github.com/cgjeffries/SER516-Team-Boston/releases/tag/1.1.2) - 2024-04-08
 
### Added
- Router Microservice which is responsible for routing requests from a client to the appropriate microservice.


## [1.1.1](https://github.com/cgjeffries/SER516-Team-Boston/releases/tag/1.1.1) - 2024-03-29
 
### Changed
- Restructure to a monorepo in preparation for refactoring into microservices


## [1.1.0](https://github.com/cgjeffries/SER516-Team-Boston/releases/tag/1.1.0) - 2024-03-25
 
### Added
- The sprint being viewed is now displayed as a label in the visualization window
- The ability to set a custom date range for the Lead Time metric
- Scatter Plot visualization to the Lead Time metric
- ability to set a custom date range for the Cycle Time metric

### Changed
- The sprint being viewed is now displayed as a label in the visualization window
- Multiple sprints' burndown charts can now be overlayed on the same graph and compared.

### Fixed

## [1.0.5](https://github.com/cgjeffries/SER516-Team-Boston/releases/tag/1.0.5) - 2024-03-25
 
### Added
- the ability to set a custom date range for the Cycle Time metric


## [1.0.4](https://github.com/cgjeffries/SER516-Team-Boston/releases/tag/1.0.4) - 2024-03-21
 
### Added
- Scatter Plot visualization to the Lead Time metric


## [1.0.3](https://github.com/cgjeffries/SER516-Team-Boston/releases/tag/1.0.3) - 2024-03-20
 
### Added
- the ability to set a custom date range for the Lead Time metric


## [1.0.2](https://github.com/cgjeffries/SER516-Team-Boston/releases/tag/1.0.2) - 2024-03-20
 
### Added
- The sprint being viewed is now displayed in as a label in the visualization window


## [1.0.1](https://github.com/cgjeffries/SER516-Team-Boston/releases/tag/1.0.1) - 2024-03-18
 
### Added
- Multiple sprints' burndown charts can now be overlayed on the same graph and compared.


## [Period-2-release](https://github.com/cgjeffries/SER516-Team-Boston/releases/tag/Period-2-release) - 2024-03-01


## [Period-1-release](https://github.com/cgjeffries/SER516-Team-Boston/releases/tag/Period-1-release) - 2024-02-18
 
### Added

- [US-4](https://github.com/cgjeffries/SER516-Team-Boston/pull/1)
functionality for selecting the burndown metric and a project to calculate the metric on.
- [US-5](https://github.com/cgjeffries/SER516-Team-Boston/pull/2)
ability to select a sprint to apply the metric to.
- [US-6](https://github.com/cgjeffries/SER516-Team-Boston/pull/4)
calculating task, user story, and business value burndown charts.
- [US-7](https://github.com/cgjeffries/SER516-Team-Boston/pull/5)
burndown visualizations for task, user story, and business value
- [US-8](https://github.com/cgjeffries/SER516-Team-Boston/pull/7)
cycle time metric selection and error handling for project selection.
- [US-10](https://github.com/cgjeffries/SER516-Team-Boston/pull/6)
api calls and calculations for cycle time to the project
- [US-11](https://github.com/cgjeffries/SER516-Team-Boston/pull/8)
cycle time visualization to the application.
- [US-12](https://github.com/cgjeffries/SER516-Team-Boston/pull/10)
lead time metric selection, unit test project selection
- [US-14](https://github.com/cgjeffries/SER516-Team-Boston/pull/9)
extracting Lead Time info from User Story histories
- [US-15](https://github.com/cgjeffries/SER516-Team-Boston/pull/13)
visualization for lead time..

### Changed
- [US-13](https://github.com/cgjeffries/SER516-Team-Boston/pull/11)
unit testing and javadoc commenting where needed to metric configurations

