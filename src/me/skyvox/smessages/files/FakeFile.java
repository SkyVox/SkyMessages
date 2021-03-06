package me.skyvox.smessages.files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import me.skyvox.smessages.Sky;

public class FakeFile {
	static Plugin p;
	static FileConfiguration language;
	static File pdfile;
	 
	public static void setup() {
		pdfile = new File(Sky.getInstance().getDataFolder(), "fake.yml");
		if (!pdfile.exists()) {
			try {
				pdfile.getParentFile().mkdirs();
				pdfile.createNewFile();
				copy(Sky.getInstance().getResource("fake.yml"), pdfile);
			} catch (IOException e) {
				Bukkit.getServer().getLogger().severe("Could not create fake.yml!");
			}
		}
		language = YamlConfiguration.loadConfiguration(pdfile);
	}
	 
	public static FileConfiguration get() {
		return language;
	}
	 
	public static void save() {
		try {
			language.save(pdfile);
		} catch (IOException e) {
			Bukkit.getServer().getLogger().severe("Could not save fake.yml!");
		}
	}
	 
	public static void reload() {
		language = YamlConfiguration.loadConfiguration(pdfile);
	}
	
	private static void copy(InputStream in, File file) {
	    try {
	        OutputStream out = new FileOutputStream(file);
	        byte[] buf = new byte[1024];
	        int len;
	        while((len=in.read(buf))>0){
	            out.write(buf,0,len);
	        }
	        out.close();
	        in.close();
	    } catch (Exception exception) {
	    	exception.printStackTrace();
	    }
	}
}