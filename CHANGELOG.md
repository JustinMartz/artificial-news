# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.3.0] - 2025-xx-xx

### Added

- [UI] Alternating row colors for list of articles

- [UI] Disabled pagination navigation buttons are now gray

### Fixed

- [UI] Fixed defect where "days ago" was showing "1 day ago" for an article created today.

## [0.2.2] - 2025-10-12

### Fixed

- [UI] Hotfix change for new paging defect

## [0.2.1] - 2025-10-12

### Fixed

- [UI] Fixed incorrect paging info for 0 results
- [UI] Fixed long headlines wrapping to second row

### Changed

- [UI] Update dependencies
- [service] Update dependencies

## [0.2.0] - 2025-07-31

### Added

- [UI] "View articles" page
- [UI] "View articles" button on welcome page
- [service] Hide `createdAt` Article property in JSON

### Changed

- [service] /articles `POST` endpoint support for pagination

## [0.1.0] - 2025-05-29

### Added

- [UI] Landing page with button to generate article
- [UI] Loading page with spinner and text
- [UI] Dynamically-generated article page
- [service] Controllers and service implementations for generating article text and images
- [service] Custom exceptions
- [service] `dev` and `prod` Spring profiles

[0.3.0]: https://github.com/JustinMartz/artificial-news/releases/tag/v0.3.0
[0.2.2]: https://github.com/JustinMartz/artificial-news/releases/tag/v0.2.2
[0.2.1]: https://github.com/JustinMartz/artificial-news/releases/tag/v0.2.1
[0.2.0]: https://github.com/JustinMartz/artificial-news/releases/tag/v0.2.0
[0.1.0]: https://github.com/JustinMartz/artificial-news/releases/tag/v0.1.0
