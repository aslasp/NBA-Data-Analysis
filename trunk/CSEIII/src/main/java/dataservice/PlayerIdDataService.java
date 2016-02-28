package dataservice;

public interface PlayerIdDataService{
     public String getPlayerName(int playerID);
     public void openSql();
     public void closeSql();
}
