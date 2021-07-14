package me.latestion.wargames.commad;

import me.latestion.wargames.Wargames;
import me.latestion.wargames.game.WarPlayer;
import me.latestion.wargames.game.enums.TeamSorting;
import me.latestion.wargames.game.enums.WarSize;
import me.latestion.wargames.game.enums.WarTeams;
import me.latestion.wargames.util.MessageHandler;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor {

    private final Wargames plugin;

    public CommandManager(Wargames plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("wargames")) {

            if (args.length == 0) {
                plugin.startNewGame((Player) sender);
                return false;
            }

            if (args[0].equalsIgnoreCase("setup")) {

                if (args[1].equalsIgnoreCase("description")) {

                    if (args[2].equalsIgnoreCase("add")) {
                        plugin.getGame().getWarMaster().spigot().sendMessage(new MessageHandler("Type the war description in chat. ")
                                .addExtra(new MessageHandler(ChatColor.RED + "[Click here to copy previous description (if any).]")
                                        .setCustomEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, plugin.getGame().getDescription()))
                                        .setHoverEvent("Click here to copy").getComponent()).getComponent());
                        plugin.descriptionCache = true;
                        return false;
                    }

                    if (args[2].equalsIgnoreCase("remove")) {
                        plugin.getGame().setDescription("");
                        return false;
                    }
                }

                if (args[1].equalsIgnoreCase("duration")) {
                    plugin.durationCache = true;
                    sender.sendMessage(ChatColor.WHITE + "Enter a custom duration between 2 and 90.");
                    return false;
                }

                if (args[1].equalsIgnoreCase("mode")) {
                    plugin.getGame().setMode(GameMode.valueOf(args[2].toUpperCase()));
                    return false;
                }

                if (args[1].equalsIgnoreCase("teams")) {
                    plugin.getGame().setSize(WarSize.valueOf(args[2]));
                    return false;
                }

                if (args[1].equalsIgnoreCase("choose")) {
                    plugin.getGame().setSorting(TeamSorting.valueOf(args[2]));
                    return false;
                }

                if (args[1].equalsIgnoreCase("respawn")) {
                    if (args[2].equalsIgnoreCase("byob")) {
                        plugin.getGame().setBringBed(true);
                    } else {
                        plugin.getGame().setBringBed(false);
                    }
                    return false;
                }

                if (args[1].equalsIgnoreCase("location")) {
                    if (args[2].equalsIgnoreCase("reset")) {
                        plugin.getGame().setTeleportPlayers(!plugin.getGame().isTeleportPlayers());
                        return false;
                    }
                    if (args.length < 5) return false;
                    double x = Double.parseDouble(args[2]);
                    double y = Double.parseDouble(args[3]);
                    double z = Double.parseDouble(args[4]);
                    World world = ((Player) sender).getLocation().getWorld();
                    plugin.getGame().setLocation(new Location(world, x, y, z));
                    return false;
                }

                if (args[1].equalsIgnoreCase("invite")) {
                    plugin.getGame().sendInvite();
                    return false;
                }

                if (args[1].equalsIgnoreCase("spawn")) {
                    String team = args[2];
                    double x = Double.parseDouble(args[3]);
                    double y = Double.parseDouble(args[4]);
                    double z = Double.parseDouble(args[5]);
                    World world = ((Player) sender).getLocation().getWorld();
                    plugin.getGame().teamSpawnLocation.put(WarTeams.valueOf(team), new Location(world, x, y, z));
                    plugin.getGame().prepareGame();
                    return false;
                }

            }

            if (args[0].equalsIgnoreCase("invite")) {

                Player player = Bukkit.getPlayerExact(args[1]);

                if (player == null) {
                    return false;
                }

                if (args[2].equalsIgnoreCase("accept")) {
                    if (plugin.getGame().getSorting() == TeamSorting.Player_Choice) {
                        if (!plugin.getGame().playerList.containsKey(player.getUniqueId())) {
                            player.sendMessage(ChatColor.RED + "Select a team before accepting the invitation.");
                            return false;
                        }
                    }
                    if (plugin.getGame().playerList.containsKey(player.getUniqueId())) {
                        plugin.getGame().playerList.get(player.getUniqueId()).acceptGame = true;
                        return false;
                    }
                    plugin.getGame().playerList.put(player.getUniqueId(), new WarPlayer(player.getUniqueId(), true));
                    player.sendMessage(ChatColor.GREEN + "Invitation accepted.");
                } else {
                    if (plugin.getGame().playerList.containsKey(player.getUniqueId())) {
                        plugin.getGame().playerList.get(player.getUniqueId()).acceptGame = false;
                        return false;
                    }
                    plugin.getGame().playerList.remove(player.getUniqueId());
                }
                return false;
            }

            if (args[0].equalsIgnoreCase("bed")) {
                Player player = Bukkit.getPlayerExact(args[1]);
                if (player != null)
                    player.setBedSpawnLocation(new Location(Bukkit.getWorld(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5])));
            }

            if (args[0].equalsIgnoreCase("begin")) {
                Player player = (Player) sender;
                if (!player.equals(plugin.getGame().getWarMaster())) return false;
                plugin.getGame().startGame();
            }

            if (args[0].equalsIgnoreCase("join")) {

                Player player = Bukkit.getPlayerExact(args[1]);
                if (player == null) return false;
                String team = args[2];
                if (plugin.getGame().playerList.containsKey(player.getUniqueId())) {
                    plugin.getGame().playerList.get(player.getUniqueId()).setTeam(WarTeams.valueOf(team));
                    return false;
                }
                plugin.getGame().playerList.put(player.getUniqueId(), new WarPlayer(player.getUniqueId()).setTeam(WarTeams.valueOf(team)));
                return false;
            }

            if (args[0].equalsIgnoreCase("teams")) {

                if (plugin.getGame() == null) return false;
                if (plugin.getGame().getSorting() != TeamSorting.Preset) return false;
                if (args.length <= 2) {
                    sender.sendMessage(ChatColor.RED + "Invalid format: /wargames teams {player} {team}");
                    return false;
                }

                Player player = Bukkit.getPlayerExact(args[1]);
                if (player == null) {
                    sender.sendMessage(ChatColor.RED + "Invalid Player Specified: " + args[1]);
                    return false;
                }
                WarTeams team = WarTeams.valueOf(args[2].toUpperCase());

                if (plugin.getGame().playerList.containsKey(player.getUniqueId())) {
                    plugin.getGame().playerList.get(player.getUniqueId()).setTeam(team);
                    return false;
                }
                plugin.getGame().playerList.put(player.getUniqueId(), new WarPlayer(player.getUniqueId()).setTeam(team));
                sender.sendMessage(ChatColor.GREEN + player.getName() + "'s team set to " + args[2]);
            }
            if (args[0].equalsIgnoreCase("roster")) {

                if (args.length == 1) return false;

                if (args[1].equalsIgnoreCase("all")) {
                    plugin.getGame().inviteAll = !plugin.getGame().inviteAll;
                    plugin.getGame().prepareGame();
                    return false;
                }

                if (args[1].equalsIgnoreCase("add")) {

                    if (args.length == 2) return false;

                    for (int i = 2; i < args.length; i++) {
                        String p = args[i];
                        Player player = Bukkit.getPlayerExact(p);
                        if (player == null) {
                            sender.sendMessage(ChatColor.RED + p + " is an invalid player.");
                            return false;
                        }
                        plugin.getGame().playerList.put(player.getUniqueId(), new WarPlayer(player.getUniqueId()));
                        plugin.getGame().inviteAll = false;
                    }
                }
            }
        }
        return false;
    }

}
