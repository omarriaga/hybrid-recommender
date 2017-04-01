package edu.uniandes.comit.recommenders.testHybrid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;

public class ttt {

	public static void main(String[] args) throws Exception {
		
		BufferedReader reed=null;
		PrintWriter prload= null;
		PrintWriter prtest= null;
		
		reed= new BufferedReader(new FileReader("data/user_artists.dat"));
		prload= new PrintWriter(new File("data/user_artistsload.dat"));
		prtest= new PrintWriter(new File("data/user_artiststest.dat"));
		
		String line=null;
		
		reed.readLine();//header
		int i=1;
		while((line=reed.readLine())!=null){
			if(i%5==0){
				prtest.println(line);
			}else{
				prload.println(line);
			}
			i++;
		}
		reed.close();
		prload.close();
		prtest.close();
		System.out.println("finish");
	}
}
