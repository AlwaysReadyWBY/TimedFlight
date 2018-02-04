package com.github.alwaysreadywby.timedflight;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config extends YamlConfiguration{
	
	private static final int configVersion=0;
	private static final int langVersion=0;
	private static final int dataVersion=0;
	
	private static Config config;
	private static Config lang;
	private static Config data;
	
	private String path;
	private int version;
	private boolean isLoaded;
	
	private Config() {
		isLoaded=false;
	}
	
	public static void init() {
		reloadPlugConfig();
		reloadLangConfig();
		reloadPlayerData();
	}
	
	public static void reloadPlugConfig() {
		config=new Config();
		config.path="config.yml";
		config.version=configVersion;
		boolean goodLoad=config.load();
		config.writeConfig("lang", "cn", false);
		if(!goodLoad) config.save();
	}
	
	public static void reloadLangConfig() {
		lang=new Config();
		if(config.contains("lang")) {
			lang.path="lang_"+config.getString("lang")+".yml";
		}else {
			lang.path="lang_cn.yml";
		}
		lang.version=langVersion;
		boolean goodLoad=lang.load();
		lang.writeConfig("title", "&a====飞行时限====",false);
		lang.writeConfig("usage.timeformat1", "&e填写时间时需要在末尾添加单位,例如:24h", false);
		lang.writeConfig("usage.timeformat2", "&e可用的单位：t,s,m,h,d,M,y", false);
		lang.writeConfig("usage.timeformat3", "&e对应的时间：刻,秒,分,时,天,月,年", false);
		lang.writeConfig("usage.help", "&e查看全部/tf指令",false);
		lang.writeConfig("usage.version", "&e显示插件版本",false);
		lang.writeConfig("usage.remain", "<玩家> &e显示当前剩余飞行时长",false);
		lang.writeConfig("usage.give", "[玩家] [时间] &e给予玩家时长",false);
		lang.writeConfig("usage.remove", "[玩家] <时间> &e移除玩家时长",false);
		lang.writeConfig("usage.set", "[玩家] [时间] &e设置玩家时长",false);
		lang.writeConfig("usage.use", "&e开启时限飞行",false);
		lang.writeConfig("usage.stop", "&e停止时限飞行",false);
		lang.writeConfig("usage.reload", "&e重载插件",false);
		lang.writeConfig("ver.version", "&a版本号&ev",false);
		lang.writeConfig("ver.author", "&a作者",false);
		lang.writeConfig("ver.mail", "&a如有漏洞请发送邮件至&e%mail%&a联系作者",false);
		lang.writeConfig("msg.reload", "&a飞行时限插件已重载",false);
		lang.writeConfig("msg.player-only", "&c该指令只能由玩家使用",false);
		lang.writeConfig("msg.no-permission", "&c你没有这样做的权限",false);
		lang.writeConfig("msg.invalid-arg-length", "&c无效的参数长度,请使用&e/tf help&c查看帮助",false);
		lang.writeConfig("msg.invalid-time-format", "&c无效的时间格式,请使用&e/tf help&c查看帮助",false);
		lang.writeConfig("msg.use.start", "&a已开启飞行", false);
		lang.writeConfig("msg.use.stop", "&c已关闭飞行", false);
		lang.writeConfig("msg.time.insufficient", "&c飞行时限不足", false);
		lang.writeConfig("msg.time.given", "&a已给予玩家&e%p%&a飞行时长&e%t%", false);
		lang.writeConfig("msg.time.removed", "&a已移除玩家&e%p%&a飞行时长&e%t%", false);
		lang.writeConfig("msg.time.cleared", "&a已清除玩家&e%p%&a飞行时长", false);
		lang.writeConfig("msg.time.set", "&a已设置玩家&e%p%&a飞行时长为&e%t%", false);
		lang.writeConfig("msg.time.remain", "&a玩家&e%p%&a拥有飞行时长%t%", false);
		lang.writeConfig("msg.error.load", "&c飞行时限:未能加载文件", false);
		lang.writeConfig("msg.error.save", "&c飞行时限:未能保存文件", false);
		lang.writeConfig("msg.error.lang", "&c飞行时限:语言文件丢失", false);
		lang.writeConfig("time.second", "秒", false);
		lang.writeConfig("time.minute", "分", false);
		lang.writeConfig("time.hour", "时", false);
		lang.writeConfig("time.day", "天", false);
		lang.writeConfig("time.month", "月", false);
		lang.writeConfig("time.year", "年", false);
		lang.writeConfig("time.tick", "tick", false);
		if(!goodLoad) lang.save();
	}
	
	public static void reloadPlayerData() {
		data=new Config();
		data.path="playerdata.yml";
		data.version=dataVersion;
		boolean goodLoad=data.load();
		if(!goodLoad) data.save();
	}
	
	public static String getTimeRemaining(String player) {
		StringBuilder ret=new StringBuilder();
		long time=getTickRemaining(player);
		if(time<0) {
			return "0";
		}
		if(time>TimedFlight.TICK_YEAR) {
			ret.append(Long.toString(time/TimedFlight.TICK_YEAR));
			ret.append(Config.getLang("time.year"));
			time=time%TimedFlight.TICK_YEAR;
		}
		if(time>TimedFlight.TICK_MONTH) {
			ret.append(Long.toString(time/TimedFlight.TICK_MONTH));
			ret.append(Config.getLang("time.month"));
			time=time%TimedFlight.TICK_MONTH;
		}
		if(time>TimedFlight.TICK_DAY) {
			ret.append(Long.toString(time/TimedFlight.TICK_DAY));
			ret.append(Config.getLang("time.day"));
			time=time%TimedFlight.TICK_DAY;
		}
		if(time>TimedFlight.TICK_HOUR) {
			ret.append(Long.toString(time/TimedFlight.TICK_HOUR));
			ret.append(Config.getLang("time.hour"));
			time=time%TimedFlight.TICK_HOUR;
		}
		if(time>TimedFlight.TICK_MINUTE) {
			ret.append(Long.toString(time/TimedFlight.TICK_MINUTE));
			ret.append(Config.getLang("time.minute"));
			time=time%TimedFlight.TICK_MINUTE;
		}
		if(time>TimedFlight.TICK_SECOND) {
			ret.append(Long.toString(time/TimedFlight.TICK_SECOND));
			ret.append(Config.getLang("time.second"));
			time=time%TimedFlight.TICK_SECOND;
		}
		if(time>0) {
			ret.append(Long.toString(time));
			ret.append(Config.getLang("time.tick"));
		}
		return ret.toString();
	}
	
	public static long getTickRemaining(String player) {
		if(data.contains(player)) {
			return data.getLong(player);
		}else {
			return 0;
		}
	}
	
	public static boolean reduceTickRemaining(String player,long duration) {
		if(data.contains(player)) {
			long remain=data.getLong(player)-duration;
			if(remain>0) {
				data.writeConfig(player, remain ,true);
				return true;
			}else {
				data.writeConfig(player, 0, true);
				return false;
			}
		}
		return false;
	}
	
	public static void addTickRemaining(String player,long duration) {
		if(data.contains(player)) {
			data.writeConfig(player, Math.max(data.getLong(player)+duration,0) ,true);
		}else {
			data.writeConfig(player, duration, true);
		}
	}
	
	public static void setTickRemaining(String player,long duration) {
		data.writeConfig(player, duration, true);
	}

	public void writeConfig(String path,Object data,boolean overWrite) {
		if(overWrite || !contains(path)) {
			set(path, data);
		}
	}
	
	public boolean load() {
		boolean ret=true;
		File file = new File(TimedFlight.getPlug().getDataFolder(), path);
		try {
			if (!file.exists()) {
	    		file.createNewFile();
	    		reset(false);
	    		ret=false;
	    	}else {
	    		load(file);
	    	}
			if(!contains("version") || version!=getInt("version")) {
				reset(true);
				ret=false;
			}
		}catch(Exception e) {
			e.printStackTrace();
			MsgMgr.informLoadError(path);
			return isLoaded=false;
		}
		isLoaded=true;
		return ret;
	}

	public void reset(boolean saveBackup) {
		File tmp=new File(TimedFlight.getPlug().getDataFolder(),path);
		if(tmp.exists() && saveBackup) {
			File bak=new File(TimedFlight.getPlug().getDataFolder(),path+".bak");
			if(bak.exists()) bak.delete();
			tmp.renameTo(bak);
			tmp.delete();
		}else {
			if(tmp.exists()) tmp.delete();
			try {
				tmp.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				MsgMgr.informSaveError(path);
				e.printStackTrace();
			}
		}
		writeConfig("version",version,true);
	}
	
	public void save() {
		File saveFile=new File(TimedFlight.getPlug().getDataFolder(),path);
		try {
			save(saveFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MsgMgr.informSaveError(path);
		}
	}

	public static String getLang(String path){
		if(lang !=null && lang.contains(path)) {
			return lang.getString(path).replace('&', '§');
		}else {
			if(lang!=null){
				MsgMgr.informLangError(path);
			}
			return "["+path+"]=?";
		}
	}

	public static void saveAll() {
		// TODO Auto-generated method stub
		config.save();
		data.save();
	}

}
