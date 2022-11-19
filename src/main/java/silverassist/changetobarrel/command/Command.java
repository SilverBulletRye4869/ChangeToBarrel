package silverassist.changetobarrel.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import silverassist.changetobarrel.ChangeToBarrel;

import java.util.List;

public class Command implements CommandExecutor {
    static final List<String> OPTIONS = List.of("add","addthis","remove","removethis");
    private final String PREFIX = "§b§[§e§lChangeToBarrel§b§l]";
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if(args.length<1)return true;
        if(!(sender instanceof Player))return true;
        if(!OPTIONS.contains(args[0]))return true;

        FileConfiguration config = ChangeToBarrel.getInstance().getConfig();
        Player p = (Player)sender;
        String worldName;  //ワールドName
        String uuidStr;  //ワールドuuid格納しておく奴
        List<String> enabledWorlds;  //有効ワールド格納しておく奴

        //====================================================================メイン処理
        //有効ワールドリスト取得
        if(args[0].contains("this")){
            uuidStr=p.getWorld().getUID().toString();
            worldName = p.getWorld().getName();
        }
        else{
            if(args.length==1)return true;
            if(Bukkit.getWorld(args[1]) == null)return true;
            uuidStr =Bukkit.getWorld(args[1]).getUID().toString();
            worldName = args[1];
        }

        enabledWorlds = (List<String>) config.getList("enabled_world");
        if(args[0].contains("add")){
            //======================================有効ワールド追加
            if(enabledWorlds.contains(uuidStr)){
                p.sendMessage(PREFIX+"§cそのワールドは登録されています");
                return true;
            }
            enabledWorlds.add(uuidStr);
            p.sendMessage(PREFIX+"§d"+worldName+"§aを正常に登録しました");

        }else{
            //======================================有効ワールド削除
            if(!enabledWorlds.contains(uuidStr)){
                p.sendMessage(PREFIX+"§cそのワールドは登録されていません");
                return true;
            }
            enabledWorlds.remove(uuidStr);
            p.sendMessage(PREFIX+"§d"+worldName+"§aを正常に削除しました");
        }

        //保存
        config.set("enabled_world",enabledWorlds);
        ChangeToBarrel.getInstance().saveConfig();
        return true;
    }
}
