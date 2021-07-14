package me.latestion.wargames.events;

import me.latestion.wargames.Wargames;
import me.latestion.wargames.game.GameState;
import me.latestion.wargames.game.Wargame;
import me.latestion.wargames.util.MessageHandler;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace implements Listener {

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        Wargame game = Wargames.getInstance().getGame();
        if (game == null) return;
        if (game.getState() == GameState.OFF) return;
        if (!game.isBringBed()) return;
        if (!event.getBlock().getType().toString().toLowerCase().contains("bed")) return;
        if (!game.playerList.containsKey(event.getPlayer().getUniqueId())) return;
        Location loc = event.getBlock().getLocation();
        event.getPlayer().spigot().sendMessage(new MessageHandler(ChatColor.GOLD + "[Click here to set that bed as your spawn location]")
                .setClickEvent("/wargames bed " + event.getPlayer().getName() + " " + loc.getWorld().getName() + " " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ())
                .getComponent());
    }

}
