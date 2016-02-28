package businesslogic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GetServer {
	private static String host=null;
	
	public static String getServerHost() throws IOException {
		if(host==null){
			BufferedReader br=new BufferedReader(new FileReader("Port.txt"));
			String str=null;
			String port=null;
			while((str=br.readLine())!=null){
				port=str;
			}
			br.close();
			String ip=null;
			br=new BufferedReader(new FileReader("IP.txt"));
			str=null;
			while((str=br.readLine())!=null){
				ip=str;
			}
			br.close();
			host="127.0.0.1:1099";
			return host;
		}else{
			return host;
		}
	}
	

}
