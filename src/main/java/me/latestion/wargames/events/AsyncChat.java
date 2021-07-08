package me.latestion.wargames.events;

import me.latestion.wargames.Wargames;
import me.latestion.wargames.util.ChatHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncChat implements Listener {

    private final Wargames plugin = Wargames.getInstance();

    @EventHandler
    public void chatEvent(AsyncPlayerChatEvent event) {
        if (plugin.getGame() != null) if (!plugin.getGame().getWarMaster().equals(event.getPlayer())) return;
        if (plugin.descriptionCache) {
            plugin.descriptionCache = false;
            plugin.getGame().setDescription(event.getMessage());
            event.setCancelled(true);
        }
    }

}
