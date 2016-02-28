package newui.playerui;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JTable;

//球员过多 用于加载球员图片
public class BaseInfoPlayerThread extends Thread{

	JTable table;
	//往表格第一列中加数据
	ArrayList<String> name;
	public BaseInfoPlayerThread(ArrayList<String> name,JTable t){
		this.name=name;
		this.table=t;
	}
	
	public void run(){
	
		for(int i=0;i<name.size();i++){
			String s=name.get(i);
			File f=new File("src/data/players/portrait/"+s+".png");
			ImageIcon tou;
			if(!f.exists())
				tou=new ImageIcon("src/data/players/portrait/None.png");
			else
                tou=new ImageIcon("src/data/players/portrait/"+s+".png");
			tou.setImage(tou.getImage().getScaledInstance(
					50, 40
				, Image.SCALE_SMOOTH));
			table.setValueAt(tou, i, 0);
			
			
		}
	
		
	}
	
	
	
	
	
}
