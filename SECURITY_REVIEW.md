# Source review

Reviewed on 2026-10-28, starting from commit `a0e88eece116f2fa5963674ad3ec227ed7f20b8f`, plus the compatibility and CI changes in this revision.

## Scope and findings

All five Java source files, both resource JSON files, Gradle scripts, workflow files and wrapper scripts were inspected.

- No direct HTTP/HTTPS clients, webhooks, sockets or arbitrary external endpoints were found in the addon's Java sources.
- No chat messages, private messages, server commands, or coordinate reporting to other players were found.
- No account/session/token collection, arbitrary file reads, process execution, reflection-based payload loading, or encoded executable payloads were found in the Java sources.
- `HorseFly` changes the locally controlled horse's movement. Normal Minecraft networking can report that movement and position to the connected server. That is distinct from covert reporting to a third party.
- `ViaClientVehicleStorageMixin` optionally overrides translated steering inputs with NaN. This is deliberate packet-affecting behavior, not a privacy feature; server behavior and acceptance are not guaranteed.
- `HorseFlyAddon.getRepo()` supplies GitHub metadata to Meteor. The target was changed from the original repository to `habue/horsefly`, branch `main`. Meteor may use this metadata to make GitHub requests. The addon does not attach coordinates or messages to it.
- Build-time endpoints include Fabric/Meteor Maven repositories, Maven Central, the Gradle plugin portal, Gradle distribution hosting. These downloads are distinct from runtime addon telemetry.

## Build hardening

The Gradle wrapper JAR was regenerated from the checksum-verified official Gradle 9.5.0 distribution. Its SHA-256 is `497c8c2a7e5031f6aa847f88104aa80a93532ec32ee17bdb8d1d2f67a194a9c7`, matching Gradle's published wrapper checksum. The distribution checksum is recorded in `gradle-wrapper.properties`.

The project now targets Minecraft/Meteor 26.1.2, has explicit loader and Meteor compatibility constraints, and uses a fixed Loom release. Actions separates the read-only build job from the release job requiring `contents: write`; pull request builds have read-only permissions and cannot publish releases. CI publishes only the installable JAR plus its SHA-256.

## Limits

This is a static source review, not a guarantee of absence of malicious behavior. It does not establish the safety of every transitive dependency, Meteor itself, ViaFabricPlus, future upstream changes or previously downloaded third-party binaries. Runtime traffic was not captured in a live Minecraft session. Compatibility compilation and GitHub workflow results must be evaluated separately from these source findings.

Java 25 must be installed explicitly (Actions does this); the automatic Foojay toolchain download plugin was removed to reduce build-time external resolution.
