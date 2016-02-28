package newui;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

/**
 * 
 * @author jincui
 *
 */
public class WebSec {
	public static void main(String[] args) {
		
	//	String destdir=getURLContent("http://www.nba.com/","gbk");
		//Pattern p=Pattern.compile("<a[\\s\\S]+?</a>");
		//��ȡ�����ӵ�ַ
		//String rest="href=\"(.+?)\"";
		//String rest="href=\"([\\w\\s./:]+?)\"";
		//String url="http://stats.nba.com/media/players/230x185/201158.png";
		//String path="active/";
		//String fileName="test.png";
		//SavePic(url,path,fileName);
		
	}
	
	//��ȡƥ����ַ�list
	public static ArrayList<String> getMatcherSubstrs(String destStr,String regex){
		Pattern p=Pattern.compile(regex);
		Matcher m=p.matcher(destStr);
		ArrayList<String> result=new ArrayList<String>();
		while(m.find()){
			//System.out.println(m.group());
			result.add(m.group());
		}
		return result;
	}
	//���urlString��Ӧ��ҳԴ������
	public static String getURLContent(String urlString,String charset){
		StringBuilder sb=new StringBuilder();
		try {
			URL url=new URL(urlString);
			//��������
			BufferedReader reader=new BufferedReader(new InputStreamReader(url.openStream(),Charset.forName(charset)));
			String temp="";
			while((temp=reader.readLine())!=null){
				//System.out.println(temp);
				sb.append(temp);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.out.println("û�����");
			//e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("û�����");
			//e.printStackTrace();
		}
		return sb.toString();
	}
	
	
	public static void SavePic(String url,String path,String fileName){
		try {
			URL urlConect=new URL(url);
			try {
				//URLConnection conect=urlConect.openConnection();
				 BufferedImage image = ImageIO.read(urlConect.openStream());
				// File file=new File()
				 FileOutputStream output=new FileOutputStream(path+fileName);
				 ImageIO.write(image, "png", output);
				 output.flush();
				 output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
