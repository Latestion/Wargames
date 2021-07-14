package me.latestion.wargames.game;

import me.latestion.wargames.Wargames;
import me.latestion.wargames.game.enums.TeamSorting;
import me.latestion.wargames.game.enums.WarSize;
import me.latestion.wargames.game.enums.WarTeams;
import me.latestion.wargames.util.ChatHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class Wargame {

    private GameState state;
    private WarSize size = WarSize.FFA;
    private GameMode mode = GameMode.ADVENTURE;
    private TeamSorting sorting = TeamSorting.Random;
    private Location loc;

    private int duration = 2; // In Minutes
    private String description;
    private boolean teleportPlayers;
    private boolean bringBed = false;
    private boolean graceOn = false;
    private int graceDuration = 1;

    private final Player warMaster;

    public Map<UUID, WarPlayer> playerList = new HashMap<>();
    public Map<WarTeams, Location> teamSpawnLocation = new HashMap<>();

    public boolean invitationPeriod = false;
    public boolean inviteAll = true;

    private int tempAccept = 0;

    public Wargame(Player player) {
        this.warMaster = player;
        description = "";
        state = GameState.PREPARE;
    }

    public void prepareGame() {
        new ChatHandler().sendProposal(warMaster);
    }

    public void sendInvite() {
        if (getState() == GameState.ON) return;
        if (loc == null) {
            getWarMaster().sendMessage(ChatColor.RED + "Location not specified.");
            return;
        }
        invitationPeriod = true;
        ChatHandler handler = new ChatHandler();
        if (inviteAll) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                handler.sendInvitation(player);
            }
        } else {
            for (WarPlayer player : playerList.values()) {
                handler.sendInvitation(player.getPlayer());
            }
        }
        Bukkit.getScheduler().runTaskLater(Wargames.getInstance(), () -> invitationPeriod = false, 6000);
    }

    public void startGame() {
        if (getState() == GameState.ON) return;
        invitationPeriod = false;
        setState(GameState.ON);
        for (WarPlayer player : playerList.values()) {
            if (!player.isAcceptGame() || !player.getPlayer().isOnline()) {
                playerList.remove(player.getUUID());
                continue;
            }
            player.setOldTeam(Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard().getEntryTeam(player.getPlayer().getName()));
        }
        if (getSorting() == TeamSorting.Random && getSize() != WarSize.FFA) {
            int membersEachTeam = playerList.size() / getSize().getSizeAsInt();
            List<WarPlayer> list = new ArrayList<>(playerList.values());
            playerList.clear();
            for (int i = 0; i < getSize().getSizeAsInt(); i++) {
                WarTeams team = WarTeams.values()[i];
                for (int members = 0; members < membersEachTeam; members++) {
                    WarPlayer player = list.get(new Random().nextInt(list.size()));
                    player.setTeam(team);
                    playerList.put(player.getUUID(), player);
                    list.remove(player);
                }
            }
        }
        setGraceOn(true);
        for (WarPlayer player : playerList.values()) {
            if (getSize() == WarSize.FFA) player.setTeam(WarTeams.FFA);
            Player p = player.getPlayer();
            if (isTeleportPlayers()) p.teleport(getLocation());
            p.setGameMode(getMode());
            player.getTeam().getTeam().addEntry(p.getName());
            p.sendTitle(ChatColor.GOLD + "Grace Period", ChatColor.YELLOW + "You have " + getGraceDuration() + " minute(s) to prepare! ");
            getLocation().getWorld().playSound(getLocation(), Objects.requireNonNull(Wargames.getInstance().getConfig().getString("Sounds.Grace-Start")), 1, 1);
        }
        Bukkit.getScheduler().runTaskLater(Wargames.getInstance(), () -> {
            setGraceOn(false);
            Bukkit.broadcastMessage(ChatColor.BOLD + "" + ChatColor.RED + "Grace Period has ended.");
            if (!isBringBed()) {
                for (WarPlayer player : playerList.values()) {
                    if (player.getPlayer().isOnline()) {
                        player.getPlayer().teleport(teamSpawnLocation.get(player.getTeam()));
                        player.getPlayer().sendTitle(ChatColor.DARK_RED + "Battle", ChatColor.RED + "Let the games begin!");
                    }
                }
            }
            Bukkit.broadcastMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "Wargames begin.");
            getLocation().getWorld().playSound(getLocation(), Objects.requireNonNull(Wargames.getInstance().getConfig().getString("Sounds.War-Start")), 1, 1);
        }, 1200);
        Bukkit.getScheduler().runTaskLater(Wargames.getInstance(), this::stopGame, (long) getDuration() * 1200);
    }

    public void stopGame() {
        setState(GameState.OFF);
        Bukkit.broadcastMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "Game over.");
        for (WarPlayer player : playerList.values()) {
            player.getPlayer().setGameMode(GameMode.SURVIVAL);
            player.getOldTeam().addEntry(player.getPlayer().getName());
        }
        getLocation().getWorld().playSound(getLocation(), Objects.requireNonNull(Wargames.getInstance().getConfig().getString("Sounds.War-End")), 1, 1);
        Wargames.getInstance().setGame(null);
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

    public boolean isTeleportPlayers() {
        return teleportPlayers;
    }

    public void setTeleportPlayers(boolean teleportPlayers) {
        this.teleportPlayers = teleportPlayers;
        prepareGame();
    }

    public TeamSorting getSorting() {
        return sorting;
    }

    public void setSorting(TeamSorting sorting) {
        this.sorting = sorting;
        prepareGame();
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public boolean isBringBed() {
        return bringBed;
    }

    public void setBringBed(boolean bringBed) {
        this.bringBed = bringBed;
        prepareGame();
    }

    public boolean isGraceOn() {
        return graceOn;
    }

    public void setGraceOn(boolean graceOn) {
        this.graceOn = graceOn;
    }

    public int getGraceDuration() {
        return graceDuration;
    }

    public void setGraceDuration(int graceDuration) {
        this.graceDuration = graceDuration;
        prepareGame();
    }

    public int getTempAccept() {
        return tempAccept;
    }

    public void setTempAccept(int tempAccept) {
        this.tempAccept = tempAccept;
        if (invitationPeriod) warMaster.sendMessage(ChatColor.LIGHT_PURPLE + "" + tempAccept + " players accepted.");
    }
}
