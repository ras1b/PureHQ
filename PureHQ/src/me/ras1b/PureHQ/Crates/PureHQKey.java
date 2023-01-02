package me.ras1b.PureHQ.Crates;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import me.ras1b.PureHQ.PureHQMain;
import me.ras1b.PureHQ.UsefulMethods.PureHQStrings;


public class PureHQKey {
	public PureHQMain plugin;
	public PureHQKey(PureHQMain main){
		this.plugin = main;
	}
	
	public boolean keymain(CommandSender sender, String[] args){
		if (sender instanceof Player){
			Player player = (Player) sender;
			if (!player.isOp()){
				player.sendMessage(PureHQStrings.NO_PERMISSION);
				return true;
			}
			if (args.length == 4 && (args[0].equalsIgnoreCase("give")|| args[0].equalsIgnoreCase("add")))
				return keyadd(player, args);
			else player.sendMessage(PureHQStrings.WRONG_COMMAND);
		}
		else {
			if (args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("add")) return keyaddConsole(args);
		}
		return true;
	}

	@SuppressWarnings({ "deprecation" })
	public boolean keyadd(Player player, String[] args){
		List<String> o = (List<String>) PureHQMain.crates.getStringList("Crates");
		if (o != null && o.contains(args[2]) && args[3].matches("[0-9]+")){
			for (Player all : Bukkit.getServer().getOnlinePlayers()) {
				if (all.getName().equals(args[1])) {
					if (all.getInventory().firstEmpty() != -1) return PureHQKeyManager.addKey(player, all, args[2], Integer.parseInt(args[3]));
					else{
						player.sendMessage(PureHQStrings.INVENTORY_NO_SPOT);
						return true;
					}
				}      
			}
			player.sendMessage(PureHQStrings.PLAYER_DOESNT_EXIST);
		}else player.sendMessage(PureHQStrings.WRONG_COMMAND);
		return true;
	}
	
	@SuppressWarnings({ "deprecation" })
	public boolean keyaddConsole(String[] args){
		List<String> o = (List<String>) PureHQMain.crates.getStringList("Crates");
		if (o != null && o.contains(args[2]) && args[3].matches("[0-9]+")){
			for (Player all : Bukkit.getServer().getOnlinePlayers()) {
				if (all.getName().equals(args[1])) {
					if (all.getInventory().firstEmpty() != -1){
						ItemStack is = new ItemStack(Material.NETHER_STAR,Integer.parseInt(args[3]));
						ItemMeta im = is.getItemMeta();
						im.setDisplayName(PureHQStrings.KEY_NAME.replace("{crate}", args[2]));
						is.setItemMeta(im);
						all.getInventory().setItem(all.getInventory().firstEmpty(), is);
						all.updateInventory();
						return true;
					}
					else{
						return true;
					}
				}      
			}
		}
		return true;
	}
}