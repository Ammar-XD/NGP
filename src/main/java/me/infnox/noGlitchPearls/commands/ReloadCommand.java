package me.infnox.noGlitchPearls.commands;

import me.infnox.noGlitchPearls.NoGlitchPearls;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class ReloadCommand implements TabExecutor {

    private final NoGlitchPearls plugin;

    public ReloadCommand(NoGlitchPearls plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (isReloadCommand(args) && sender.hasPermission("ngp.reload")) {
            reloadConfig(sender);
            return true;
        }
        return false;
    }

    private boolean isReloadCommand(String[] args) {
        return args.length == 1 && args[0].equalsIgnoreCase("reload");
    }

    private void reloadConfig(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            try {
                plugin.getConfiguration().reload();
                player.sendMessage(Component.text("Config has been reloaded", NamedTextColor.GREEN));
            } catch (IOException e) {
                player.sendMessage(Component.text("An error occurred while reloading the config.", NamedTextColor.RED));
                plugin.getLogger().log(Level.SEVERE, "Error reloading configuration", e);
            }
        } else {
            sender.sendMessage(Component.text("Only players can execute this command.", NamedTextColor.RED));
        }
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            StringUtil.copyPartialMatches(args[0], Collections.singletonList("reload"), completions);
            Collections.sort(completions);
            return completions;
        }
        return Collections.emptyList();
    }
}
