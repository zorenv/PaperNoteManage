package com.paper.action;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.paper.db.DAO;
import com.paper.db.DbConn;


public class treetozip {
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String id ="1";
		ttp(id);

	}
	public static String ttp (String id){
		DAO dao = new DAO();
		String paperNickName1 = null,paperNickName=null;
		String paperWebFilePath1 = null;
		String paperWebFilePath2 = null;
		String rootpath=null,path=null,paperID=null,pdfname=null,zipname=null,repath=null;
		String[] ppath;
		copyfile copy = new copyfile();
		FileToZip Z = new FileToZip();
		
		try {
		String sql ="select paperNickName, paperWebFilePath from paper where paperID="+id;
		
		ResultSet rs = dao.executeQuery(sql);
		while (rs.next()) {
			paperNickName1 = rs.getString(1);
			System.out.println("pathpaperNickName1="+paperNickName1);
			// paperWebFilePath1 为当前节点的地址
		    paperWebFilePath1 = rs.getString(2);
			System.out.println("paperWebFilePath1="+paperWebFilePath1);
			}
		// rootpath为文件拷贝的目的地址
		// --add by zyc
		 rootpath =getWebrootPath()+"zip/"+paperNickName1;
		 String res = "zip/"+paperNickName1;
		 path=rootpath;
		File rootFile1 = new File(rootpath);
		if(!rootFile1.exists())
		{
			rootFile1.mkdirs();
			System.out.println("创建文件夹:"+rootFile1.toString()+"|");
		}
		repath=rootpath;
		if(paperWebFilePath1==null){
			do{
			String sql2 ="select paperNickName, paperWebFilePath, paperID  from paper where paperPID="+id;
			System.out.println(sql2);
			ResultSet rs2 = dao.executeQuery(sql2);
			
			while (rs2.next()) {
				System.out.println("11111111111");
			paperNickName=rs2 .getString(1);
			// path 为文件拷贝的目的地址
			path=repath+"\\"+paperNickName;
			//System.out.println(path);
			File rootFile2 = new File(path);
			if(!rootFile2.exists())
			{
				rootFile2.mkdir();
				System.out.println("创建"+path);
			}
		    paperWebFilePath2 = rs2 .getString(2);
		    paperID=rs2 .getString(3);
		    if(paperWebFilePath2==null){
		    	
		    	id=paperID;
		    	System.out.println("新ID"+id);
		    	}
		    if(paperWebFilePath2!=null){
		    	System.out.println("路径不空");
		    	//System.out.println(path+"+"+paperWebFilePath2);
				ppath=paperWebFilePath2.split("/");
				pdfname=ppath[ppath.length-1];
				System.out.println("pdfname="+pdfname);
				path=path+"\\"+pdfname;
				paperWebFilePath2=paperWebFilePath2.replace('/', '\\');
				paperWebFilePath2=getWebrootPath()+paperWebFilePath2;
				//System.out.println(path+"+"+paperWebFilePath2);
				copy.copyFile(paperWebFilePath2,path);
		    	
		    }
			
			}
			repath=path;
			}while(paperWebFilePath2==null);
			
			try {
				System.out.println("FROM treetozip>> "+rootpath);
				Z.filezip(rootpath+".zip",rootpath );
				return res+".zip";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}

		}catch (SQLException e) {
			System.out.println("flase");
			e.printStackTrace();
			// TODO Auto-generated catch block e.printStackTrace();
		}
		return null;
		
	}
	private static String getWebrootPath(){
		ClassLoader classLoader = Thread.currentThread()  
	            .getContextClassLoader();  
	    if (classLoader == null) {  
	        classLoader = ClassLoader.getSystemClassLoader();  
	    }  
	    java.net.URL url = classLoader.getResource("");  
	    String ROOT_CLASS_PATH = url.getPath() + "/";  
	    File rootFile = new File(ROOT_CLASS_PATH);  
	    String WEB_INFO_DIRECTORY_PATH = rootFile.getParent() + "/";  
	    File webInfoDir = new File(WEB_INFO_DIRECTORY_PATH);  
	    String SERVLET_CONTEXT_PATH = webInfoDir.getParent() + "/"; 
	    
	    String root=SERVLET_CONTEXT_PATH+"file/";
		return root;
	}

}
