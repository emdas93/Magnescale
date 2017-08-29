package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveSetting {
	public SaveSetting(String fileName, String setting, String setting2) {
		
		
		File f = new File("./"+fileName);
		try {
			FileWriter fw = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(setting +" "+ setting2);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
public SaveSetting(String fileName, String str1, String str2, String str3) {
		
		
		File f = new File("./"+fileName);
		try {
			FileWriter fw = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(str1 +" "+ str2 + " " + str3);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
