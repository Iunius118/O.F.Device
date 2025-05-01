# O.F.Device (Ore Farming Device)

O.F.Device is a Minecraft mod to add devices for farming ores.

材料と燃料を消費して一定確率で鉱石を生成できる装置を追加するmod。

## Download

- [From Modrinth](https://modrinth.com/mod/o-f-device)
- [From CurseForge](https://www.curseforge.com/minecraft/mc-mods/o-f-device)

## Requirement

- NeoForge

## Description

Version 9.0.0

### OF Device (ＯＦ装置)

OF Devices can be mined using any pickaxe. The appearance of the OF Device block can be changed using a stick.

ＯＦ装置はツルハシで破壊可能。ＯＦ装置ブロックに棒を使用することで外観を変更可能。

#### Recipe

![ ](./docs/media/recipe_device_0_v010.png "Crafting recipe: OF Device")

4 Stones + Furnace + Lever + 2 Iron Ingots + Stone Pickaxe

#### GUI

![The upper right slot for materials, the left slot for fuel, and the bottom right slot for products](./docs/media/gui_device_0_v010.png "GUI: OF Device")

1. Material slot (材料). Only Cobblestone, Cobbled Deepslate, Netherrack, or OF C Feeder (丸石・深層岩の丸石・ネザーラック・ＯＦ丙供給装置のみ)
2. Fuel slot (燃料)
3. Product slot (生成物)

![ ](./docs/media/processing_line_v040.png "Hopper automation")

If the product slot fills up during operation, the products are ejected out of the block. Hopper can be used to push materials and fuel into the device, and to pull products from the device.

装置の上面・側面・下面にホッパーを接続してアイテムの搬入出が可能（それぞれ材料搬入・燃料搬入・生成物搬出）。特に生成物スロットのアイテムは自動的にブロック外へ排出されることがあるため、下面へのホッパー設置を強く推奨。

#### Products

|               |       Cobblestone        |                 Cobbled Deepslate                  |    Netherrack     |
|:-------------:|:------------------------:|:--------------------------------------------------:|:-----------------:|
|   **Major**   |          Stone           |                     Deepslate                      |   Nether Brick    |
| **Uncommon**  |         Coal Ore         |                                                    |                   |
|   **Rare**    | Iron Ore, <br>Copper Ore |                 Deepslate Iron Ore                 | Nether Quartz Ore |
| **Very Rare** |                          |                Deepslate Copper Ore                |  Nether Gold Ore  |
| **Legendary** |     Lapis Lazuli Ore     | Deepslate Lapis Lazuli Ore, <br>Deepslate Coal Ore |                   |

#### Farming Efficiency

Appearance rate of ores increases with number of players, villagers, etc. (up to 3) around the device (5x3x5).

ＯＦ装置の周囲（5×3×5）にプレイヤーや村人など（最大3人まで）がいると、鉱石の出現率が上昇する。

### OF Device Mod 1 (ＯＦ装置改)

Modified version of OF Device. Processing speed is faster than OF Device.

ＯＦ装置の改造版。ＯＦ装置よりも処理速度が速い。

#### Recipe

![ ](./docs/media/recipe_device_1_v020.png "Crafting recipe: OF Device Mod 1")

OF Device + Iron Pickaxe

#### Products

|               |          Cobblestone           |                            Cobbled Deepslate                            |    Netherrack     |
|:-------------:|:------------------------------:|:-----------------------------------------------------------------------:|:-----------------:|
|   **Major**   |             Stone              |                                Deepslate                                |   Nether Brick    |
| **Uncommon**  |            Coal Ore            |                                                                         |                   |
|   **Rare**    |    Iron Ore, <br>Copper Ore    |             Deepslate Redstone Ore, <br>Deepslate Iron Ore              | Nether Quartz Ore |
| **Very Rare** |          Redstone Ore          | Deepslate Diamond Ore, <br>Deepslate Copper Ore, <br>Deepslate Gold Ore |  Nether Gold Ore  |
| **Legendary** | Lapis Lazuli Ore, <br>Gold Ore |           Deepslate Lapis Lazuli Ore, <br>Deepslate Coal Ore            |                   |
|  **Mythic**   |          Diamond Ore           |                                                                         |                   |

### OF Device Mod 2 (ＯＦ装置改二)

Modified version of OF Device Mod 1. Processing speed is faster than OF Device Mod 1.

ＯＦ装置改の改造版。ＯＦ装置改よりも処理速度が速い。

#### Recipe

![ ](./docs/media/recipe_device_2_v020.png "Crafting recipe: OF Device Mod 2")

OF Device Mod 1 + Diamond Pickaxe

#### Products

|               |                      Cobblestone                      |                                      Cobbled Deepslate                                       |    Netherrack     |
|:-------------:|:-----------------------------------------------------:|:--------------------------------------------------------------------------------------------:|:-----------------:|
|   **Major**   |                         Stone                         |                                          Deepslate                                           |   Nether Brick    |
| **Uncommon**  |               Coal Ore, <br>Copper Ore                |                                                                                              |                   |
|   **Rare**    |                Iron Ore, <br>Gold Ore                 |           Deepslate Redstone Ore, <br>Deepslate Copper Ore, <br>Deepslate Iron Ore           | Nether Quartz Ore |
| **Very Rare** |                     Redstone Ore                      |                        Deepslate Diamond Ore, <br>Deepslate Gold Ore                         |  Nether Gold Ore  |
| **Legendary** | Lapis Lazuli Ore, <br>Amethyst Shard, <br>Emerald Ore |            Deepslate Lapis Lazuli Ore, <br>Amethyst Shard, <br>Deepslate Coal Ore            |                   |
|  **Mythic**   |                      Diamond Ore                      |                                    Deepslate Emerald Ore                                     |  Ancient Debris   |

### OF C Feeder I (ＯＦ丙供給装置一型)

![Place this item in material slot of OF device](./docs/media/gui_cobblestone_feeder_v030.png "GUI: Using OF C Feeder")

OF C Feeder I provides an infinite supply of Cobblestones to OF Devices. However, the fuel consumption is doubled.

ＯＦ装置の材料スロットに入れると装置へ無限に丸石を供給する。ただし、燃料の消費速度が2倍になる。

If the altitude of the device is 0 or below, this item will supply the device with Cobbled Deepslate.

ＯＦ装置の高度が0以下の場合は、深層岩の丸石を供給する。

#### Recipe

![ ](./docs/media/recipe_cobblestone_feeder_v030.png "Crafting recipe: OF C Feeder I")

Lava Bucket + Redstone Repeater + Piston + Stone Pickaxe + Water Bucket

#### Recycling Recipe

![ ](./docs/media/feeder_to_lava_bucket_v040.png "Crafting recipe: Lava Bucket")

OF C Feeder I + Bucket -> Lava Bucket

### OF C Feeder II (ＯＦ丙供給装置二型)

OF C Feeder II is the improved version of the Feeder I and does not increase fuel consumption.

供給装置一型の改良版。燃料の消費速度が増加しない。

#### Recipe

![ ](./docs/media/recipe_cobblestone_feeder_2_v100.png "Crafting recipe: OF C Feeder II")

OF C Feeder I + Diamond Pickaxe

#### Recycling Recipes

![ ](./docs/media/feeder_2_to_lava_bucket_v100.png "Crafting recipe: Lava Bucket")

OF C Feeder II + Bucket -> Lava Bucket

![ ](./docs/media/feeder_2_to_diamond_pickaxe_v100.png "Crafting recipe: Diamond Pickaxe")

OF C Feeder II + Stick -> Diamond Pickaxe

### OF C Device (ＯＦ丙装置)

OF C Device is a cobblestone generator.

丸石を生成する装置。

#### Recipe

![ ](./docs/media/recipe_cobblestone_device_0_v110.png "Crafting recipe: OF C Device")

7 Stones + OF C Feeder II + Redstone Torch

#### GUI

![ ](./docs/media/gui_cobblestone_device_0_v110.png "GUI: OF C Device")

Hopper can be used to pull cobblestones from underside of the device.

装置の下面にホッパーを接続して丸石の搬出が可能。

#### Recycling Recipes

![ ](./docs/media/recipe_c_device_to_feeder_2_v110.png "Crafting recipe: OF C Feeder II")

OF C Device -> OF C Feeder II

### Config

The default config file is saved to the following path:

- `<instance_path>/config/orefarmingdevice-server.toml`

Restart the game when you change the config. In multiplayer mode, server-side config will be used.

コンフィグを変更したときはゲームの再起動が必要。マルチプレイの場合はサーバー側の設定が使用される。

- isCobblestoneFeederAvailable (boolean, default value is true)
  - Whether OF C Feeders are available for devices
  - ＯＦ丙供給装置がＯＦ装置で材料として使えるか（true→使える、false→使えない）
- enableFarmingEfficiency (boolean, default value is true)
  - Whether mobs around device increase farming efficiency of device
  - ＯＦ装置の周囲にモブがいるとき、鉱石の出現率が上昇するか（true→する、false→しない）
- accelerateProcessingSpeedByMod (boolean, default value is true)
  - Whether to accelerate processing speed of device by modification
  - ＯＦ装置の改造によって処理速度が上昇するか（true→する、false→しない）
- increaseFuelConsumptionByMod (boolean, default value is true)
  - Whether to increase fuel consumption of device by modification
  - ＯＦ装置の改造によって燃料消費量が増加するか（true→する、false→しない）

## License

This project is licensed under the MIT license.
