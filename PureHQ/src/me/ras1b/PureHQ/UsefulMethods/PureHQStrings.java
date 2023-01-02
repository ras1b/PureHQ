package me.ras1b.PureHQ.UsefulMethods;

import org.bukkit.ChatColor;

public class PureHQStrings {
	//GENERAL
	public static final String NO_PERMISSION = ChatColor.RED + "You do not have permission to perform this command";
	public static final String WRONG_COMMAND = ChatColor.RED + "You entered the wrong command!";
	public static final String INVENTORY_NO_SPOT = ChatColor.RED + "You don't have a free spot in your inventory!";
	public static final String PLAYER_DOESNT_EXIST = ChatColor.RED + "This player does not exist!";
	public static final String RANK_DOESNT_EXIST = ChatColor.RED + "This rank does not exist!";
	//CRATES
	public static final String CRATE_ADDED = ChatColor.GREEN + "Successfully added crate " + ChatColor.RED + "{crate}";
	public static final String CRATE_EXISTS = ChatColor.RED + "This crate already exists!";
	public static final String CRATE_DOESNT_EXIST = ChatColor.RED + "This crate doesn't exists!";
	public static final String CRATE_REMOVED = ChatColor.GREEN + "Successfully removed crate " + ChatColor.RED + "{crate}";
	public static final String CRATE_SET = ChatColor.GREEN + "{crate}" + " crate successfully set!";
	public static final String CHEST_CRATE_EXISTS = ChatColor.RED + "This chest is already of type " + "{crate}";
	public static final String REMOVE_AIR = ChatColor.RED + "Cannot remove air!";
	public static final String ITEM_NOT_IN_CRATE = ChatColor.RED + "This crate doesn't contain that item!";
	public static final String ITEM_CRATE_REMOVAL = ChatColor.GREEN + "Successfully removed " + ChatColor.RED + "{item}";
	public static final String CRATE_GET_ITEM_INT = ChatColor.RED + "Please enter the position (number) of the item you want to retrieve!";
	public static final String POSITION_EMPTY = ChatColor.RED + "That position is empty!";
	public static final String CRATE_GET_ITEM = ChatColor.GREEN + "Successfully added item!";
	public static final String USAGE_CRATE_ADD_ITEM = ChatColor.RED + "Usage: crate <cratename> additem <percentage>";
	public static final String CRATE_SET_ITEM = ChatColor.GREEN + "Successfully added item to the crate!";
	public static final String CRATE_REMOVED_FAIL = ChatColor.RED + "You cannot remove a crate!";
	//KEYS
	public static final String PLAYER_ADD_KEY = ChatColor.GREEN + "Successfully added " + ChatColor.RED + "{amount}" + " " + "{crate}" + " key(s)" + ChatColor.GREEN + " to " + ChatColor.RED + "{player}";
	public static final String KEY_NAME = "�2"+"{crate}" + " �1key";
	//CLAIM
	
	
}
