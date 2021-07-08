package me.latestion.wargames.game;

import me.latestion.wargames.util.ChatHandler;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Wargame {

    private GameState state;
    private WarSize size = WarSize.FFA;
    private GameMode mode = GameMode.ADVENTURE;
    private Location loc;

    public boolean allowTeamSelection = false;
    private int duration = 2; // In Minutes
    private String description;

    private final Player warMaster;

    public Map<UUID, WarPlayer> playerList = new HashMap<>();

    public Wargame(Player player) {
        this.warMaster = player;
        description = "";
        state = GameState.PREPARE;
    }

    public void prepareGame() {
        new ChatHandler().sendProposal(warMaster);
    }

    public void sendInvite() {
        new ChatHandler().sendInvitation();
    }

    public void setMode(GameMode mode) {
        this.mode = mode;
        prepareGame();
    }

    public GameMode getMode() {
        return mode;
    }

    public void setSize(WarSize size) {
        this.size = size;
        prepareGame();
    }

    public WarSize getSize() {
        return size;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
        prepareGame();
    }

    public Player getWarMaster() {
        return warMaster;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String s) {
        this.description = s;
        prepareGame();
    }

    public Location getLocation() {
        return loc;
    }

    public void setLocation(Location loc) {
        this.loc = loc;
        prepareGame();
    }

}
