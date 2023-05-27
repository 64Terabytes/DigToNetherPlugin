package io.github._64terabytes.digtonether.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.plugin.Plugin;

import static org.bukkit.Bukkit.getServer;

public class BlockPortalCreation implements Listener {

    private final Plugin plugin;
    public BlockPortalCreation(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPortalCreate(PortalCreateEvent event) {
        boolean disablePortals = isPortalCreationBlocked();
        if (disablePortals) {
            event.setCancelled(true);
        }
    }

    private boolean isPortalCreationBlocked() {
        return plugin.getConfig().getBoolean("disable_nether_portal_creation");
    }
}
