package com.github.alwaysreadywby.timedflight;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CmdMgr {
	
	public static boolean onCommandReceive(CommandSender sender, Command command, String label, String[] args) {
		String cmd=command.getName();
		if(cmd.equalsIgnoreCase("tf") || cmd.equalsIgnoreCase("timedflight")) {
			boolean isPlayer=sender instanceof Player;
			if(args.length==0 || args[0].equalsIgnoreCase("help") || args[0].equals("?")) {
				onCommandHelp(sender,args);
			}else if(args[0].equalsIgnoreCase("version")) {
				if(checkPerm(sender,"timedflight.admin")) {
					onCommandVersion(sender,args);
				}
			}else if(args[0].equalsIgnoreCase("reload")) {
				if(checkPerm(sender,"timedflight.admin")) {
					onCommandReload(sender,args);
				}
			}else if(args[0].equalsIgnoreCase("give")) {
				if(checkPerm(sender,"timedflight.admin")) {
					onCommandGive(sender,args);
				}
			}else if(args[0].equalsIgnoreCase("set")) {
				if(checkPerm(sender,"timedflight.admin")) {
					onCommandSet(sender,args);
				}
			}else if(args[0].equalsIgnoreCase("remove")) {
				if(checkPerm(sender,"timedflight.admin")) {
					onCommandRemove(sender,args);
				}
			}else if(args[0].equalsIgnoreCase("use")) {
				if(checkPerm(sender,"timedflight.use")) {
					onCommandUse(sender,args);
				}
			}else if(args[0].equalsIgnoreCase("stop")) {
				if(checkPerm(sender,"timedflight.use")) {
					onCommandStop(sender,args);
				}
			}else if(args[0].equalsIgnoreCase("remain")) {
				if(checkPerm(sender,"timedflight.use")) {
					onCommandRemain(sender,args);
				}
			}else {
				onCommandHelp(sender,args);
			}
			return true;
		}
		return false;
	}

	private static boolean checkPerm(CommandSender sender,String perm) {
		// TODO Auto-generated method stub
		if(sender.hasPermission(perm)) {
			return true;
		}else {
			MsgMgr.sendMsg(sender,Config.getLang("msg.no-permission"));
			return false;
		}
	}
	
	private static boolean checkPlayer(CommandSender sender) {
		// TODO Auto-generated method stub
		if(sender instanceof Player) {
			return true;
		}else {
			MsgMgr.sendMsg(sender, Config.getLang("msg.player-only"));
			return false;
		}
	}

	private static void onCommandReload(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		TimedFlight.getPlug().onReload();
		MsgMgr.sendMsg(sender,Config.getLang("msg.reload"));
	}

	private static void onCommandVersion(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		MsgMgr.sendVerMsg(sender);
	}

	private static void onCommandHelp(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		MsgMgr.sendHelpMsg(sender);
	}
	
	private static void onCommandUse(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		if(checkPlayer(sender)) {
			Player p=(Player) sender;
			if(Config.getTickRemaining(p.getName())>0 || p.hasPermission("timedflight.bypass")) {
				p.setAllowFlight(true);
				p.setMetadata("timedflight.lastchecked", new LastCheckedMeta(p.getWorld().getFullTime()));
				MsgMgr.sendMsg(p, Config.getLang("msg.use.start"));
			}else {
				MsgMgr.sendMsg(sender, Config.getLang("msg.time.insufficient"));
			}
		}
	}

	private static void onCommandStop(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		if(checkPlayer(sender)) {
			Player p=(Player) sender;
			if(p.isFlying()) p.setFallDistance(-255);
			p.setAllowFlight(false);
			MsgMgr.sendMsg(p, Config.getLang("msg.use.stop"));
		}
	}
	
	private static void onCommandGive(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		if(args.length==3) {
			Config.addTickRemaining(args[1], TimedFlight.getTimeFor(args[2]));
			MsgMgr.sendMsg(sender, Config.getLang("msg.time.given").replace("%p%", args[1]).replace("%t%", args[2]));
		}else {
			MsgMgr.sendMsg(sender, Config.getLang("msg.invalid-arg-length"));
		}
	}
	
	private static void onCommandRemove(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		if(args.length==2) {
			Config.setTickRemaining(args[1], 0);
			MsgMgr.sendMsg(sender, Config.getLang("msg.time.cleared").replace("%p%",args[1]));
		}else if(args.length==3) {
			Config.reduceTickRemaining(args[1], TimedFlight.getTimeFor(args[2]));
			MsgMgr.sendMsg(sender, Config.getLang("msg.time.removed").replace("%p%",args[1]).replace("%t%", args[2]));
		}else {
			MsgMgr.sendMsg(sender, Config.getLang("msg.invalid-arg-length"));
		}
		
	}
	
	private static void onCommandSet(CommandSender sender, String[] args) {
		if(args.length==3) {
			Config.setTickRemaining(args[1], TimedFlight.getTimeFor(args[2]));
			MsgMgr.sendMsg(sender, Config.getLang("msg.time.set").replace("%p%",args[1]).replace("%t%", Config.getTimeRemaining(args[1])));
		}else {
			MsgMgr.sendMsg(sender, Config.getLang("msg.invalid-arg-length"));
		}
	}
	
	private static void onCommandRemain(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		if(args.length==1) {
			if(checkPlayer(sender)) {
				MsgMgr.sendMsg(sender, Config.getLang("msg.time.remain").replace("%p%",sender.getName()).replace("%t%", Config.getTimeRemaining(sender.getName())));
			}
		}else if(args.length==2) {
			MsgMgr.sendMsg(sender, Config.getLang("msg.time.remain").replace("%p%",args[1]).replace("%t%", Config.getTimeRemaining(args[1])));
		}else {
			MsgMgr.sendMsg(sender, Config.getLang("msg.invalid-arg-length"));
		}
	}
}
