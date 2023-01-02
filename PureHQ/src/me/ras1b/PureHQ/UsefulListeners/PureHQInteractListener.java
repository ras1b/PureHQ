package me.ras1b.PureHQ.UsefulListeners;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.ras1b.PureHQ.UsefulMethods.PureHQHiddenString;
import me.ras1b.PureHQ.PureHQMain;
import me.ras1b.PureHQ.Economy.PureHQEconomyManager;


public class PureHQInteractListener implements Listener{
	@SuppressWarnings("unused")
	private PureHQMain plugin;
	public static int playerMoney = 10000;

    public PureHQInteractListener(PureHQMain plugin) {
        this.plugin = plugin;
    }
	

	@SuppressWarnings({ "unchecked", "deprecation" })
	@EventHandler
	public void playerInteractEvent(PlayerInteractEvent event) {
		Block b = event.getClickedBlock();
		if (b != null){
			if (b.getType() == Material.SIGN || b.getType() == Material.WALL_SIGN){
				if (event.getAction() == Action.RIGHT_CLICK_BLOCK){
					Sign s = (Sign) b.getState();
					String[] lines = s.getLines();
					if (lines[0].equalsIgnoreCase("�1[Buy]")){
						PureHQInteractListener.buySign(event);
					}
					else if (lines[0].equalsIgnoreCase("�1[Sell]")){
						PureHQInteractListener.sellSign(event);
					}
				}
			}
			if(b.getType() == Material.CHEST){
				if (event.getAction() == Action.LEFT_CLICK_BLOCK){
					ItemStack is = event.getPlayer().getItemInHand();
					Block bb = event.getClickedBlock();
					if (is.getType().equals(Material.NETHER_STAR)){
						ItemMeta im = is.getItemMeta();
						if (im.getDisplayName().contains(" �1key")){
							String cratetype = im.getDisplayName().substring(2, im.getDisplayName().length() -6);
							List<String> locList = PureHQMain.crates.getStringList("Crates locations." + cratetype);
							Location location = bb.getLocation();
							String locString = location.getX() + ";" + location.getY() + ";" + location.getZ() + ";" + location.getWorld().getName();
							if (locList == null || !locList.contains(locString)){
								event.getPlayer().sendMessage(ChatColor.RED + "Crate not recognized!");
							}
							else{
								List<ItemStack> itemlist = (List<ItemStack>)PureHQMain.crates.get("Crateitem." + cratetype);
								if (itemlist != null){
									Inventory inv = Bukkit.createInventory(null, 27,"�2"+cratetype + "�3 crate");
									ItemStack[] islist = itemlist.toArray(new ItemStack[0]);
									inv.setContents(islist);
									event.getPlayer().openInventory(inv);
								}
							}
							
						}
					}else{
						List<String> crateList = PureHQMain.crates.getStringList("Crates");
						Location location = bb.getLocation();
						String locString = location.getX() + ";" + location.getY() + ";" + location.getZ() + ";" + location.getWorld().getName();
						for (String cratetype : crateList){
							List<String> locList = PureHQMain.crates.getStringList("Crates locations." + cratetype);
							if (locList.contains(locString)){
								List<ItemStack> itemlist = (List<ItemStack>)PureHQMain.crates.get("Crateitem." + cratetype);
								if (itemlist != null){
									Inventory inv = Bukkit.createInventory(null, 27,"�2"+cratetype + "�3 crate");
									ItemStack[] islist = itemlist.toArray(new ItemStack[0]);
									inv.setContents(islist);
									event.getPlayer().openInventory(inv);
								}
							}
						}
					}
					
				}
				else if (event.getAction() == Action.RIGHT_CLICK_BLOCK){
					ItemStack is = event.getPlayer().getItemInHand();
					Block bb = event.getClickedBlock();
					if (is.getType().equals(Material.NETHER_STAR)){
						ItemMeta im = is.getItemMeta();
						if (im.getDisplayName().contains(" �1key")){
							event.setCancelled(true);
							String cratetype = im.getDisplayName().substring(2, im.getDisplayName().length() -6);
							List<String> locList = PureHQMain.crates.getStringList("Crates locations." + cratetype);
							Location location = bb.getLocation();
							String locString = location.getX() + ";" + location.getY() + ";" + location.getZ() + ";" + location.getWorld().getName();
							if (locList == null || !locList.contains(locString)){
								event.getPlayer().sendMessage(ChatColor.RED + "Crate not recognized!");
							}
							else{
								List<ItemStack> isList = (List<ItemStack>) PureHQMain.crates.get("Crateitem."+cratetype);
								if (isList != null){
									int percentages = 0;
									for (ItemStack items : isList){
										ItemMeta itemmeta = items.getItemMeta();
										String s = PureHQHiddenString.extractHiddenString(itemmeta.getLore().get(0));
										if (s.matches("[0-9]+")){
											percentages = percentages + Integer.parseInt(s);
										}
									}
									Inventory inv = Bukkit.createInventory(null, 9, "�2"+cratetype + " �3crate reward");
									ItemStack[] itemstacklist = new ItemStack[9];
									boolean breakit = false;
									for (int x = 0; x < 3; x = x + 1){
										Random random = new Random();
										int chosenNumber = random.nextInt(percentages);
										int percentages2 = 0;
										
										for (ItemStack items : isList){
											ItemMeta itemmeta = items.getItemMeta();
											String s = PureHQHiddenString.extractHiddenString(itemmeta.getLore().get(0));
											if (s.matches("[0-9]+")){
												percentages2 = percentages2 + Integer.parseInt(s);
											}
											
											if (chosenNumber < percentages2){
												if (event.getPlayer().getInventory().firstEmpty() == -1 || event.getPlayer().getInventory().firstEmpty() > 32){
													event.getPlayer().sendMessage(ChatColor.RED + "You don't have enough room in your inventory!");
													
													breakit = true;
													break;
												}else{
													ItemStack itemNoLore = new ItemStack(items);
													ItemMeta itemMetaNoLore = itemNoLore.getItemMeta();
													itemMetaNoLore.setLore(new ArrayList<String>());
													itemNoLore.setItemMeta(itemMetaNoLore);
													itemstacklist[x] = itemNoLore;
													
													
													break;
												}
											}
										}
										if (breakit){
											break;
										}
									}
									if (!breakit){
										StringBuilder sb = new StringBuilder();
										sb.append(ChatColor.GREEN + "You opened a crate and got ");
										ItemStack iteminHand = event.getPlayer().getItemInHand();
										if (iteminHand.getAmount() > 1){
											iteminHand.setAmount( iteminHand.getAmount() - 1);
											event.getPlayer().getInventory().setItemInHand(iteminHand);
										}else{
											event.getPlayer().getInventory().removeItem(event.getPlayer().getItemInHand());
										}
										event.getPlayer().updateInventory();
										inv.setContents(itemstacklist);
										if (inv != null){
											for (ItemStack i : itemstacklist){
												if (i != null && !i.getType().equals(Material.AIR)){
													sb.append(ChatColor.RED + "" + i.getAmount() + " " + i.getType() + "(S) ");
												}
											}
											sb.append(ChatColor.GREEN + "!");
											event.getPlayer().openInventory(inv);
											event.getPlayer().sendMessage(sb.toString());
										}
										
									}
									
								}
							}
						}	
					}else{
						List<String> crateList = PureHQMain.crates.getStringList("Crates");
						Location location = bb.getLocation();
						String locString = location.getX() + ";" + location.getY() + ";" + location.getZ() + ";" + location.getWorld().getName();
						for (String cratetype : crateList){
							List<String> locList = PureHQMain.crates.getStringList("Crates locations." + cratetype);
							if (locList.contains(locString)){
								event.setCancelled(true);
								event.getPlayer().damage(1);
								event.getPlayer().sendMessage(ChatColor.RED + "You don't have a key for this crate!");
							}
						}
					}
				}
			}
			ItemStack is = event.getPlayer().getItemInHand();
			if (is.getType().equals(Material.PAPER)){
			ItemMeta im = is.getItemMeta();
			List<String> lore = im.getLore();
			if (im.getDisplayName().contains("�e$")){
				if (im.getDisplayName().substring(3).matches("[0-9]+")){
					if (lore.get(0).equals("�9Rightclick to add to your balance!")){
						PureHQEconomyManager.addMoney(event.getPlayer().getName(), Integer.parseInt(im.getDisplayName().substring(3)));
						event.getPlayer().getInventory().remove(event.getPlayer().getItemInHand());
						event.getPlayer().sendMessage(ChatColor.GREEN + "Added "+ ChatColor.RED + "$" + im.getDisplayName().substring(3) + ChatColor.GREEN + " to your balance!");
					}else{
						
					}
				}
			}
		}
		}
	}
			
		
		
	
	
