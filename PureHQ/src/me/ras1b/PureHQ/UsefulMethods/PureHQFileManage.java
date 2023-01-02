package me.ras1b.PureHQ.UsefulMethods;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import me.ras1b.PureHQ.PureHQMain;

public class PureHQFileManage {
	
	public static HashMap<File, BufferedWriter> writers = new HashMap<File, BufferedWriter>();
	private PureHQMain plugin;
	public PureHQFileManage(PureHQMain main){
		this.plugin = main;
	}
	
	public void firstRun()throws Exception {
		if(!PureHQMain.playerDataFile.exists()){
			PureHQMain.playerDataFile.getParentFile().mkdirs();
            copy(plugin.getResource("/data/playerdata.yml"), PureHQMain.playerDataFile);
        }
		if(!PureHQMain.ranksFile.exists()){
			PureHQMain.ranksFile.getParentFile().mkdirs();
            copy(plugin.getResource("/data/ranks.yml"), PureHQMain.ranksFile);
        }
		if(!PureHQMain.cratesFile.exists()){
			PureHQMain.cratesFile.getParentFile().mkdirs();
            copy(plugin.getResource("/data/crates.yml"), PureHQMain.cratesFile);
        }
	}
	
	public static void loadYamls() {
        try {
        	PureHQMain.ranks.load(PureHQMain.ranksFile);
            PureHQMain.playerData.load(PureHQMain.playerDataFile);
            PureHQMain.crates.load(PureHQMain.cratesFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public static void copy(InputStream in, File file) {
	    try {
	        OutputStream out = new FileOutputStream(file);
	        byte[] buf = new byte[1024];
	        int len;
	        if (in != null){
	        	while((len=in.read(buf))>0){
		            out.write(buf,0,len);
		        }
	        	out.close();
		        in.close();
	        }	        	    
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	public static void saveYamls() {
	    try {
	        PureHQMain.ranks.save(PureHQMain.ranksFile);
	        PureHQMain.playerData.save(PureHQMain.playerDataFile);
	        PureHQMain.crates.save(PureHQMain.cratesFile);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}