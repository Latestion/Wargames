package me.latestion.wargames.events;

import me.latestion.wargames.Wargames;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncChat implements Listener {

    private final Wargames plugin = Wargames.getInstance();

    @EventHandler
    public void chatEvent(AsyncPlayerChatEvent event) {
        if (plugin.getGame() == null) return;
        if (!plugin.getGame().getWarMaster().equals(event.getPlayer())) return;
        if (plugin.descriptionCache) {
            plugin.descriptionCache = false;
            plugin.getGame().setDescription(event.getMessage());
            event.setCancelled(true);
            return;
        }
        if (plugin.durationCache) {
            try {
                int time = Integer.parseInt(event.getMessage());
                if (time < 2 || time > 90) {
                    event.getPlayer().sendMessage(ChatColor.RED + "Input should be between 2 and 90");
                    return;
                }
                plugin.durationCache = false;
                plugin.getGame().setDuration(time);
                event.setCancelled(true);
            } catch (Exception e) {
                event.getPlayer().sendMessage(ChatColor.RED + "Input in not a integer.");
            }
        }
        if (plugin.graceCache) {
            try {
                int time = Integer.parseInt(event.getMessage());
                if (time == 0) {
                    event.getPlayer().sendMessage(ChatColor.RED + "Grace period cannot be 0.");
                    return;
                }
                plugin.graceCache = false;
                plugin.getGame().setGraceDuration(time);
                event.setCancelled(true);
            } catch (Exception e) {
                event.getPlayer().sendMessage(ChatColor.RED + "Input in not a integer.");
            }
        }
    }
}
