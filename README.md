# Better with Magnets

Craftable magnets for [Better than Adventure](https://betterthanadventure.net/). 

Requires [Melib](https://github.com/MelonMojito/Melib).

Hold a magnet or wear it on your head and nearby dropped items are pulled toward you. The magnet's item texture lights up while it is active.

## Installing

Drop the jar in `mods/`. It works in **singleplayer**, on a **dedicated server**, or both:

- **Client** - full support: the item textures, the activated-magnet model, and an options page under Options with a Magnets toggle.
- **Dedicated server** - magnets are always on. Clients do not strictly need the mod to be pulled toward items, but without it, they will see the plain fireball texture instead of the magnet.

## Crafting

```
R . L      R = Redstone Dust
S . S      L = Blue Dye
S S S      S = Steel Ingot
```

Produces a Magnet (a fireball charge with metadata 1).

## Options

`Options > Better with Magnets > Magnets` toggles magnets on the client (this is what singleplayer uses). Stored in the normal game options file as `betterwithmagnets.magnets.enabled`.
