package web;
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
	
	//获取destStr中与正则相匹配的List
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
	//打开指定url的网页内容
	public static String getURLContent(String urlString,String charset){
		StringBuilder sb=new StringBuilder();
		try {
			URL url=new URL(urlString);
			
			BufferedReader reader=new BufferedReader(new InputStreamReader(url.openStream(),Charset.forName(charset)));
			String temp="";
			while((temp=reader.readLine())!=null){
				//System.out.println(temp);
				sb.append(temp);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.out.println("无数据");
			//e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("无数据");
			//e.printStackTrace();
		}
		return sb.toString();
	}
	
	//根据url和文件名保存网络图片
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
