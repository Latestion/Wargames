package me.latestion.wargames.game;

import me.latestion.wargames.Wargames;
import me.latestion.wargames.game.enums.WarTeams;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.UUID;

public class WarPlayer {

    private final UUID player;
    private Location startLoc;
    private Team oldTeam;
    private WarTeams team;

    private boolean acceptGame;

    public WarPlayer(UUID player) {
        this.player = player;
    }

    public WarPlayer(UUID player, boolean bol) {
        this.player = player;
        setAcceptGame(bol);
    }

    public UUID getUUID() {
        return player;
    }

    public WarTeams getTeam() {
        return team;
    }

    public WarPlayer setTeam(WarTeams team) {
        this.team = team;
        return this;
    }

    public Team getOldTeam() {
        return oldTeam;
    }

    public void setOldTeam(Team team) {
        this.oldTeam = team;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(player);
    }

    public boolean isAcceptGame() {
        return acceptGame;
    }

    public void setAcceptGame(boolean acceptGame) {
        this.acceptGame = acceptGame;
        if (acceptGame) {
            Wargames.getInstance().getGame().setTempAccept(Wargames.getInstance().getGame().getTempAccept() + 1);
        } else {
            Wargames.getInstance().getGame().setTempAccept(Wargames.getInstance().getGame().getTempAccept() - 1);
        }
    }
}
