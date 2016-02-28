package newui.matchui;

import vo.MatchVO;
import newui.FatherPanel;

public class LiveDetailPanel extends FatherPanel{
	DetailCard card;
	HistoryLiveTextPanel  history;
	//tag=0 history
	public LiveDetailPanel(final MatchVO v,int tag) {
		isDetail = true;
		// ----card------------
		card = new DetailCard(v);
		gbc.gridy = 1;
		gbc.gridheight = 4;
		gbc.weighty = 4;
		gbl.setConstraints(card, gbc);
		add(card);
		//----live historyPanel---根据tag类型决定是哪种直播
		String vteam=v.getVisitingTeam();
		String htem=v.getHomeTeam();
		String date=v.getDate();
		String season=v.getSeason();
		history=new HistoryLiveTextPanel(vteam,htem,season,date);
		gbc.gridy=5;
		gbc.gridheight = 7;
		gbc.weighty=7;
		gbl.setConstraints(history, gbc);
		add(history);
	}
}
