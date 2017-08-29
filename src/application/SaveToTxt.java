package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Calendar;

public class SaveToTxt {
	String fileName;
	String filePath;
	public SaveToTxt() {
		this.fileName = "C:\\dataList.txt";
	}
	
	{
		
	}
	public SaveToTxt(String fileName) {
		Calendar cal = Calendar.getInstance();
		String year = Integer.toString(cal.get(Calendar.YEAR));
		String month = Integer.toString(cal.get(Calendar.MONTH)+1);
		String date = Integer.toString(cal.get(Calendar.DATE));
		this.fileName = "C:\\magnescale\\" + fileName + "\\" + year+"년\\" + month+"월\\" + date+"일" + "DATA.csv";
		this.filePath = "C:\\magnescale\\" + fileName + "\\" + year+"년\\" + month+"월";
	}

	public void save(String data) {

		try {
			File f = new File(this.fileName);
			File path = new File(this.filePath);
			if(!f.exists()) {
				path.mkdirs();
			}
			////////////////////////////////////////////////////////////////
			
			FileWriter fw = new FileWriter(f,true);
			FileOutputStream fileOutputStream = new FileOutputStream(f, true);
			OutputStreamWriter OutputStreamWriter = new OutputStreamWriter(fileOutputStream, "MS949");
			BufferedWriter out = new BufferedWriter(OutputStreamWriter);
			
			
			
//			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f),"UTF-8"));
			
			if(f.length() == 0) {
				String firstWrite = "DATE,CH1_DATA,CH1_판정,CH2_DATA,CH2_판정\r\n";
				out.write(firstWrite);
			}
			
			out.write(data);
			out.newLine();
			out.close();
			////////////////////////////////////////////////////////////////
		} catch (Exception e) {
			System.err.println(e); // 에러가 있다면 메시지 출력
			System.exit(1);
		}

	}
}
