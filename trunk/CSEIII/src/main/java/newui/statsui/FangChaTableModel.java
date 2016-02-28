package newui.statsui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class FangChaTableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String[][] content=new String[3][7];
	String[] title={"方差来源","偏差平方和","自由度","方差","F值","Fα","显著性"};
	public FangChaTableModel(ArrayList<String> list) {
		content[0][0]=list.get(0);
		content[0][1]=list.get(3);
		content[0][2]=list.get(4);
		content[0][3]=list.get(5);
		content[0][4]=list.get(6);
		content[0][5]=list.get(7);
		content[0][6]=list.get(8);
		//-----------------------
		content[1][0]=list.get(1);
		content[1][1]=list.get(9);
		content[1][2]=list.get(10);
		content[1][3]=list.get(11);
		content[1][4]="";
		content[1][5]=list.get(12);
		content[1][6]="";
		//-----------------------
		content[2][0]=list.get(2);
		content[2][1]=list.get(13);
		content[2][2]=list.get(14);
		content[2][3]="";
		content[2][4]="";
		content[2][5]="";
		content[2][6]="";
	}
	
	public int getRowCount() {
		return 3;
	}

	public int getColumnCount() {
		return 7;
	}
	public String getColumnName(int c){
		return title[c];
	}
	
	public Object getValueAt(int r, int c) {
		return content[r][c];
	}
}
