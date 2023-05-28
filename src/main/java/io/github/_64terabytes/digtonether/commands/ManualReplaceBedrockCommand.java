package io.github._64terabytes.digtonether.commands;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ManualReplaceBedrockCommand implements CommandExecutor {

    private final Plugin plugin;
    public ManualReplaceBedrockCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("replacebedrockinchunk")) {
            if (sender.hasPermission("dtn.admin")) {
                try {
                    Chunk chunk = player.getLocation().getChunk();
                    World world = player.getWorld();
                    String replacement = plugin.getConfig().getString("bedrock_replacement");

                    if (world.getEnvironment() == World.Environment.NORMAL) {
                        int minY = world.getMinHeight();
                        int maxY = minY + 5;

                        for (int x = 0; x < 16; x++) {
                            for (int y = minY; y < maxY; y++) {
                                for (int z = 0; z < 16; z++) {
                                    Block block = chunk.getBlock(x, y, z);
                                    if (block.getType() == Material.BEDROCK) {
                                        block.setType(Material.valueOf(replacement));
                                    }
                                }
                            }
                        }
                    } else if (world.getEnvironment() == World.Environment.NETHER) {
                        int maxY = 128;
                        int minY = maxY - 5;

                        for (int x = 0; x < 16; x++) {
                            for (int y = minY; y < maxY; y++) {
                                for (int z = 0; z < 16; z++) {
                                    Block block = chunk.getBlock(x, y, z);

                                    if (block.getType() == Material.BEDROCK) {
                                        block.setType(Material.valueOf(replacement));
                                    }
                                }
                            }
                        }
                    }
                }
                catch (IllegalArgumentException ex) {
                    player.sendMessage(ChatColor.RED + "UNKNOWN ERROR");
                }
            }
            else {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            }
        }
        return true;
    }
}
