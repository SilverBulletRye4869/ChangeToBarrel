package silverassist.changetobarrel.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

import static silverassist.changetobarrel.command.Command.OPTIONS;

public class Tab implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length==1)return OPTIONS;
        else{
            if(args[0].contains("this") || args[0].equals("list"))return null;
            List<String> worldName = new ArrayList<>();
            Bukkit.getWorlds().forEach(world -> worldName.add(world.getName()));
            return worldName;
        }
    }
}
