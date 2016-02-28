package newui.statsui;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import data.PlayerStatistic;

public class HuiGuiTableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String[][] content=new String[3][11];
	String[] title={"因素","出场次数","得分","篮板","助攻","抢断","犯规","盖帽","效率值","GmSc效率值","得分-篮板-助攻"};
	public HuiGuiTableModel() {
		PlayerStatistic ps=new PlayerStatistic();
		HashMap<String, ArrayList<String>> map=ps.start();
		ArrayList<String> efflist=map.get("efficiency");
		ArrayList<String> gmsclist=map.get("GmScEfficiencyValue");
		ArrayList<String> scorelist=map.get("score_rebound_assist");
		ArrayList<String> sclist=map.get("score");
		ArrayList<String> relist=map.get("rebound");
		ArrayList<String> aslist=map.get("assist");
		ArrayList<String> malist=map.get("matchNum");
		ArrayList<String> stlist=map.get("steal");
		ArrayList<String> folist=map.get("foul");
		ArrayList<String> bllist=map.get("block");
		content[0][0]="F值";
		content[1][0]="拟合度";
		content[2][0]="回归系数";
		content[0][1]=malist.get(0);
		content[1][1]=malist.get(1);
		content[2][1]=malist.get(2);
		
		content[0][2]=sclist.get(0);
		content[1][2]=sclist.get(1);
		content[2][2]=sclist.get(2);
		
		content[0][3]=relist.get(0);
		content[1][3]=relist.get(1);
		content[2][3]=relist.get(2);
		
		content[0][4]=aslist.get(0);
		content[1][4]=aslist.get(1);
		content[2][4]=aslist.get(2);
		
		content[0][5]=stlist.get(0);
		content[1][5]=stlist.get(1);
		content[2][5]=stlist.get(2);
		
		content[0][6]=folist.get(0);
		content[1][6]=folist.get(1);
		content[2][6]=folist.get(2);
		
		content[0][7]=bllist.get(0);
		content[1][7]=bllist.get(1);
		content[2][7]=bllist.get(2);
		
		content[0][8]=efflist.get(0);
		content[1][8]=efflist.get(1);
		content[2][8]=efflist.get(2);
		
		content[0][9]=gmsclist.get(0);
		content[1][9]=gmsclist.get(1);
		content[2][9]=gmsclist.get(2);
		
		content[0][10]=scorelist.get(0);
		content[1][10]=scorelist.get(1);
		content[2][10]=scorelist.get(2);
	}
	public int getRowCount() {
		return 3;
	}

	public int getColumnCount() {
		return 11;
	}
	public String getColumnName(int c){
		return title[c];
	}
	public Object getValueAt(int rowIndex, int columnIndex) {
		return content[rowIndex][columnIndex];
	}

}
