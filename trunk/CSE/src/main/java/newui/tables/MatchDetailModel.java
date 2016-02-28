package newui.tables;

import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import bl.player.Player;
import vo.RecordVO;

public class MatchDetailModel extends AbstractTableModel{
	String[] head={"姓名","位置", "分钟", "命中", "出手", "投篮命中率","三分命中","三分出手","三分命中率",
			"罚球命中","罚球出手","罚球命中率","进攻","防守",
			 "篮板","助攻","抢断","盖帽","失误","犯规","得分"};
	ArrayList<ArrayList<Object>> content=new ArrayList<ArrayList<Object>>();
	//最后一行统计
	int time;
	DecimalFormat df;
	public MatchDetailModel(){
		df=new DecimalFormat("0.0");
	}
	public int getRowCount() {
		// TODO Auto-generated method stub
		return content.size() ;
	}

	public int getColumnCount() {
		// TODO Auto-generated method stub
		return head.length;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return content.get(rowIndex).get(columnIndex);
	}
	
	public String getColumnName(int col) {
		return head[col];
	}
	public Class getColumnClass(int column) {
		return content.get(0).get(column).getClass();
	}
	//={"姓名","位置", "分钟", "%", "命中", "出手","三分%","三分命中","三分出手",
	//"罚球%","罚球命中","罚球出手","进攻","防守",
	// "篮板","助攻","抢断","盖帽","失误","犯规","得分"};
	//统计  率则平均  其他相加
	public void Refresh(ArrayList<RecordVO> list){
		content.clear();
		int n=list.size();
		double shootRate=0;int shootNum=0;
		int shootAtm=0;double threeRate=0;int threeNum=0;
		double threeAtm=0;double freeRate=0;int freeNum=0;
		int freeAtm=0;int offendR=0;int defendR=0;
		int assist=0;int steal=0;int block=0;int turn=0;int foul=0;int score=0;
		for(RecordVO v:list){
			ArrayList<Object> line=new ArrayList<Object>();
			line.add(v.getPlayerName());
			line.add(v.getPosition());
			line.add(v.getPresentTime());
			//保留一位小数
			String shoot="0";
			if(v.getShootAttemptNum()!=0){
				shoot=df.format((v.getShootHitNum()/(double)v.getShootAttemptNum())*100);
				
			}
			shootRate+=Double.parseDouble(shoot);
			shoot=shoot+'%';
			line.add(v.getShootHitNum());
			shootNum+=v.getShootHitNum();
			line.add(v.getShootAttemptNum());
			shootAtm+=v.getShootAttemptNum();
			line.add(shoot);
			String three="0";
			if(v.getThreeAttemptNum()!=0)
				 three=df.format((v.getThreeHitNum()/(double)v.getThreeAttemptNum())*100);
			threeRate+=Double.parseDouble(three);
			three=three+'%';
			line.add(v.getThreeHitNum());
			threeNum+=v.getThreeHitNum();
			line.add(v.getThreeAttemptNum());
			threeAtm+=v.getThreeAttemptNum();
			line.add(three);
			String free="0";
			if(v.getFreeThrowAttemptNum()!=0)
				free=df.format((v.getFreeThrowHitNum()/(double)v.getFreeThrowAttemptNum())*100);
			freeRate+=Double.parseDouble(free);
			free=free+'%';
			line.add(v.getFreeThrowHitNum());
			freeNum+=v.getFreeThrowHitNum();
			line.add(v.getFreeThrowAttemptNum());
			freeAtm+=v.getFreeThrowAttemptNum();
			line.add(free);
			
			line.add(v.getOffenReboundNum());
			offendR+=v.getOffenReboundNum();
			line.add(v.getDefenReboundNum());
			defendR+=v.getDefenReboundNum();
			line.add(v.getReboundNum());
			
			line.add(v.getAssistNum());
			assist+=v.getAssistNum();
			line.add(v.getStealNum());
			steal+=v.getStealNum();
			
			//block=0;int turn=0;int foul=0;int score=0;
			line.add(v.getBlockNum());
			block+=v.getBlockNum();
			line.add(v.getTurnOverNum());
			turn+=v.getTurnOverNum();
			line.add(v.getFoulNum());
			foul+=v.getFoulNum();
			line.add(v.getScore());
			score+=v.getScore();
			content.add(line);
		}
		
		ArrayList<Object> last=new ArrayList<Object>();
		last.add("统计");
		last.add("-");
		last.add(time);
		String s=df.format(shootRate/n)+"%";		
		last.add(shootNum);
		last.add(shootAtm);
		last.add(s);
		String t=df.format(threeRate/n)+"%";
		last.add(threeNum);last.add(threeAtm);
		last.add(t);
		String f=df.format(freeRate/n)+"%";
		last.add(freeNum);last.add(freeAtm);last.add(f);
		last.add(offendR);last.add(defendR);last.add(offendR+defendR);
		last.add(assist);last.add(steal);last.add(block);
		last.add(turn);last.add(foul);last.add(score);
		content.add(last);
		
	}
	
	public void setTime(int time){
		this.time=time;
		
	}
}
