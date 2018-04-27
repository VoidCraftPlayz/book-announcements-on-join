package com.voidcraftplays.planetarypvp.listeners;

import com.voidcraftplays.planetarypvp.Main;
import com.voidcraftplays.planetarypvp.util.BookUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftMetaBook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class PlayerJoinListener implements Listener {

    private Main main = Main.getPlugin(Main.class);

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {

        Player player = e.getPlayer();

        if (!main.disabledAnnouncements.contains(player.getUniqueId().toString())) {

            ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
            BookMeta meta = (BookMeta) book.getItemMeta();

            List<IChatBaseComponent> pages = new ArrayList<>();

            try {

                pages = (List)CraftMetaBook.class.getDeclaredField("pages").get(meta);

            } catch (ReflectiveOperationException ex) {

                ex.printStackTrace();


            }

            for (int i = 1; i < main.getConfig().getInt("book.pages") + 1; i++) {

                TextComponent t = new TextComponent();

                for (int j = 1; j < main.getConfig().getInt("book.page" + i + ".components") + 1; j++) {

                    String path = "book.page" + i + ".component" + j;
                    TextComponent component = new TextComponent(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', main.getConfig().getString(path + ".text")));

                    if (main.getConfig().contains(path + ".clickevent")) {

                        component.setClickEvent(new ClickEvent(ClickEvent.Action.valueOf(main.getConfig().getString(path + ".clickevent.action")), main.getConfig().getString(path + ".clickevent.string")));

                    }

                    if (main.getConfig().contains(path + ".hoverevent")) {

                        component.setHoverEvent(new HoverEvent(HoverEvent.Action.valueOf(main.getConfig().getString(path + ".hoverevent.action")), new ComponentBuilder(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', main.getConfig().getString(path + ".hoverevent.string"))).create()));

                    }

                    t.addExtra(component);

                }

                IChatBaseComponent page = IChatBaseComponent.ChatSerializer.a(ComponentSerializer.toString(t));
                pages.add(page);

            }

            meta.setTitle("PlanetaryPvP");
            meta.setAuthor("PlanetaryPvP");
            book.setItemMeta(meta);

            new BukkitRunnable() {

                public void run() {

                    BookUtil.openBook(book, player);

                }

            }.runTaskLaterAsynchronously(main, 40L);

        }

    }

}
