package newui;

import bl.Match;
import bl.Player;
import bl.Team;
import blService.MatchBLService;
import blService.PlayerBLService;
import blService.TeamBLService;

//何时初始化
public interface Service {
	public final PlayerBLService player = new Player();
	public final TeamBLService team = new Team();
	public final MatchBLService match = new Match();

}
