package me.latestion.wargames.game.enums;

import me.latestion.wargames.Wargames;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Team;

import java.util.Objects;

public enum WarTeams {

    RED(Wargames.getInstance().getConfig().getString("Teams.RED"), ChatColor.RED),
    BLUE(Wargames.getInstance().getConfig().getString("Teams.BLUE"), ChatColor.BLUE),
    GREEN(Wargames.getInstance().getConfig().getString("Teams.GREEN"), ChatColor.GREEN),
    YELLOW(Wargames.getInstance().getConfig().getString("Teams.YELLOW"), ChatColor.YELLOW),
    FFA(Wargames.getInstance().getConfig().getString("Teams.FFA"), ChatColor.DARK_RED);

    private final Team team;
    private final ChatColor color;

    WarTeams(String team, ChatColor color) {
        this.team = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard().getTeam(team);
        this.color = color;
    }

    public Team getTeam() {
        return team;
    }

    public ChatColor getColor() {
        return color;
    }


}
