package newui.teamui;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JTable;

public class BaseInfoTeamThread extends Thread{
	
	JTable table;
	//往表格第一列中加数据
	ArrayList<String> name;
	public BaseInfoTeamThread(ArrayList<String> name,JTable t){
		this.name=name;
		this.table=t;
	}
	
	public void run(){
	
		for(int i=0;i<name.size();i++){
			String s=name.get(i);
			ImageIcon tou=new ImageIcon("image/teamIcon/teamsPng90/"+s+".png");
			tou.setImage(tou.getImage().getScaledInstance(
					80, 80
				, Image.SCALE_DEFAULT));
			table.setValueAt(tou, i, 0);
			
			
			
		}
	
		
	}
	
	
	

}
