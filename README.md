# HorseFly for Meteor 26.1.2

Client-side horse movement addon for **Minecraft Java 26.1.2** and **Meteor Client 26.1.2**. Requires Java 25 and Fabric Loader 0.19.2 or newer. It is not a Minecraft 1.21.4 build.

## Install

Download `horsefly-1.0.2.jar` from this fork's [development release](https://github.com/habue/horsefly/releases/tag/snapshot) or a successful [Actions build](https://github.com/habue/horsefly/actions), then place it beside Meteor in your `mods` folder. Do not install a sources JAR.

The module is **Movement → Horse Fly**. WASD controls horizontal movement, Jump ascends and Sprint descends (using your configured key bindings).

## Settings

- `speed`: horizontal blocks per tick; default 1.
- `vertical-speed`: vertical blocks per tick; default 0.5.
- `glide`: downward motion while idle; default 0.
- `nan-input`: optional ViaFabricPlus steering override; default on.
- `nan-delay`: ordinary input ticks between overrides; default 0.

ViaFabricPlus is optional. Its steering override only applies when translating to a server protocol at or below Minecraft 1.21.1; ordinary horse movement does not require it. Server acceptance of movement is not guaranteed.

## Build

```sh
./gradlew build
```

Installable output: `build/libs/horsefly-1.0.2.jar`.

The build uses Minecraft 26.1.2, Meteor's 26.1.2 snapshot line, Java 25 and a fixed Loom release. Pushes to `main` build an artifact and update the development release; pull requests build with read-only repository permissions. Fork owners may need to enable Actions on GitHub before the first run.

See [SECURITY_REVIEW.md](SECURITY_REVIEW.md) for the source review and its limits.
