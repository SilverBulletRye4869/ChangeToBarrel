package silverassist.changetobarrel;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import java.util.Map;

public class Event implements Listener {
    @EventHandler
    public void ChestPlaceEvent(BlockPlaceEvent e){
        Block block = e.getBlock();
        if(!(block.getType() == Material.CHEST || block.getType() == Material.TRAPPED_CHEST))return;  //チェストとトラップチェストのみ対象
        Player p = e.getPlayer();
        if(!ChangeToBarrel.getInstance().getConfig().getStringList("enabled_world").contains(p.getWorld().getUID().toString()))return;  //有効ワールドに登録されているか
        if(p.hasPermission("cb.bypass"))return;  //チェスト使用権限があるか
        block.setType(Material.BARREL);

        //========================================================================樽の向きを調整
        BlockData blockData = block.getBlockData();
        BlockFace face;
        switch ((int)(e.getPlayer().getLocation().getPitch() / 45.1)){
            case -1:
                face = BlockFace.DOWN;
                break;
            case 1:
                face = BlockFace.UP;
                break;
            default:
                Map<Integer, BlockFace> faceMapByYaw = Map.of(-1,BlockFace.WEST,0,BlockFace.NORTH,1,BlockFace.EAST);
                int yawInteger = Math.round(e.getPlayer().getLocation().getYaw() / 90);
                if(faceMapByYaw.containsKey(yawInteger))face=faceMapByYaw.get(yawInteger);
                else face = BlockFace.SOUTH;
        }
        ((Directional)blockData).setFacing(face);
        block.setBlockData(blockData);
    }
}
