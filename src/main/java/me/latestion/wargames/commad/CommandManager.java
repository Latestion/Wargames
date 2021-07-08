package me.latestion.wargames.commad;

import me.latestion.wargames.Wargames;
import me.latestion.wargames.game.WarSize;
import me.latestion.wargames.util.ChatHandler;
import me.latestion.wargames.util.MessageHandler;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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

            plugin.startNewGame((Player) sender);

            if (args.length == 0) {
                return false;
            }

            if (args[0].equalsIgnoreCase("setup")) {

                if (args[1].equalsIgnoreCase("description")) {

                    if (args[2].equalsIgnoreCase("add")) {
                        System.out.println(plugin.getGame().getDescription());
                        plugin.getGame().getWarMaster().spigot().sendMessage(new MessageHandler("Type the war description in chat. ")
                                .addExtra(new MessageHandler(ChatColor.RED + "[Click here to copy previous description (if any).]")
                                        .setCustomEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, plugin.getGame().getDescription()))
                                            .setHoverEvent("Click here to copy").getComponent()).getComponent());
                        plugin.descriptionCache = true;
                    }

                    if (args[2].equalsIgnoreCase("remove")) {
                        plugin.getGame().setDescription("");
                        return false;
                    }

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
                    plugin.getGame().allowTeamSelection = Boolean.parseBoolean(args[2]);
                    return false;
                }

                if (args[1].equalsIgnoreCase("location")) {
                    plugin.getGame().setLocation(plugin.getGame().getWarMaster().getLocation());
                }

                if (args[1].equalsIgnoreCase("invite")) {
                    plugin.getGame().sendInvite();
                }

            }

            if (args[0].equalsIgnoreCase("invite")) {

                if (args.length == 1) return false;

                Player player = Bukkit.getPlayerExact(args[1]);


            }

        }
        return false;
    }

}
