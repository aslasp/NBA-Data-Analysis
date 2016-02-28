package newui.tables;

import java.util.Comparator;

public class PlayerTableComparator implements Comparator<String>{

	public int compare(String o1, String o2) {
		String[] temp1=o1.split(":");
		String[] temp2=o2.split(":");
		int minute1=Integer.parseInt(temp1[0]);
		int second1=Integer.parseInt(temp1[1]);
		int minute2=Integer.parseInt(temp2[0]);
		int second2=Integer.parseInt(temp2[1]);
		if(minute1<minute2){
			return -1;
		}
		else if(minute1>minute2){
			return 1;
		}
		else{
			if(second1<second2){
				return -1;
			}
			else if(second1>second2){
				return 1;
			}
			else{
				return 0;
			}
		}
		
	}

}
