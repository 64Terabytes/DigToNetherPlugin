package io.github._64terabytes.digtonether;

import io.github._64terabytes.digtonether.commands.ManualReplaceBedrockCommand;
import io.github._64terabytes.digtonether.listeners.BlockPortalCreation;
import io.github._64terabytes.digtonether.listeners.CancelNetherRoofSpawn;
import io.github._64terabytes.digtonether.listeners.ReplaceBedrock;
import io.github._64terabytes.digtonether.runnables.DimensionTeleport;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class DigToNether extends JavaPlugin {

    @Override
    public void onEnable() {
        // bStats
        Metrics metrics = new Metrics(this, 18585);

        // Plugin startup logic
        saveDefaultConfig();

        ManualReplaceBedrockCommand command = new ManualReplaceBedrockCommand(this);

        int dimension_tp_tick = getConfig().getInt("dimension_teleport_check");

        getServer().getPluginManager().registerEvents(new ReplaceBedrock(this), this);
        getServer().getPluginManager().registerEvents(new CancelNetherRoofSpawn(), this);
        getServer().getPluginManager().registerEvents(new BlockPortalCreation(this), this);

        new DimensionTeleport(this).runTaskTimer(this, 0, dimension_tp_tick);

        getCommand("replacebedrockinchunk").setExecutor(command);

        getServer().getConsoleSender().sendMessage("[DigToNether] Plugin has started");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic


        getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[DigToNether] Plugin has stopped");
    }
}
