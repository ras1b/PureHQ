package me.ras1b.PureHQ.Crates;

import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import me.ras1b.PureHQ.PureHQMain;
import me.ras1b.PureHQ.UsefulMethods.PureHQFileManage;
import me.ras1b.PureHQ.UsefulMethods.PureHQStrings;

public class PureHQCrateBreakListener implements Listener{
	@EventHandler
	public void playerItemBreakEvent(BlockBreakEvent event) {
		if (event.getBlock().getType().equals(Material.CHEST)){
			List<String> crateList = PureHQMain.crates.getStringList("Crates");
			Location location = event.getBlock().getLocation();
			String locString = location.getX() + ";" + location.getY() + ";" + location.getZ()
																	+ ";" + location.getWorld().getName();
			if (crateList != null){
				for (String s : crateList){
					List<String> locList = PureHQMain.crates.getStringList("Crates locations."+s);
					if (locList != null && locList.contains(locString)){
							if (!event.getPlayer().isOp()){
								event.getPlayer().sendMessage(PureHQStrings.CRATE_REMOVED_FAIL);
								event.setCancelled(true);
							}else{
								locList.remove(locString);
								event.getPlayer().sendMessage(PureHQStrings.CRATE_REMOVED.replace("{crate}", s));
								PureHQMain.crates.set("Crates locations."+s, locList);
								PureHQFileManage.saveYamls();
							}
					}
				}
			}
		}
	}
}