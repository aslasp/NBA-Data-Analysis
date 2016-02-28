package newui.playerui;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;


public class MediaTableModel  extends MyTableModel {
	
	 Vector<String> columnNames=new Vector<String>();
	 ArrayList<ArrayList<Object>> rowData=new ArrayList<ArrayList<Object>>();
	 ArrayList<String> names=new ArrayList<String>();
	
	public MediaTableModel(){

		columnNames.add("球队");
		columnNames.add("时间");
		columnNames.add("参与球员");
		columnNames.add("文字直播");
        
		ArrayList<Object> a1=new ArrayList<Object>();
		a1.add("");
		a1.add("00:00.0");
		a1.add("");
		a1.add("本节比赛结束");
		names.add("");
//		Object[] a=new Object[]{"","00:00.0",new Object(),"本节比赛结束"};
		ImageIcon tou=new ImageIcon("aa");
		tou.setImage(tou.getImage().getScaledInstance(50, 40, Image.SCALE_DEFAULT));
		
		ArrayList<Object> a2=new ArrayList<Object>();
		a2.add("勇士");
		a2.add("00:17.8");
		a2.add("斯蒂芬-库里");
		a2.add("[勇士 104-90] 库里 三次罚球第三罚中 （26分）");
		names.add("斯蒂芬-库里");
//		Object[] b=new Object[]{"勇士","00:17.8",tou,"[勇士 104-90] 库里 三次罚球第三罚中 （26分）"};
		rowData.add(a1);
		rowData.add(a2);
	}
	
	public Class getColumnClass(int c) {
		return rowData.get(0).get(c).getClass();
	}
	
//	得到共有多少列
	public int getColumnCount() {
		 int length=columnNames.size();
		return length;
	}
	

    public void setValueAt(Object value, int row, int column) {
		rowData.get(row).set(column, value);
	}
//得到共有多少行
	public int getRowCount() {
		//rowData=new Vector<String[]>();
		
		int length=rowData.size();
		return length;
	}
//得到某行某列的数据
	public Object getValueAt(int rowIndex, int columnIndex) {
		ArrayList<Object> buffer=rowData.get(rowIndex);
		Object it=buffer.get(columnIndex);
		return it;
	}
//得到某列的名字	
	public String getColumnName(int columnIndex){
		
		String columnName=columnNames.get(columnIndex);
		return columnName;
	}
	
	public void addRow(ArrayList<Object> hang){
		rowData.add(hang);
	}
	
	public ArrayList<String> getNameList(){
		return names;
	}

	public void clearAll(){
		rowData.clear();
	}
}
