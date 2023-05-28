package io.github._64terabytes.digtonether.listeners;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.plugin.Plugin;

public class ReplaceBedrock implements Listener {

    private final Plugin plugin;

    public ReplaceBedrock(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        boolean enableReplace = isReplaceBedrockEnabled();
        if (enableReplace) {
            Chunk chunk = event.getChunk();
            World world = chunk.getWorld();
            Material replacement = replacementToUse();
            if (event.isNewChunk()) {
                if (world.getEnvironment() == World.Environment.NORMAL) {
                    int minY = world.getMinHeight();
                    int maxY = minY + 5;
                    for (int x = 0; x < 16; x++) {
                        for (int y = minY; y < maxY; y++) {
                            for (int z = 0; z < 16; z++) {
                                Block block = chunk.getBlock(x, y, z);
                                if (block.getType() == Material.BEDROCK) {
                                    block.setType(replacement);
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
                                    block.setType(replacement);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isReplaceBedrockEnabled() {
        return plugin.getConfig().getBoolean("replace_bedrock_in_new_chunk");
    }

    private Material replacementToUse() {
        String replacementName = plugin.getConfig().getString("bedrock_replacement");
        return Material.matchMaterial(replacementName);
    }
}