	@SuppressWarnings("deprecation")
	public static void sellSign(PlayerInteractEvent event){
		Block b = event.getClickedBlock();
		Player player = event.getPlayer();
		Sign s = (Sign) b.getState();
		String[] lines = s.getLines();
		String[] itemids = lines[2].split(":");
		NumberFormat format = NumberFormat.getCurrencyInstance();
		String price = lines[3];
		int p = 0;
		try {
			p = format.parse(price).intValue();
		} catch (Exception e) {
			player.sendMessage(ChatColor.RED + "Something went wrong!");
			e.printStackTrace();
		}
		
		
		if (itemids.length < 1 || itemids.length > 2){
			player.sendMessage(ChatColor.RED + "Something went wrong!");
		}else if (itemids.length == 1){
			ItemStack its = new ItemStack(Integer.parseInt(lines[2]),1);
			Material m = Material.getMaterial(Integer.parseInt(itemids[0]));
			
				if (!player.getInventory().containsAtLeast(its, Integer.parseInt(lines[1]))){
					player.sendMessage(ChatColor.RED + "You don't have enough items!");
				}else{
					PureHQEconomyManager.addMoney(player.getName(), p);
					
				int amount = Integer.parseInt(lines[1]);
				int leftover = amount % 64;
				amount = amount - leftover;
				int stacks = amount / 64;	
				int InvSpaceNeeded = stacks;
				if (leftover > 0){
					InvSpaceNeeded = InvSpaceNeeded + 1;
				}
				ItemStack is = new ItemStack(Integer.parseInt(lines[2]),64);
				ItemStack is2 = new ItemStack(Integer.parseInt(lines[2]),leftover);
				for (int i = 0; i < stacks; i++){
					if (player.getInventory().containsAtLeast(is,1)){
						player.getInventory().removeItem(is);
					}
				}
				if (player.getInventory().containsAtLeast(is2,leftover)){
					player.getInventory().removeItem(is2);
				}
				player.updateInventory();
				player.sendMessage(ChatColor.GREEN + "You successfully sold " + m.toString() + " for " + ChatColor.RED + "$"+ p);
				playerMoney = playerMoney + p;
			}
		}else {
			short damage = 0;
			ItemStack its = new ItemStack(Integer.parseInt(itemids[0]),1,damage,Byte.parseByte(itemids[1]));
			if (!player.getInventory().containsAtLeast(its, Integer.parseInt(lines[1]))){
				player.sendMessage(ChatColor.RED + "You don't have enough items!");
			}else{
				
			
			Material m = Material.getMaterial(Integer.parseInt(itemids[0]));
			int amount = Integer.parseInt(lines[1]);
			int leftover = amount % 64;
			amount = amount - leftover;
			int stacks = amount / 64;	
			int InvSpaceNeeded = stacks;
			if (leftover > 0){
				InvSpaceNeeded = InvSpaceNeeded + 1;
			}
			ItemStack is = new ItemStack(Integer.parseInt(itemids[0]),64,damage,Byte.parseByte(itemids[1]));
			ItemStack is2 = new ItemStack(Integer.parseInt(itemids[0]),leftover,damage,Byte.parseByte(itemids[1]));
			
			for (int i = 0; i < stacks; i++){
				if (player.getInventory().containsAtLeast(is,1)){
					player.getInventory().removeItem(is);
				}
			}
			if (player.getInventory().containsAtLeast(is2,leftover)){
				player.getInventory().removeItem(is2);
			}

			player.updateInventory();
			player.sendMessage(ChatColor.GREEN + "You successfully sold " + m.toString() + " for " + ChatColor.RED + "$"+ p);
			playerMoney = playerMoney + p;
			}	
		}
	}
	
	
	
	
	@SuppressWarnings("deprecation")
	public static boolean buySign(PlayerInteractEvent event){
		Block b = event.getClickedBlock();
		Player player = event.getPlayer();
		Sign s = (Sign) b.getState();
		String[] lines = s.getLines();
		NumberFormat format = NumberFormat.getCurrencyInstance();
		String price = lines[3];
		try {
			Number p = format.parse(price);
			
				String[] itemid = lines[2].split(":");
				if (itemid.length < 1 || itemid.length > 2){
					player.sendMessage(ChatColor.RED + "Something went wrong!");
					return true;
				}
				else if (itemid.length == 1){
					Material m = Material.getMaterial(Integer.parseInt(lines[2]));
					
					int amount = Integer.parseInt(lines[1]);
					int leftover = amount % 64;
					amount = amount - leftover;
					int stacks = amount / 64;	
					int InvSpaceNeeded = stacks;
					if (leftover > 0){
						InvSpaceNeeded = InvSpaceNeeded + 1;
					}
					if (player.getInventory().firstEmpty() == -1 || player.getInventory().firstEmpty() > (36-InvSpaceNeeded)){
						player.sendMessage(ChatColor.RED + "You do not have enough space in your inventory!");
						return true;
					}else{
						if (!PureHQEconomyManager.removeMoney(player.getName(), p.intValue())){
							player.sendMessage(ChatColor.RED + "You don't have enough money!");
							return true;
						}
						ItemStack is = new ItemStack(Integer.parseInt(lines[2]),64);
						ItemStack is2 = new ItemStack(Integer.parseInt(lines[2]),leftover);
						for (int i = 0; i < stacks; i++){
							player.getInventory().setItem(player.getInventory().firstEmpty(), is);
						}
						if (leftover != 0){
							player.getInventory().setItem(player.getInventory().firstEmpty(), is2);
						}
						player.sendMessage(ChatColor.GREEN + "You successfully bought " + m.toString() + " for " + ChatColor.RED + "$"+ p);
					}
					player.updateInventory();
				}
				else {
					short damage = 0;
					int amount = Integer.parseInt(lines[1]);
					int leftover = amount % 64;
					amount = amount - leftover;
					int stacks = amount / 64;	
					int InvSpaceNeeded = stacks;
					if (leftover > 0){
						InvSpaceNeeded = InvSpaceNeeded + 1;
					}
					if (player.getInventory().firstEmpty() == -1 || player.getInventory().firstEmpty() > (36-InvSpaceNeeded)){
						player.sendMessage(ChatColor.RED + "You do not have enough space in your inventory!");
					}else{
						ItemStack is = new ItemStack(Integer.parseInt(itemid[0]),64,damage,Byte.parseByte(itemid[1]));
						ItemStack is2 = new ItemStack(Integer.parseInt(itemid[0]),leftover,damage,Byte.parseByte(itemid[1]));
						for (int i = 0; i < stacks; i++){
							player.getInventory().setItem(player.getInventory().firstEmpty(), is);
						}
						player.getInventory().setItem(player.getInventory().firstEmpty(), is2);
						player.sendMessage(ChatColor.GREEN + "You successfully bought " + is.getType().toString() + " for " + ChatColor.RED + "$"+ p);
					}
					player.updateInventory();
				}
				
			

		} catch (Exception e) {
			player.sendMessage(ChatColor.RED + "Something went wrong!");
			e.printStackTrace();
		}
		return true;
	}
}
