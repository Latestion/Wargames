package me.latestion.wargames.game;

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

    public boolean acceptGame;

    public WarPlayer(UUID player) {
        this.player = player;
    }

    public WarPlayer(UUID player, boolean bol) {
        this.player = player;
        this.acceptGame = bol;
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
}
