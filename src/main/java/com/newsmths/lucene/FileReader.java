package com.newsmths.lucene;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.log4j.Logger;

import com.newsmths.ide.task.MailTask;

/*
 * 
 * 获取文件内容
 * 
 * */
public class FileReader {

	private static Logger log = Logger.getLogger(FileReader.class);
	
	public static String readText(File file){
		String content = "";
		Reader re = null;
		
		try {
			re = new InputStreamReader(new FileInputStream(file),"GBK"); //UTF-8是乱码，GBK和GB2312是正常的
			char[] chs = new char[1024];
			int count;
			while ((count = re.read(chs)) != -1) {
				content = content + new String(chs, 0, count);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.error("", e);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("", e);
		}
		return content;
	}
	
	public static void main(String[] args) {
		File file = new File("test.txt"); 
		FileReader.readText(file);
	}
	
}
