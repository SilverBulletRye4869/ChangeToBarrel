package silverassist.changetobarrel;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import silverassist.changetobarrel.command.Command;
import silverassist.changetobarrel.command.Tab;

import java.util.LinkedList;

public final class ChangeToBarrel extends JavaPlugin {
    private static JavaPlugin plugin = null;

    @Override
    public void onEnable() {
        //================================イベント登録
        this.getServer().getPluginManager().registerEvents(new Event(), this);
        //================================コマンド登録
        PluginCommand command = getCommand("changebarrel");
        if(command!=null){
            command.setExecutor(new Command());
            command.setTabCompleter(new Tab());
        }
        //================================インスタンス格納
        plugin = this;
        //================================yml設定
        this.saveDefaultConfig();
        if(this.getConfig().get("enabled_world") == null)this.getConfig().set("enabled_world",new LinkedList<>());
        this.saveConfig();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    //インスタンス取得
    public static JavaPlugin getInstance(){
        return plugin;
    }
}
