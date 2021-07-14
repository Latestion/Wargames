package me.latestion.wargames;

import me.latestion.wargames.commad.CommandManager;
import me.latestion.wargames.commad.TabComplete;
import me.latestion.wargames.events.AsyncChat;
import me.latestion.wargames.events.BlockPlace;
import me.latestion.wargames.events.PlayerDeath;
import me.latestion.wargames.events.PlayerJoinLeave;
import me.latestion.wargames.game.Wargame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Wargames extends JavaPlugin {

    private Wargame game;
    private static Wargames plugin;

    public boolean descriptionCache = false;
    public boolean durationCache = false;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        this.saveDefaultConfig();
        getCommand("wargames").setExecutor(new CommandManager(this));
        getCommand("wargames").setTabCompleter(new TabComplete());
        loadEvents();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void loadEvents() {
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new AsyncChat(), this);
        manager.registerEvents(new PlayerJoinLeave(), this);
        manager.registerEvents(new PlayerDeath(), this);
        manager.registerEvents(new BlockPlace(), this);
    }

    public Wargame getGame() {
        return game;
    }

    public void setGame(Wargame game) {
        this.game = game;
    }

    public static Wargames getInstance() {
        return plugin;
    }

    public void startNewGame(Player player) {
        this.game = new Wargame(player);
        game.prepareGame();
    }
}
