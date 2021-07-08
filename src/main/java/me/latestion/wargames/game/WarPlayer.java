package me.latestion.wargames.game;

import org.bukkit.Location;

import java.util.UUID;

public class WarPlayer {

    private final UUID player;
    private Location startLoc;

    public WarPlayer(UUID player) {
        this.player = player;
    }

    public UUID getPlayer() {
        return player;
    }
}
