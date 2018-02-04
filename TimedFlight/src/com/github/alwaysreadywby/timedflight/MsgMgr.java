package com.github.alwaysreadywby.timedflight;

import java.util.logging.Logger;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MsgMgr {
	
	public static void inform(String msg) {
		TimedFlight.getPlug().getLogger().info(msg);
	}

	public static void informLoadError(String path) {
		// TODO Auto-generated method stub
		inform(Config.getLang("msg.error.load")+path);
	}

	public static void informSaveError(String path) {
		// TODO Auto-generated method stub
		inform(Config.getLang("msg.error.save")+path);
	}

	public static void informLangError(String path) {
		// TODO Auto-generated method stub
		inform(Config.getLang("msg.error.save")+path);
	}
	
	public static void sendVerMsg(CommandSender sender) {
		// TODO Auto-generated method stub
		sender.sendMessage(Config.getLang("title"));
		sender.sendMessage(Config.getLang("ver.version")+TimedFlight.getPlug().getDescription().getVersion());
		sender.sendMessage(Config.getLang("ver.author")+"§eAlwaysReadyWBY");
		sender.sendMessage(Config.getLang("ver.mail").replaceAll("%mail%", "wby27_2006@126.com或alwaysreadywby@gmail.com"));
	}

	public static void sendHelpMsg(CommandSender sender) {
		// TODO Auto-generated method stub
		sender.sendMessage(Config.getLang("title"));
		sender.sendMessage(Config.getLang("usage.timeformat1"));
		sender.sendMessage(Config.getLang("usage.timeformat2"));
		sender.sendMessage(Config.getLang("usage.timeformat3"));
		sender.sendMessage("§a/tf remain "+Config.getLang("usage.remain"));
		sender.sendMessage("§a/tf use "+Config.getLang("usage.use"));
		sender.sendMessage("§a/tf stop "+Config.getLang("usage.stop"));
		sender.sendMessage("§a/tf help: "+Config.getLang("usage.help"));
		sender.sendMessage("§a/tf give "+Config.getLang("usage.give"));
		sender.sendMessage("§a/tf remove "+Config.getLang("usage.remove"));
		sender.sendMessage("§a/tf set "+Config.getLang("usage.set"));
		sender.sendMessage("§a/tf version: "+Config.getLang("usage.version"));
		sender.sendMessage("§a/tf reload: "+Config.getLang("usage.reload"));
	}

	public static void sendMsg(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		sender.sendMessage(args);
	}

	public static void sendMsg(CommandSender sender, String str) {
		// TODO Auto-generated method stub
		sender.sendMessage(str);
	}
}
