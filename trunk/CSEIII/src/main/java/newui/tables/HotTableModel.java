package newui.tables;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;


public class HotTableModel extends AbstractTableModel{
	public int num=2;
	public String[] head={"哈哈"};
	public ArrayList<ArrayList<Object>> content =new ArrayList<ArrayList<Object>>();;
	public HotTableModel(String[] head){

		this.head=head;
	}
	
	public int getRowCount() {
		// TODO Auto-generated method stub
	
		return content.size();
	}
	@Override
	public Class getColumnClass(int c) {
		return content.get(0).get(c).getClass();
	}
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return head.length;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		//System.out.println(rowIndex+":"+columnIndex);
		//System.out.println("你报错啊："+content.size());
		return content.get(rowIndex).get(columnIndex);
	}
	public String getColumnName(int col) {
		return head[col];
	}
	
	public void setHead(String[] head){
		this.head=head;
	}
	
	
	
}
