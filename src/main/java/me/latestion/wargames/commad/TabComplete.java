package me.latestion.wargames.commad;

import me.latestion.wargames.game.enums.WarTeams;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TabComplete implements TabCompleter {

    private final List<String> array = new ArrayList<>();

    public TabComplete() {
        for (WarTeams team : WarTeams.values()) array.add(team.toString());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if (label.equalsIgnoreCase("wargames")) {

            if (!(sender instanceof Player)) {
                return null;
            }

            Player player = (Player) sender;

            if (args.length == 0) {
                return Collections.singletonList("teams");
            }

            if (args[0].equalsIgnoreCase("setup")) {

                if (args.length == 1) return null;

                if (args[1].equalsIgnoreCase("location")) {

                    if (args.length == 3) {
                        return Collections.singletonList(player.getLocation().getBlockX() + "");
                    }

                    if (args.length == 4) {
                        return Collections.singletonList(player.getLocation().getBlockY() + "");
                    }

                    if (args.length == 5) {
                        return Collections.singletonList(player.getLocation().getBlockZ() + "");
                    }
                }

                if (args[1].equalsIgnoreCase("spawn")) {

                    if (args.length == 2) return null;

                    if (args.length == 4) {
                        return Collections.singletonList(player.getLocation().getBlockX() + "");
                    }

                    if (args.length == 5) {
                        return Collections.singletonList(player.getLocation().getBlockY() + "");
                    }

                    if (args.length == 6) {
                        return Collections.singletonList(player.getLocation().getBlockZ() + "");
                    }

                }
            }
            if (args[0].equalsIgnoreCase("teams")) {

                if (args.length == 1) {
                    return null;
                }

                if (args.length == 2) {
                    return array;
                }

            }
        }

        return null;
    }

}
