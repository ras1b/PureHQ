package me.ras1b.PureHQ.Economy;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;

import me.ras1b.PureHQ.PureHQMain;


public class PureHQSignListener implements Listener{
	@SuppressWarnings("unused")
	private PureHQMain plugin;
	
    public PureHQSignListener(PureHQMain plugin) {
        this.plugin = plugin;
    }
	
	
	@EventHandler
	public void playerSignEvent(SignChangeEvent event) {
		if (event.getLine(0).equalsIgnoreCase("[Buy]")){
			PureHQSignListener.buySign(event);
		}
		else if (event.getLine(0).equalsIgnoreCase("[Sell]")){
			PureHQSignListener.sellSign(event);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void sellSign(SignChangeEvent event){
		if (!event.getPlayer().isOp()){
			event.getPlayer().sendMessage(ChatColor.RED + "You don't have permission to make a sell sign!");
			event.setCancelled(true);
		}
		else{
			String amount = event.getLine(1);
			String price = event.getLine(3);
			String itemid = event.getLine(2);
			String[] itemids = itemid.split(":");
			if (price.matches("[0-9]+") && price.length() > 0){
				if (itemid.matches("[0-9,/:]+") && itemid.length() > 0){
					if (itemids.length == 1){
						try {
							Material m = Material.getMaterial(Integer.parseInt(itemids[0]));
							if (amount.matches("[0-9]+") && Integer.parseInt(amount) > 0 && Integer.parseInt(amount) < 2305){
								event.setLine(0, "�1[Sell]");
								event.setLine(1, amount);
								event.setLine(3, "$" + price);
								event.setLine(2, m.getId()+"");
							}
							else{
								event.getPlayer().sendMessage(ChatColor.RED + "Enter a valid amount on row 2.");
								event.setCancelled(true);
							}
							
						}catch(Exception e){
							event.getPlayer().sendMessage(ChatColor.RED + "Enter a valid id on row 3.");
							event.setCancelled(true);
						}
					}
					else if (itemids.length == 2){
						try {
							short damage = 0;
							ItemStack is = new ItemStack(Integer.parseInt(itemids[0]),1,damage,Byte.parseByte(itemids[1]));
							if (is != null){
								if (amount.matches("[0-9]+") && amount.length() > 0 && Integer.parseInt(amount) < 2305){
									event.setLine(0, "�1[Sell]");
									event.setLine(1, amount);
									event.setLine(3, "$" + price);
									event.setLine(2, is.getTypeId() + ":"+is.getData().getData());
								}
								else{
									event.getPlayer().sendMessage(ChatColor.RED + "Enter a valid amount on row 2.");
									event.setCancelled(true);
								}
							}
						}catch(Exception e){
							event.getPlayer().sendMessage(ChatColor.RED + "Enter a valid id on row 3.");
							event.setCancelled(true);
						}
					}
					else{
						event.getPlayer().sendMessage(ChatColor.RED + "Enter a valid id on row 3.");
						event.setCancelled(true);
					}
					
				}
			}else{
				event.getPlayer().sendMessage(ChatColor.RED + "Enter a valid price");
				event.setCancelled(true);
			}
		}
	}
	
	
	
	
	
	
	
	@SuppressWarnings("deprecation")
	public static void buySign(SignChangeEvent event){
		if (!event.getPlayer().isOp()){
			event.getPlayer().sendMessage(ChatColor.RED + "You don't have permission to make a buy sign!");
			event.setCancelled(true);
		}
		else{
			String amount = event.getLine(1);
			String row2 = event.getLine(3);
			String row3 = event.getLine(2);
			if (row2.matches("[0-9]+") && row2.length() > 0){
				if (row3.matches("[0-9,/:]+") && row3.length() > 0){
					String[] itemids = row3.split(":");
					
					if (itemids.length == 1){
						try {
							Material m = Material.getMaterial(Integer.parseInt(itemids[0]));
							if (amount.matches("[0-9]+") && amount.length() > 0 && Integer.parseInt(amount) < 2305){
								event.setLine(0, "�1[Buy]");
								event.setLine(1, amount);
								event.setLine(3, "$" + row2);
								event.setLine(2, m.getId()+"");
							}
							else{
								event.getPlayer().sendMessage(ChatColor.RED + "Enter a valid amount on row 2.");
								event.setCancelled(true);
							}
							
						}catch(Exception e){
							event.getPlayer().sendMessage(ChatColor.RED + "Enter a valid id on row 3.");
							event.setCancelled(true);
						}
					}
					else if (itemids.length == 2){
						try {
							short damage = 0;
							ItemStack is = new ItemStack(Integer.parseInt(itemids[0]),1,damage,Byte.parseByte(itemids[1]));
							if (is != null){
								if (amount.matches("[0-9]+") && amount.length() > 0 && Integer.parseInt(amount) < 2305){
									event.setLine(0, "�1[Buy]");
									event.setLine(1, amount);
									event.setLine(3, "$" + row2);
									event.setLine(2, is.getTypeId() + ":"+is.getData().getData());
								}
								else{
									event.getPlayer().sendMessage(ChatColor.RED + "Enter a valid amount on row 2.");
									event.setCancelled(true);
								}
							}
						}catch(Exception e){
							event.getPlayer().sendMessage(ChatColor.RED + "Enter a valid id on row 3.");
							event.setCancelled(true);
						}
					}
					else{
						event.getPlayer().sendMessage(ChatColor.RED + "Enter a valid id on row 3.");
						event.setCancelled(true);
					}
				}
				else{
					event.getPlayer().sendMessage(ChatColor.RED + "Enter a valid id on row 3.");
					event.setCancelled(true);
				}
			}
			else{
				event.getPlayer().sendMessage(ChatColor.RED + "Enter a price on row 4.");
				event.setCancelled(true);
			}
		}
	}
}
