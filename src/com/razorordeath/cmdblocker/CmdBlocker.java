package com.razorordeath.cmdblocker;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;

public class CmdBlocker extends JavaPlugin implements Listener, CommandExecutor {

    private CmdBlocker plugin;
    private String no_permission = null;
    private String allow_permission = null;
    private HashSet<String> blocked_commands = null;

    @Override
    public void onEnable() {
    plugin = this;
    reloadConfig();
    Bukkit.getServer().getPluginManager().registerEvents(this, this);
}

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission(this.allow_permission)) {
            return;
        }

        String attemptedCommand = event.getMessage().toLowerCase();
        for (String blockedCommand : this.blocked_commands) {
            if (attemptedCommand.contains(blockedCommand.toLowerCase())) {
                event.setCancelled(true);
                player.sendMessage(this.no_permission);
                break;
            }
        }
    }
}