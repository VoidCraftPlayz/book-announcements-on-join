package com.voidcraftplays.planetarypvp.commands;

import com.voidcraftplays.planetarypvp.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ToggleAnnouncementsCommand implements CommandExecutor {

    private Main main = Main.getPlugin(Main.class);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            if (main.disabledAnnouncements.contains(player.getUniqueId().toString())) {

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a(!) &7You have &asuccessfully enabled &7your &aannouncements&7!"));
                main.disabledAnnouncements.remove(player.getUniqueId().toString());
                List<String> disabled = main.getConfig().getStringList("toggled-off");
                disabled.remove(player.getUniqueId().toString());
                main.getConfig().set("toggled-off", disabled);
                main.saveConfig();

            } else {

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a(!) &7You have &asuccessfully disabled &7your &aannouncements&7!"));
                main.disabledAnnouncements.add(player.getUniqueId().toString());
                List<String> disabled = main.getConfig().getStringList("toggled-off");
                disabled.add(player.getUniqueId().toString());
                main.getConfig().set("toggled-off", disabled);
                main.saveConfig();

            }

        }

        return false;
    }

}
