package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import SQLHelper.FileList;
import vo.PlayerVO;

public class PlayerOwingTeamData {
	Map<String, String> playerTeam=new HashMap<String, String>();
	Map<String, PlayerVO> playerActive=new HashMap<String, PlayerVO>();
	Map<String, PlayerVO> playerHistoric=new HashMap<String, PlayerVO>();
	
	private void baseInfoInitActive() {
		try {
			FileList fl = new FileList("src/data/players/info/active");
			ArrayList<String> names = fl.getList();
			for (String name : names) {
				readActiveInfoFromFile(name);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void readActiveInfoFromFile(String fileName) {
		PlayerVO player;
		String name;
		String number;
		String position;
		String height;
		int weight;
		String birth;
		int age;
		int exp;
		String school;
		String[] content = new String[9];
		try {
			File file = new File(fileName);
			if (!file.exists()) {

				file.createNewFile();
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "UTF-8"));
			int i = 0;
			String temp = null;
			temp = br.readLine();
			while (temp != null) {
				String[] nit = temp.split(":");
				if (nit.length == 1) {
					content[i++] = "";
				} else {
					content[i++] = nit[1].trim();
				}
				temp = br.readLine();
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		name = content[0];
		number = content[1];
		position = content[2];
		height = content[3];
		if (content[4].equals("")) {
			weight = 0;
		} else {
			weight = Integer.parseInt(content[4]);
		}
		birth = content[5];
		if (content[6].equals("")) {
			age = 0;
		} else {
			age = Integer.parseInt(content[6]);
		}
		if (content[7].equals("")) {
			exp = 0;
		} else {
			exp = Integer.parseInt(content[7]);
		}
		school = content[8];
		player = new PlayerVO(name, number, position, height, weight, birth,
				age, exp, school);
		playerActive.put(name, player);
	}
	
	private void baseInfoInitHistoric() {
		try {
			FileList fl = new FileList("src/data/players/info/historic");
			ArrayList<String> names = fl.getList();
			for (String name : names) {
				readHistoricInfoFromFile(name);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void readHistoricInfoFromFile(String fileName) {
		PlayerVO player;
		String name;
		String number;
		String position;
		String height;
		int weight;
		String birth;
		int age;
		int exp;
		String school;
		String[] content = new String[9];
		try {
			File file = new File(fileName);
			if (!file.exists()) {

				file.createNewFile();
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "UTF-8"));
			int i = 0;
			String temp = null;
			temp = br.readLine();
			while (temp != null) {
				String[] nit = temp.split(":");
				if (nit.length == 1) {
					content[i++] = "";
				} else {
					content[i++] = nit[1].trim();
				}
				temp = br.readLine();
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		name = content[0];
		number = content[1];
		position = content[2];
		height = content[3];
		if (content[4].equals("")) {
			weight = 0;
		} else {
			weight = Integer.parseInt(content[4]);
		}
		birth = content[5];
		if (content[6].equals("")) {
			age = 0;
		} else {
			age = Integer.parseInt(content[6]);
		}
		if (content[7].equals("")) {
			exp = 0;
		} else {
			exp = Integer.parseInt(content[7]);
		}
		school = content[8];
		player = new PlayerVO(name, number, position, height, weight, birth,
				age, exp, school);
		playerHistoric.put(name, player);
	}
	
	
	
	private void readInfoFromOwingTeamFile() {
		String name;
		String owingTeam;
		try {
			File file = new File("src/data/players/playerTeam.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "UTF-8"));
			String temp = null;
			temp = br.readLine();
			while (temp != null) {
				String[] nit=temp.split(",");
				name=nit[0];
				if(nit.length==1){
					owingTeam="";
				}
				else{
					owingTeam=nit[1];
				}
				playerTeam.put(name, owingTeam);
				temp = br.readLine();
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void doIt(){
		baseInfoInitActive();
		baseInfoInitHistoric();
		readInfoFromOwingTeamFile();
		Iterator<Entry<String, String>> iter = playerTeam.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String name=(String)entry.getKey();
			playerTeamWriteToActiveFile(name);
			playerTeamWriteToHistoricFile(name);
		}
	}
	
	public void playerTeamWriteToActiveFile(String name){
		try{
			
			File file=new File("src/data/players/info/active/"+name+".txt");
	        if(!file.exists()){   	
//	        	System.out.println("active lost "+name+"!!!");	        	
	        	return;
	        }
	        
	        BufferedWriter bw = null;
			try {
				bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			String team=playerTeam.get(name);
			bw.write("OwingTeam:"+team+ "\r\n");
			bw.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public void playerTeamWriteToHistoricFile(String name){
		try{
			
			File file=new File("src/data/players/info/historic/"+name+".txt");
	        if(!file.exists()){   	
	        	System.out.println("historic lost "+name+"!!!");	        	
	        	return;
	        }
	        
	        BufferedWriter bw = null;
			try {
				bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			String team=playerTeam.get(name);
			bw.write("OwingTeam:"+team+ "\r\n");
			bw.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		PlayerOwingTeamData players=new PlayerOwingTeamData();
		players.doIt();
	}
}
