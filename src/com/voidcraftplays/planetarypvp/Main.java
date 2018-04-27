package com.voidcraftplays.planetarypvp;

import com.voidcraftplays.planetarypvp.commands.ToggleAnnouncementsCommand;
import com.voidcraftplays.planetarypvp.listeners.PlayerJoinListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    public List<String> disabledAnnouncements = new ArrayList<>();

    @Override
    public void onEnable() {

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getCommand("toggleannouncements").setExecutor(new ToggleAnnouncementsCommand());

        disabledAnnouncements = getConfig().getStringList("toggled-off");

    }

}
