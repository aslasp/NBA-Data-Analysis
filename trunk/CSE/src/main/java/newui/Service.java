package newui;

import bl.match.Match;
import bl.player.Player;
import bl.team.Team;
import blservice.MatchBLService;
import blservice.PlayerBLService;
import blservice.TeamBLService;
//何时初始化
public interface Service {
	public final PlayerBLService player=new Player();
	public final TeamBLService team=new Team();
	public final MatchBLService match=new Match();

}
