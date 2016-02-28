package SQLHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class FileList {
    private String dir_name=null;
    Vector<String> ver=null;
    ArrayList<String> names=null;
    
    public FileList(String dir_name) throws IOException{
        this.dir_name=dir_name;    //文件夹地址
        names=new ArrayList<String>();    //保存文件列表的文件地址
        ver=new Vector<String>();    //用做堆栈
    }

    public ArrayList<String> getList() throws Exception{
        ver.add(dir_name);
        while(ver.size()>0){
            File[] files = new File(ver.get(0).toString()).listFiles();    //获取该文件夹下所有的文件(夹)名
            ver.remove(0);
            
            int len=files.length;
            for(int i=0;i<len;i++){
                String tmp=files[i].getAbsolutePath();
                if(files[i].isDirectory())    //如果是目录，则加入队列。以便进行后续处理
                    ver.add(tmp);
                else                    
                    names.add(tmp);        //如果是文件，则加入到names
            }
        }
        return names;
    }
}
