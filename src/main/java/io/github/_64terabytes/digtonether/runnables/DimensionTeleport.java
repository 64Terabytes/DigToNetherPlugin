package io.github._64terabytes.digtonether.runnables;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import static java.lang.Math.floor;

public class DimensionTeleport extends BukkitRunnable {
    private final Plugin plugin;

    public DimensionTeleport(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (World world : plugin.getServer().getWorlds()) {
            if (world.getEnvironment() == World.Environment.NORMAL) {
                for (LivingEntity entity : world.getLivingEntities()) {
                    boolean enableNetherCoords = plugin.getConfig().getBoolean("use_nether_coordinates");
                    if (entity.getLocation().getY() <= entity.getWorld().getMinHeight()) {
                        double netherX, netherZ;
                        if (enableNetherCoords) {
                            netherX = floor(entity.getLocation().getX() / 8);
                            netherZ = floor(entity.getLocation().getZ() / 8);
                        } else {
                            netherX = floor(entity.getLocation().getX());
                            netherZ = floor(entity.getLocation().getZ());
                        }

                        for (World tpworld : plugin.getServer().getWorlds()) {
                            if (tpworld.getEnvironment() == World.Environment.NETHER) {
                                Location destination = new Location(tpworld, netherX + 0.5, 125, netherZ + 0.5);
                                tpworld.getBlockAt(new Location(tpworld, netherX, 125, netherZ)).setType(Material.AIR);
                                tpworld.getBlockAt(new Location(tpworld, netherX, 126, netherZ)).setType(Material.AIR);
                                tpworld.getBlockAt(new Location(tpworld, netherX, 127, netherZ)).setType(Material.AIR);
                                entity.teleport(destination);
                            }
                        }
                    }
                }
            } else if (world.getEnvironment() == World.Environment.NETHER) {
                for (LivingEntity entity : world.getLivingEntities()) {
                    if (entity.getLocation().getY() + 1 >= 128) {
                        double overworldX, overworldZ;
                        boolean enableNetherCoords = plugin.getConfig().getBoolean("use_nether_coordinates");
                        if (enableNetherCoords) {
                            overworldX = floor(entity.getLocation().getX() / 8);
                            overworldZ = floor(entity.getLocation().getZ() / 8);
                        } else {
                            overworldX = floor(entity.getLocation().getX());
                            overworldZ = floor(entity.getLocation().getZ());
                        }

                        for (World tpworld : plugin.getServer().getWorlds()) {
                            if (tpworld.getEnvironment() == World.Environment.NORMAL) {
                                Location destination = new Location(tpworld, overworldX + 0.5, tpworld.getMinHeight() + 1, overworldZ + 0.5);
                                boolean enableReplaceAir = plugin.getConfig().getBoolean("replace_air_on_overworld_return");
                                String airReplacement = plugin.getConfig().getString("air_replacement");
                                if (enableReplaceAir) {
                                    if (tpworld.getBlockAt(new Location(tpworld, overworldX, tpworld.getMinHeight(), overworldZ)).getType() == Material.AIR) {
                                        tpworld.getBlockAt(new Location(tpworld, overworldX, tpworld.getMinHeight(), overworldZ)).setType(Material.valueOf(airReplacement));
                                    }
                                }

                                tpworld.getBlockAt(new Location(tpworld, overworldX, tpworld.getMinHeight() + 1, overworldZ)).setType(Material.AIR);
                                tpworld.getBlockAt(new Location(tpworld, overworldX, tpworld.getMinHeight() + 2, overworldZ)).setType(Material.AIR);
                                entity.teleport(destination);
                            }
                        }
                    }
                }
            }
        }
    }
}
