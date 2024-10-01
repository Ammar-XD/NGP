package me.infnox.noGlitchPearls.listeners;

import me.infnox.noGlitchPearls.NoGlitchPearls;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;

public class PearlListener implements Listener {

    private final NoGlitchPearls plugin;
    private MiniMessage miniMessage;

    public PearlListener(NoGlitchPearls plugin, MiniMessage miniMessage) {
        this.plugin = plugin;
        this.miniMessage = MiniMessage.miniMessage();
    }


    @EventHandler
    public void onPlayerPearl(PlayerTeleportEvent event) {

        if (event.getCause() != TeleportCause.ENDER_PEARL) {
            return;
        }

        Player player = event.getPlayer();
        Location targetLocation = event.getTo();

        if (isGlitch(targetLocation)) {
            event.setCancelled(true);
            givePlayerPearl(player);
            player.teleport(event.getFrom());
            player.sendMessage(miniMessage.deserialize(plugin.getConfiguration().getString("glitch-message")));
        }
    }


    private boolean isGlitch(Location location) {

        Material blockType = location.getBlock().getType();

        if (isGlitchableBlock(blockType)) {
            return true;
        }

        if (location.getBlock().getType().isSolid()) {
            return true;
        }

        return false;
    }

    private boolean isGlitchableBlock(Material blockType) {
        return blockType == Material.LADDER ||
                blockType == Material.VINE ||
                blockType == Material.IRON_BARS ||
                blockType == Material.OAK_FENCE ||
                blockType == Material.ACACIA_FENCE ||
                blockType == Material.BAMBOO_FENCE ||
                blockType == Material.BIRCH_FENCE ||
                blockType == Material.CHERRY_FENCE ||
                blockType == Material.CRIMSON_FENCE ||
                blockType == Material.JUNGLE_FENCE ||
                blockType == Material.MANGROVE_FENCE ||
                blockType == Material.SPRUCE_FENCE ||
                blockType == Material.DARK_OAK_FENCE ||
                blockType == Material.NETHER_BRICK_FENCE;
    }

    private void givePlayerPearl(Player player) {
        if (player.getInventory().firstEmpty() != -1) {
            player.getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 1));
        } else {
            player.getWorld().dropItem(player.getLocation(), new ItemStack(Material.ENDER_PEARL, 1));
        }
    }

}
