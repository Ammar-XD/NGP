package me.infnox.noGlitchPearls.listeners;

import me.infnox.noGlitchPearls.NoGlitchPearls;
import org.bukkit.entity.Endermite;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;



public class EndermiteListener implements Listener {

    private final NoGlitchPearls plugin;

    public EndermiteListener(NoGlitchPearls plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEndermiteSpawn(CreatureSpawnEvent event) {
        if (event.getEntity() instanceof Endermite) {
            if (event.getSpawnReason() == SpawnReason.ENDER_PEARL) {
                if (!isEndermiteSpawningAllowed()) {
                    event.setCancelled(true);
                }
            }
        }
    }

    public boolean isEndermiteSpawningAllowed() {
        return plugin.getConfiguration().getBoolean("allow-endermite-spawn", true);
    }
}
