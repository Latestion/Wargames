package me.latestion.wargames.util;

import me.latestion.wargames.Wargames;
import me.latestion.wargames.game.WarSize;
import me.latestion.wargames.game.Wargame;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class ChatHandler {

    public void sendProposal(Player player) {
        Wargame game = Wargames.getInstance().getGame();
        player.spigot().sendMessage(
                new MessageHandler(ChatColor.GRAY + "================================\n").getComponent(),
                new MessageHandler(ChatColor.GRAY + "-------" + ChatColor.BOLD + ChatColor.GOLD + "Wargames Proposal" + ChatColor.RESET + ChatColor.GRAY + "---------\n").getComponent(),
                new MessageHandler(ChatColor.WHITE + "Description: ").setHoverEvent(ChatColor.YELLOW + game.getDescription())
                        .addExtra(new MessageHandler(ChatColor.GREEN + "[add text] ").setClickEvent("/wargames setup description add").getComponent())
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
                new MessageHandler(ChatColor.WHITE + "Players Choose Own Teams: ")
                        .addExtra(new MessageHandler(game.allowTeamSelection ? ChatColor.BOLD + "" + ChatColor.GREEN + "[True] " : ChatColor.GREEN + "[True] ")
                                .setClickEvent("/wargames setup choose true").getComponent())
                        .addExtra(new MessageHandler(game.allowTeamSelection ? ChatColor.RED + "[False]\n" : ChatColor.BOLD + "" + ChatColor.RED + "[False]\n")
                                .setClickEvent("/wargames setup choose false").getComponent()).getComponent(),
                new MessageHandler(ChatColor.WHITE + "Duration: ").setHoverEvent(ChatColor.WHITE + "" + game.getDuration() + " Minutes")
                        .addExtra(new MessageHandler(ChatColor.YELLOW + "[Enter Time in Minutes]\n").getComponent()).getComponent(),
                new MessageHandler(ChatColor.WHITE + "Location: ")
                        .addExtra(new MessageHandler(ChatColor.GREEN + "[Click To Set] ").getComponent())
                        .addExtra(new MessageHandler(ChatColor.RED + "[Disable]\n").getComponent()).getComponent(),
                new MessageHandler(ChatColor.GRAY + "-------")
                        .addExtra(new MessageHandler(ChatColor.DARK_RED + "[Send out Invitations]")
                                .setHoverEvent(ChatColor.GRAY + "Click here to send the invitations.").setClickEvent("/wargames setup invite").getComponent())
                        .addExtra(new MessageHandler(ChatColor.GRAY + "-------\n").getComponent()).getComponent(),
                new MessageHandler(ChatColor.GRAY + "================================\n").getComponent());
    }

    public void sendInvitation() {
        Wargame game = Wargames.getInstance().getGame();
        TextComponent[] c = new TextComponent[]{
                new MessageHandler(ChatColor.GRAY + "================================\n").getComponent(),
                new MessageHandler(ChatColor.GRAY + "-------" + ChatColor.BOLD + ChatColor.GOLD + "Wargames Invitation" + ChatColor.RESET + ChatColor.GRAY + "---------\n").getComponent(),
                new MessageHandler(ChatColor.WHITE + "Description: " + game.getDescription() + "\n").getComponent(),
                new MessageHandler(ChatColor.WHITE + "Mode: " + game.getMode().toString() + "\n").getComponent(),
                new MessageHandler(ChatColor.WHITE + "Teams: ")
                        .addExtra(new MessageHandler(ChatColor.DARK_RED + "[FFA] ").getComponent())
                        .addExtra(new MessageHandler(ChatColor.RED + "[2] ").getComponent())
                        .addExtra(new MessageHandler(ChatColor.LIGHT_PURPLE + "[3] ").getComponent())
                        .addExtra(new MessageHandler(ChatColor.DARK_PURPLE + "[4]\n").getComponent()).getComponent(),
                new MessageHandler(ChatColor.WHITE + "Duration: " + game.getDuration() + "\n").getComponent(),
                new MessageHandler(ChatColor.WHITE + "Location: " + "X:" + game.getLocation().getBlockX() + " Y:" + game.getLocation().getBlockY() + " Z:"
                        + game.getLocation().getBlockZ() + "\n").getComponent(),
                new MessageHandler(ChatColor.GRAY + "---------")
                        .addExtra(new MessageHandler(ChatColor.GREEN + "[Accept]").getComponent())
                        .addExtra(new MessageHandler(ChatColor.GRAY + "-").getComponent())
                        .addExtra(new MessageHandler(ChatColor.RED + "[Decline]").getComponent())
                        .addExtra(new MessageHandler(ChatColor.GRAY + "---------").getComponent()).getComponent(),
                new MessageHandler(ChatColor.WHITE + "================================\n").getComponent()};
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.spigot().sendMessage(c);
        }
    }

}

/*
====================================
----------Wargames Invitation----------
Description: ""
Mode: [Mode]---------------------------
Teams: [Count]------------------------
Duration: [Time]-----------------------
Location: [Location]--------------------
------------------------------------
Choose Team: [List of Teams]-----------
------------------------------------
---------[Accept]-[Decline]---------
====================================
 */

/*
========================================
----------Wargames Proposal-------------
Description: [add text] [edit] [remove]-
Mode: [Adventure] [Survival]------------
Teams: [FFA] [2] [3] [4]----------------
Players Choose Own Teams: [True] [False]
Duration: [Enter Time in Minutes]-------
Location: [Click to set] [Disable]------
---------[Send out Invitations]---------
========================================
 */
