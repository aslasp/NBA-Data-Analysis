package blService;

public interface PlayerIdBLService {
	public String getPlayerName(int playerID);
	public void openSql();
	public void closeSql();
}
