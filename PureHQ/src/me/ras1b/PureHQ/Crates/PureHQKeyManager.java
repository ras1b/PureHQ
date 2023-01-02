package me.ras1b.PureHQ.Crates;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import me.ras1b.PureHQ.UsefulMethods.PureHQStrings;



public class PureHQKeyManager {
	public static boolean addKey(Player player, Player playerKey, String cratename, int amount){
		ItemStack is = new ItemStack(Material.NETHER_STAR,amount);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(PureHQStrings.KEY_NAME.replace("{crate}", cratename));
		is.setItemMeta(im);
		playerKey.getInventory().setItem(playerKey.getInventory().firstEmpty(), is);
		playerKey.updateInventory();
		player.sendMessage(PureHQStrings.PLAYER_ADD_KEY.replace("{amount}", amount+"").replace("{crate}", cratename).replace("{player}", playerKey.getName()));
		return true;
	}
}
