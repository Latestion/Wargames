package me.latestion.wargames.util;

import me.latestion.wargames.Wargames;
import me.latestion.wargames.game.Wargame;
import me.latestion.wargames.game.enums.TeamSorting;
import me.latestion.wargames.game.enums.WarSize;
import me.latestion.wargames.game.enums.WarTeams;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class ChatHandler {

    public void sendProposal(Player player) {
        Wargame game = Wargames.getInstance().getGame();
        player.spigot().sendMessage(
                new MessageHandler(ChatColor.GRAY + "================================\n").getComponent(),
                new MessageHandler(ChatColor.GRAY + "-------")
                        .addExtra(new MessageHandler("Wargames Proposal", true).setColor(ChatColor.GOLD).getComponent())
                        .addExtra(new MessageHandler(ChatColor.GRAY + "---------\n").getComponent()).getComponent(),
                new MessageHandler(ChatColor.WHITE + "Description: ").setHoverEvent(ChatColor.YELLOW + game.getDescription())
                        .addExtra(new MessageHandler(ChatColor.GREEN + "[add text] ")
                                .setHoverEvent("Edit or Add text").setClickEvent("/wargames setup description add").getComponent())
                        .addExtra(new MessageHandler(ChatColor.GREEN + "[remove]\n").setClickEvent("/wargames setup description remove").getComponent()).getComponent(),
                new MessageHandler(ChatColor.WHITE + "Mode: ")
                        .addExtra(new MessageHandler("[Adventure] ", game.getMode() == GameMode.ADVENTURE).setColor(ChatColor.GREEN)
                                .setClickEvent("/wargames setup mode adventure").getComponent())
                        .addExtra(new MessageHandler("[Survival]\n", game.getMode() == GameMode.SURVIVAL).setColor(ChatColor.RED)
                                .setClickEvent("/wargames setup mode survival").getComponent()).getComponent(),
                new MessageHandler(ChatColor.WHITE + "Teams: ")
                        .addExtra(new MessageHandler("[FFA] ", game.getSize() == WarSize.FFA).setColor(ChatColor.DARK_RED)
                                .setClickEvent("/wargames setup teams FFA").getComponent())
                        .addExtra(new MessageHandler("[2] ", game.getSize() == WarSize.T_2).setColor(ChatColor.RED)
                                .setClickEvent("/wargames setup teams T_2").getComponent())
                        .addExtra(new MessageHandler("[3] ", game.getSize() == WarSize.T_3).setColor(ChatColor.LIGHT_PURPLE)
                                .setClickEvent("/wargames setup teams T_3").getComponent())
                        .addExtra(new MessageHandler("[4]\n", game.getSize() == WarSize.T_4).setColor(ChatColor.DARK_PURPLE)
                                .setClickEvent("/wargames setup teams T_4").getComponent()).getComponent(),
                game.getSize() == WarSize.FFA ? new MessageHandler("").getComponent() :
                        new MessageHandler(ChatColor.WHITE + "Team Sorting: ")
                                .addExtra(new MessageHandler("[Random] ", game.getSorting() == TeamSorting.Random).setColor(ChatColor.GREEN)
                                        .setClickEvent("/wargames setup choose Random").getComponent())
                                .addExtra(new MessageHandler("[Player's Choice] ", game.getSorting() == TeamSorting.Player_Choice).setColor(ChatColor.RED)
                                        .setClickEvent("/wargames setup choose Player_Choice").getComponent())
                                .addExtra(new MessageHandler("[Preset]\n", game.getSorting() == TeamSorting.Preset).setColor(ChatColor.YELLOW)
                                        .setClickEvent("/wargames setup choose Preset").getComponent()).getComponent(),
                new MessageHandler(ChatColor.WHITE + "Roster: ")
                        .addExtra(new MessageHandler("[All] ", game.inviteAll)
                                .setClickEvent("/wargames roster all")
                                .setHoverEvent("Click to invite all players.").setColor(ChatColor.GREEN).getComponent())
                        .addExtra(new MessageHandler("[Select Players] \n", !game.inviteAll).setColor(ChatColor.RED)
                                .setCustomEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wargames roster add "))
                                .setHoverEvent("Click to invite selected players").getComponent()).getComponent(),
                new MessageHandler(ChatColor.WHITE + "Duration: ").setHoverEvent(ChatColor.WHITE + "" + game.getDuration() + " Minutes")
                        .addExtra(new MessageHandler(ChatColor.YELLOW + "[Enter Time in Minutes]\n").setClickEvent("/wargames setup duration").getComponent()).getComponent(),
                new MessageHandler(ChatColor.WHITE + "Location: ").addLocToHover(game.getLocation())
                        .addExtra(new MessageHandler(ChatColor.GREEN + "[Click To Set] ")
                                .setCustomEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wargames setup location ")).getComponent())
                        .addExtra(new MessageHandler("[Disable]\n", game.isTeleportPlayers()).setColor(ChatColor.RED).setHoverEvent("Disables Player Teleporting")
                                .setClickEvent("/wargames setup location reset").getComponent()).getComponent(),
                new MessageHandler(ChatColor.WHITE + "Respawn Method: ")
                        .addExtra(new MessageHandler("[Preset] ", !game.isBringBed()).setColor(ChatColor.GREEN)
                                .setClickEvent("/wargames setup respawn preset").getComponent())
                        .addExtra(new MessageHandler("[BYOB]\n", game.isBringBed()).setColor(ChatColor.RED)
                                .setClickEvent("/wargames setup respawn byob").getComponent()).getComponent(),
                respawnPoints(game).getComponent(),
                new MessageHandler(ChatColor.GRAY + "-------")
                        .addExtra(new MessageHandler(ChatColor.DARK_RED + "[Send out Invitations]")
                                .setHoverEvent(ChatColor.GRAY + "Click here to send the invitations.").setClickEvent("/wargames setup invite").getComponent())
                        .addExtra(new MessageHandler(ChatColor.GRAY + "-------\n").getComponent()).getComponent(),
                new MessageHandler(ChatColor.GRAY + "================================\n").getComponent());
    }

    public MessageHandler respawnPoints(Wargame game) {
        if (game.isBringBed()) return new MessageHandler("");
        MessageHandler component = new MessageHandler("Respawn Point(s): ");
        int size = game.getSize().getSizeAsInt();
        for (int i = 0; i < size; i++) {
            WarTeams team = WarTeams.values()[i];
            if (size == 1) {
                team = WarTeams.FFA;
            }
            String teamName = team.toString();
            component.addExtra(new MessageHandler(game.teamSpawnLocation.containsKey(team)
                    ? "[" + game.teamSpawnLocation.get(team).getBlockX() + ", " + game.teamSpawnLocation.get(team).getBlockY() + ", "
                    + game.teamSpawnLocation.get(team).getBlockZ() + "]" + (i + 1 == size ? "\n" : " ") : "[]" + (i + 1 == size ? "\n" : " "))
                    .setHoverEvent(teamName + " Spawn point")
                    .setCustomEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wargames setup spawn " + teamName + " ")).getComponent());
        }
        return component;
    }

/*
========================================
----------Wargames Proposal-------------
Description: [edit text] [remove]
Mode: [Adventure] [Survival]
Teams: [FFA] [2] [3] [4]
Team Sorting: [Random] [Player’s Choice] [Preset]
Duration: [Enter Time in Minutes]
Location: [Click to set] [Disable]
Respawn Method: [Preset] [BYOB]
Respawn Point(s): [] [] [] []
---------[Send out Invitations]---------
=========================================

*/
/*
====================================
        Wargames Invitation
        Description: ""
        Mode: [Mode]
        Teams: [Count]
        Team sorting: [Setting]
        Duration: [Time]
        Location: [coordinates]
        Choose Team: [list of teams]
        [Accept]-[Decline]
====================================
*/

    public void sendInvitation(Player player) {
        Wargame game = Wargames.getInstance().getGame();
        player.spigot().sendMessage(new MessageHandler(ChatColor.GRAY + "================================\n").getComponent(),
                new MessageHandler(ChatColor.GRAY + "-------")
                        .addExtra(new MessageHandler("Wargames Invitation", true).setColor(ChatColor.GOLD).getComponent())
                        .addExtra(new MessageHandler(ChatColor.GRAY + "---------\n").getComponent()).getComponent(),
                new MessageHandler(ChatColor.WHITE + "Description: " + game.getDescription() + "\n").getComponent(),
                new MessageHandler(ChatColor.WHITE + "Mode: " + ChatColor.GREEN + game.getMode().toString() + "\n").getComponent(),
                new MessageHandler(ChatColor.WHITE + "Teams: " + game.getSize().getString() + "\n").getComponent(),
                game.getSize() == WarSize.FFA ? new MessageHandler("").getComponent() :
                        new MessageHandler(ChatColor.WHITE + "Team sorting: " + game.getSorting().toString() + "\n").getComponent(),
                new MessageHandler(ChatColor.WHITE + "Duration: " + game.getDuration() + " Minutes\n").getComponent(),
                new MessageHandler(ChatColor.WHITE + "Location: " + "X: " + game.getLocation().getBlockX() + " Y: " + game.getLocation().getBlockY() + " Z: "
                        + game.getLocation().getBlockZ() + "\n").getComponent(),
                addTeams(game, player).getComponent(),
                new MessageHandler(ChatColor.GRAY + "---------")
                        .addExtra(new MessageHandler("[Accept]", true).setColor(ChatColor.GREEN)
                                .setClickEvent("/wargames invite " + player.getName() + " accept")
                                .setHoverEvent("Click to accept the war invitation.").getComponent())
                        .addExtra(new MessageHandler(ChatColor.GRAY + "-").getComponent())
                        .addExtra(new MessageHandler("[Decline]", true).setColor(ChatColor.RED)
                                .setClickEvent("/wargames invite " + player.getName() + " decline")
                                .setHoverEvent("Click to decline the war invitation.").getComponent())
                        .addExtra(new MessageHandler(ChatColor.GRAY + "---------\n").getComponent()).getComponent(),
                new MessageHandler(ChatColor.GRAY + "================================\n").getComponent()
        );
    }

    private MessageHandler addTeams(Wargame game, Player player) {
        if (game.getSorting() == TeamSorting.Random) {
            return new MessageHandler("");
        }
        if (game.getSorting() == TeamSorting.Preset) {
            return new MessageHandler("Your Team: " + game.playerList.get(player.getUniqueId()).getTeam().getColor()
                    + game.playerList.get(player.getUniqueId()).getTeam().toString() + "\n");
        }
        MessageHandler component = new MessageHandler(ChatColor.WHITE + "Choose Team: ");
        if (game.getSize().getSizeAsInt() == 1) {
            return new MessageHandler("Your Team: " + WarTeams.FFA.getColor() + "[FFA]\n");
        }
        int size = game.getSize().getSizeAsInt();
        for (int i = 0; i < size; i++) {
            WarTeams team = WarTeams.values()[i];
            String teamName = team.toString();
            component.addExtra(new MessageHandler(i + 1 == size ? "[" + teamName + "]\n" : "[" + teamName + "] ")
                    .setClickEvent("/wargames join " + player.getName() + " " + teamName).setColor(team.getColor()).getComponent());
        }
        return component;
    }
}