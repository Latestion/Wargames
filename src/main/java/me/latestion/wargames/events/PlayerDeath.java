package me.latestion.wargames.events;

import me.latestion.wargames.Wargames;
import me.latestion.wargames.game.GameState;
import me.latestion.wargames.game.Wargame;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Objects;

public class PlayerDeath implements Listener {

    @EventHandler
    public void playerDeath(EntityDamageEvent event) {
        Wargame game = Wargames.getInstance().getGame();
        if (game == null) return;
        if (game.getState() != GameState.ON) return;
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if (!game.playerList.containsKey(player.getUniqueId())) return;
        if (game.isGraceOn()) {
            event.setCancelled(true);
            return;
        }
        if (player.getHealth() > event.getFinalDamage()) return;
        event.setCancelled(true);
        player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
        if (game.isBringBed())
            player.teleport(Objects.requireNonNull(player.getBedSpawnLocation()));
        else
            player.teleport(game.teamSpawnLocation.get(game.playerList.get(player.getUniqueId()).getTeam()));
    }

}
