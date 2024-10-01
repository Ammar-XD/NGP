package me.infnox.noGlitchPearls;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.infnox.noGlitchPearls.commands.ReloadCommand;
import me.infnox.noGlitchPearls.listeners.EndermiteListener;
import me.infnox.noGlitchPearls.listeners.PearlListener;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class NoGlitchPearls extends JavaPlugin {


    private YamlDocument configuration;
    private MiniMessage miniMessage;

    @Override
    public void onEnable() {

        try {
            configuration = YamlDocument.create(new File(getDataFolder(), "config.yml"), Objects.requireNonNull(getResource("config.yml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        getServer().getPluginManager().registerEvents(new PearlListener(this, miniMessage), this);
        getServer().getPluginManager().registerEvents(new EndermiteListener(this), this);
        getLogger().info("Successfully registered Listeners");

        Objects.requireNonNull(getCommand("ngp")).setExecutor(new ReloadCommand(this));
        Objects.requireNonNull(getCommand("ngp")).setTabCompleter(new ReloadCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public YamlDocument getConfiguration() {
        return configuration;
    }
}
