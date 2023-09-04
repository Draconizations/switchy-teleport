<!--suppress HtmlDeprecatedTag, XmlDeprecatedElement -->
<center>
An add-on module for <a href="https://modrinth.com/mod/switchy">Switchy</a> that provides some location-based utilities.<br/>
Requires <a href="https://modrinth.com/mod/connector">Connector</a> and <a href="https://modrinth.com/mod/forgified-fabric-api">FFAPI</a> on forge.<br/>
</center>

---

**Modules**:

- `last_location`: teleport to a preset's last location whenever you switch to that preset
- `spawn_point`: separate spawn points per preset

## Usage
Both modules are disabled by default, run `/switchy module enable` to enable them.

### Last Location
When first switching to a preset after enabling this module, nothing will happen. When you switch back to that preset later, you will be teleported to where you left off. Disabling the module removes all saved locations.

### Spawn Point
When switching to a preset for the first time after enabling this module, that preset will keep the spawnpoint of the *previous* preset that you just switched from. You can re-set the spawnpoint for the current preset by using a bed or respawn anchor.
