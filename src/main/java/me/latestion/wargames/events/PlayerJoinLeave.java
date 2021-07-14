package me.latestion.wargames.events;

import me.latestion.wargames.Wargames;
import me.latestion.wargames.util.ChatHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinLeave implements Listener {

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        if (Wargames.getInstance().getGame() == null) return;
        if (Wargames.getInstance().getGame().invitationPeriod) {
            if (Wargames.getInstance().getGame().inviteAll)
                new ChatHandler().sendInvitation(event.getPlayer());
            else if (Wargames.getInstance().getGame().playerList.containsKey(event.getPlayer().getUniqueId()))
                new ChatHandler().sendInvitation(event.getPlayer());
        }
    }
}
