package me.ras1b.PureHQ.Crates;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.ras1b.PureHQ.PureHQMain;
import me.ras1b.PureHQ.UsefulMethods.PureHQFileManage;
import me.ras1b.PureHQ.UsefulMethods.PureHQHiddenString;
import me.ras1b.PureHQ.UsefulMethods.PureHQStrings;



public class PureHQCrate {
	public boolean cratemain(Player player, String[] args){
		if (!player.isOp()){
			player.sendMessage(PureHQStrings.NO_PERMISSION);
			return true;
		}
		if (args.length == 2){
			if (args[0].equals("create") || args[0].equals("add") || args[0].equals("c"))return cratecreate (player,args);
			if (args[0].equals("remove") || args[0].equals("r") || args[0].equals("delete")) return crateremove (player,args);
			if (args[0].equals("set") || args[0].equals("s")) return crateset (player,args);
			if (args[0].equals("removeitem") || args[0].equals("ri")) return crateremoveitem (player,args);
		}else if (args.length == 3){
			if (args[0].equals("additem") || args[0].equals("ai")) return crateadditem (player,args);
			if (args[0].equals("getitem") || args[0].equals("gi")) return crategetitem (player,args);
		}
		player.sendMessage(PureHQStrings.WRONG_COMMAND);
		return true;
	}
	
	public boolean cratecreate(Player player, String[] args){
		List<String> obj = PureHQMain.crates.getStringList("Crates");
		if (!obj.contains(args[1])){
			obj.add(args[1]);
			PureHQMain.crates.set("Crates",obj);
			PureHQFileManage.saveYamls();
			player.sendMessage(PureHQStrings.CRATE_ADDED.replace("{crate}", args[1]));
			return true;
		}else{
			player.sendMessage(PureHQStrings.CRATE_EXISTS);
			return true;
		}
	}
	
	public boolean crateremove(Player player, String[] args){
		List<String> obj = PureHQMain.crates.getStringList("Crates");
		if (obj == null || !obj.contains(args[1])){
			player.sendMessage(PureHQStrings.CRATE_DOESNT_EXIST);
			return true;
		}else{
			obj.remove(args[1]);
			PureHQMain.crates.set("Crates",obj);
			PureHQFileManage.saveYamls();
			player.sendMessage(PureHQStrings.CRATE_REMOVED.replace("{crate}", args[1]));
			return true;
		}
	}
	
	@SuppressWarnings("deprecation")
	public boolean crateset(Player player, String[] args){
		Block b = player.getTargetBlock(null, 100);
		if (b.getType() == Material.CHEST){
			List<String> obj = PureHQMain.crates.getStringList("Crates");
			if (obj == null || !obj.contains(args[1])){
				player.sendMessage(PureHQStrings.CRATE_DOESNT_EXIST);
				return true;
			}
			Location location = b.getLocation();
			String locString = location.getX() + ";" + location.getY() + ";" + location.getZ() + ";" + location.getWorld().getName();
			
			List<String> locList = PureHQMain.crates.getStringList("Crates locations."+args[1]);
			if (locList == null){
				locList = new ArrayList<String>();
				locList.add(locString);
				player.sendMessage(PureHQStrings.CRATE_SET.replace("{crate}",args[1]));
			}else{
				if (locList.contains(locString)){
					player.sendMessage(PureHQStrings.CHEST_CRATE_EXISTS.replace("{crate}", args[1]));
					return true;
				}
				locList.add(locString);
				player.sendMessage(PureHQStrings.CRATE_SET.replace("{crate}", args[1]));
			}
			PureHQMain.crates.set("Crates locations." + args[1], locList);
			PureHQFileManage.saveYamls();
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public boolean crateremoveitem(Player player, String[] args){
		String cratetype = args[1];
		ItemStack is = player.getItemInHand();
		if (is.getType().equals(Material.AIR)){
			player.sendMessage(PureHQStrings.REMOVE_AIR);
			return true;
		}
		List<ItemStack> itemlist = (List<ItemStack>)PureHQMain.crates.get("Crateitem." + cratetype);
		if (itemlist == null || !itemlist.contains(is)){
			player.sendMessage(PureHQStrings.ITEM_NOT_IN_CRATE);
			return true;
		}else{
			for (ItemStack i : itemlist){
				if (i != null && i.hasItemMeta() && is.hasItemMeta()){
					if (i.equals(is) && i.getItemMeta().equals(is.getItemMeta())){
						itemlist.remove(i);
						PureHQMain.crates.set("Crateitem."+cratetype, itemlist);
						PureHQFileManage.saveYamls();
						player.sendMessage(PureHQStrings.ITEM_CRATE_REMOVAL.replace("{item}", is.getType().toString()));
						break;
					}
				}
			}
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public boolean crategetitem(Player player, String[] args){
		String cratetype = args[1];
		if (!args[2].matches("[0-9]+")){
			player.sendMessage(PureHQStrings.CRATE_GET_ITEM_INT);
			return true;
		}
		int index = Integer.parseInt(args[2]);
		List<ItemStack> itemlist = (List<ItemStack>)PureHQMain.crates.get("Crateitem." + cratetype);
		if (itemlist != null){
			Inventory inv = Bukkit.createInventory(null, 27,"�2"+cratetype + "�3 crate");
			ItemStack[] islist = itemlist.toArray(new ItemStack[0]);
			inv.setContents(islist);
			if (index < 0 || index > inv.firstEmpty() -1){
				player.sendMessage(PureHQStrings.POSITION_EMPTY);
			}else{
				if (player.getInventory().firstEmpty() == -1){
					player.sendMessage(PureHQStrings.INVENTORY_NO_SPOT);
				}else{
					player.getInventory().setItem(player.getInventory().firstEmpty(), inv.getItem(index));
					player.sendMessage(PureHQStrings.CRATE_GET_ITEM);
				}
			}
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public boolean crateadditem(Player player, String[] args){
		if (args.length == 3){
			String percentage = args[2];
			if (!percentage.matches("[0-9]+")){
				player.sendMessage(PureHQStrings.USAGE_CRATE_ADD_ITEM);
				return true;
			}
			int percent = Integer.parseInt(percentage);
			List<ItemStack> itemlist = (List<ItemStack>)PureHQMain.crates.get("Crateitem." + args[1]);
			if (player.getItemInHand().getType() == Material.AIR){
				player.sendMessage(PureHQStrings.REMOVE_AIR);
				return true;
			}
			if (itemlist != null){
				ItemStack is = player.getItemInHand();
				List<String> lore = new ArrayList<String>();
				lore.add(PureHQHiddenString.encodeString(percent+ ""));
				ItemMeta im = is.getItemMeta();
				im.setLore(lore);
				is.setItemMeta(im);
				itemlist.add(is);
			}else{
				itemlist = new ArrayList<ItemStack>();
				ItemStack is = player.getItemInHand();
				List<String> lore = new ArrayList<String>();
				lore.add(PureHQHiddenString.encodeString(percent+ ""));
				ItemMeta im = is.getItemMeta();
				im.setLore(lore);
				is.setItemMeta(im);
				itemlist.add(is);
			}
			player.sendMessage(PureHQStrings.CRATE_SET_ITEM);
			PureHQMain.crates.set("Crateitem." + args[1], itemlist);
			PureHQFileManage.saveYamls();
		}else{
			player.sendMessage(PureHQStrings.USAGE_CRATE_ADD_ITEM);
		}
		return true;
	}
}