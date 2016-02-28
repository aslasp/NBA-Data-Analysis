package data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;


/*设计思路：
 * 第一次调用了getList方法后 开启checkChange的线程
 * 
 * */
public class FileList {
	private String dir_name = null;
	Vector<String> ver = null;
	ArrayList<String> names = null;
	private String path;
	private Match match;

	// 传一个参数方法；

	public FileList(String dir_name) throws IOException {
		this.dir_name = dir_name; // 文件夹地址
		names = new ArrayList<String>(); // 保存文件列表的文件地址
		ver = new Vector<String>(); // 用做堆栈
		path = new File(dir_name).getPath() + "\\";
	}
	
	public FileList (String dir_name ,Match match) throws IOException {
		this(dir_name);
		this.match=match;
		
	}

	public ArrayList<String> getList() throws Exception {
		/*
		 * ver.add(dir_name); while(ver.size()>0){ File[] files = new
		 * File(ver.get(0).toString()).listFiles(); //获取该文件夹下所有的文件(夹)名
		 * ver.remove(0);
		 * 
		 * int len=files.length; for(int i=0;i<len;i++){ String
		 * tmp=files[i].getAbsolutePath(); if(files[i].isDirectory())
		 * //如果是目录，则加入队列。以便进行后续处理 ver.add(tmp); else names.add(tmp);
		 * //如果是文件，则加入到names } }
		 */
		// 试试看 这样行不行；
		String[] pname = new File(dir_name).list();
		for (int i = 0; i < pname.length; i++)
			names.add(path + pname[i]);// 字符串连接方法较为耗时 应比较一下
		return names;
	}

	

	
	
	public ArrayList<String> checkMatchesChange() {
		String[] newpath = new File(dir_name).list();
		int newnum = newpath.length;
		int cha = newnum - names.size();
		ArrayList<String> pathaddress=null;
		if (cha > 0) {// 说明有新文件
			// cha 表明新增文件个数 新增文件填入序列不一定
			pathaddress = new ArrayList<String>();
			for (int i = 0; i < newnum; i++) {
				String np = path + newpath[i];
				if (names.indexOf(np) < 0) {
					// 说明该文件为新文件 np为新地址
					pathaddress.add(np);
					cha--;
					if (cha == 0)
						break;// 因为随机插入 有益于 时间
				}
			}
			//更新names;
			names.addAll(pathaddress);
		}
		
		if(pathaddress==null)
			return null;
		else 			
		  return pathaddress;

	}

}
