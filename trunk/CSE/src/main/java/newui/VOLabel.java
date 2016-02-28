package newui;

import javax.swing.JLabel;

import vo.PlayerVO;

public class VOLabel extends JLabel{

	/**
	 * 用在球员对比的JLabel，内含一个VO
	 */
	private static final long serialVersionUID = 1L;
	PlayerVO vo;
	public VOLabel(String txt){
		super(txt);
	}
	public VOLabel(String txt,PlayerVO p){
		super(txt);
		vo=p;
	}
	public void setVO(PlayerVO p){
		vo=p;
	}
	public PlayerVO getVO(){
		return vo;
	}
}
