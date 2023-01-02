package me.ras1b.PureHQ.Economy;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.ras1b.PureHQ.PureHQMain;

public class PureHQEconomyCommands {
	@SuppressWarnings("unused")
	private PureHQMain plugin;
	public PureHQEconomyCommands(PureHQMain main){
		this.plugin = main;
		
	}
	
	@SuppressWarnings("deprecation")
	public static boolean pay(CommandSender sender, String[] args){
		if (args.length == 2){
			String playername = args[0];
			String amountt = args[1];
			if (!amountt.matches("[0-9]+")){
				sender.sendMessage(ChatColor.RED + "Please enter a valid amount!");
				return true;
			}
			int amount = Integer.parseInt(amountt);
			boolean result = PureHQEconomyManager.removeMoney(sender.getName(), amount);
			if (!result){
				sender.sendMessage(ChatColor.RED + "You don't have enough money!");
				return true;
			}
			for (Player all : Bukkit.getServer().getOnlinePlayers()) {
			      if (all.getName().equals(playername)) {
			    	  PureHQEconomyManager.addMoney(playername, amount);
			    	  sender.sendMessage(ChatColor.GREEN + "Successfully paid "+ ChatColor.RED + "$" + amount + ChatColor.GREEN+" to " + ChatColor.RED + playername);
			    	  all.sendMessage(ChatColor.RED + sender.getName() + ChatColor.GREEN + " paid you " + ChatColor.RED + "$" + amount);
			    	  return true;
			      }    
			}
			sender.sendMessage(ChatColor.RED + "This player does not exist or is not online!");
			return true;
		}else{
			sender.sendMessage(ChatColor.RED + "Usage: pay player amount");
			return true;
		}
		
		
	}
	
	public static boolean withdraw(CommandSender sender, String[] args){
		Player player = (Player) sender;
		if (args.length > 1 || args.length == 0){
			sender.sendMessage(ChatColor.RED + "Usage: withdraw amount");
			return true;
		}else {
			String amountt = args[0];
			if (!amountt.matches("[0-9]+")){
				sender.sendMessage(ChatColor.RED + "You entered a wrong amount!");
				return true;
			}
			int slotFree = player.getInventory().firstEmpty();
			if (slotFree != -1){
				int amount = Integer.parseInt(amountt);
				boolean result = PureHQEconomyManager.removeMoney(player.getName(), amount);
				if (result){
					ItemStack is = new ItemStack(Material.PAPER,1);
					ItemMeta im = is.getItemMeta();
					im.setDisplayName("�e$" + amount);
					List<String> lore = new ArrayList<String>();
					lore.add("�9Rightclick to add to your balance!");
					im.setLore(lore);
					is.setItemMeta(im);
					player.getInventory().addItem(is);
					sender.sendMessage(ChatColor.GREEN + "You successfully withdrew "+ ChatColor.RED + "$" + amount);
				}
			}else{
				sender.sendMessage(ChatColor.RED + "You dont have enough inventory space!");
			}
			
		}
		
		return true;
	}
	
	public boolean bal(CommandSender sender, String[] args){
		Player player = (Player) sender;
		if (args.length == 0){
			int money = PureHQEconomyManager.checkMoney(player);
			player.sendMessage(ChatColor.GRAY +"Your balance is " + ChatColor.GREEN + "$" + money);
		}
		else if (args.length == 1 || args.length == 2){
			player.sendMessage(ChatColor.RED + "Please add an amount and playername to your command!");
		}
		else if (args.length == 3){
			if (!player.isOp()){
				player.sendMessage(ChatColor.RED + "You do not have permission to perform this command");
				return true;
			}
			if (args[0].equalsIgnoreCase("add")){
				PureHQEconomyCommands.baladd(sender, args);
			}else if (args[0].equalsIgnoreCase("remove")){
				PureHQEconomyCommands.balremove(sender, args);
			}else if (args[0].equalsIgnoreCase("set")){
				PureHQEconomyCommands.balset(sender, args);
			}else{
				player.sendMessage(ChatColor.RED + "Wrong command! Usage: balance add|remove|set amount");
			}
		}else{
			player.sendMessage(ChatColor.RED + "Wrong command! Usage: balance add|remove|set amount");
		}
		return true;
	}
	
	public static void baladd(CommandSender sender, String[] args){
		String playername = args[1];
		String amount = args[2];
		if (!amount.matches("[0-9]+")){
			sender.sendMessage(ChatColor.RED + "You entered a wrong amount!");
			return;
		}
		Object obj = PureHQMain.playerData.get("Name." + playername);
		if (obj == null){
			sender.sendMessage(ChatColor.RED + "This username does not exist!");
			return;
		}
		PureHQEconomyManager.addMoney(playername, Integer.parseInt(amount));
		
		sender.sendMessage(ChatColor.GREEN + "You successfully added " +ChatColor.RED + "$"+amount + ChatColor.GREEN + " to " + ChatColor.RED +playername);
	}
	
	public static void balremove(CommandSender sender, String[] args){
		String playername = args[1];
		String amount = args[2];
		if (!amount.matches("[0-9]+")){
			sender.sendMessage(ChatColor.RED + "You entered a wrong amount!");
			return;
		}
		Object obj = PureHQMain.playerData.get("Name." + playername);
		if (obj == null){
			sender.sendMessage(ChatColor.RED + "This username does not exist!");
			return;
		}
			if (PureHQEconomyManager.removeMoney(playername, Integer.parseInt(amount))){
				sender.sendMessage(ChatColor.GREEN + "You successfully removed " +ChatColor.RED + "$"+amount + ChatColor.GREEN + " from " + ChatColor.RED +playername);
			}else{
				sender.sendMessage(ChatColor.RED + "This user does not have enough money!");
			}
		
			
	}
	public static void balset(CommandSender sender, String[] args){
		String playername = args[1];
		String amount = args[2];
		if (!amount.matches("[0-9]+")){
			sender.sendMessage(ChatColor.RED + "You entered a wrong amount!");
			return;
		}
		Object obj = PureHQMain.playerData.get("Name." + playername);
		if (obj == null){
			sender.sendMessage(ChatColor.RED + "This username does not exist!");
			return;
		}
		PureHQMain.playerData.set("Name."+sender.getName()+".money", Integer.parseInt(amount));
		sender.sendMessage(ChatColor.GREEN + "You successfully set " +ChatColor.RED + "$"+amount + ChatColor.GREEN + " as balance for " + ChatColor.RED +playername);

	}
}
