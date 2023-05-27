package io.github._64terabytes.digtonether.listeners;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CancelNetherRoofSpawn implements Listener {

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        if (event.getLocation().getWorld().getEnvironment() == World.Environment.NETHER) {
            if (event.getLocation().getY() >= 128) {
                event.setCancelled(true);
            }
        }
    }
}
