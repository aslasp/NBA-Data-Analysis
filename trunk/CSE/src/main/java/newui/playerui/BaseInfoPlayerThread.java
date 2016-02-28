package newui.playerui;

import java.awt.Image;
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
			ImageIcon tou=new ImageIcon("image/player/portrait/"+s+".png");
			tou.setImage(tou.getImage().getScaledInstance(
					50, 40
				, Image.SCALE_DEFAULT));
			table.setValueAt(tou, i, 0);
			
			
		}
	
		
	}
	
	
	
	
	
}
