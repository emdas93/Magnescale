package application;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class SaveToExcel {
	String fileName;
	String fileDir;

	public SaveToExcel() {
		Calendar cal = Calendar.getInstance();
		String time = String.valueOf(cal.get(Calendar.YEAR)) + String.valueOf((cal.get(Calendar.MONTH)+1)) + String.valueOf(cal.get(Calendar.DATE));
		
		this.fileName = time + ".xls";
		this.fileDir = "./";
	}

	public SaveToExcel(String fileName, String fileDir) {
		Calendar cal = Calendar.getInstance();
		String time = String.valueOf(cal.get(Calendar.YEAR)) + String.valueOf((cal.get(Calendar.MONTH)+1)) + String.valueOf(cal.get(Calendar.DATE)) + String.valueOf(cal.get(Calendar.HOUR)) + String.valueOf(cal.get(Calendar.MINUTE)) + String.valueOf(cal.get(Calendar.SECOND));
		this.fileName = fileName + ".xls";
		this.fileDir = fileDir;
	}

	public void saveFile(String data) {
		WritableWorkbook workbook = null;

		WritableSheet sheet = null;

		jxl.write.Label label = null;
		File file = new File(this.fileDir + this.fileName);

		try {
			// 파일 생성
			workbook = Workbook.createWorkbook(file);

			// 시트 생성
			workbook.createSheet("sheet1", 0);
			sheet = workbook.getSheet(0);

			// 셀에 쓰기
			label = new jxl.write.Label(0, 0, "Date");
			sheet.addCell(label);
			label = new jxl.write.Label(1, 0, "1CH_DATA");
			sheet.addCell(label);
			label = new jxl.write.Label(2, 0, "1CH_판정");
			sheet.addCell(label);
			label = new jxl.write.Label(3, 0, "2CH_DATA");
			sheet.addCell(label);
			label = new jxl.write.Label(4, 0, "2CH_판정");
			sheet.addCell(label);
			
			HashMap rs = new HashMap();
			String[] dataList;
			Calendar cal = Calendar.getInstance();
			String time = String.valueOf(cal.get(Calendar.YEAR)) + String.valueOf((cal.get(Calendar.MONTH)+1)) + String.valueOf(cal.get(Calendar.DATE)) + String.valueOf(cal.get(Calendar.HOUR)) + String.valueOf(cal.get(Calendar.MINUTE)) + String.valueOf(cal.get(Calendar.SECOND));
			dataList = data.split(" ");
			rs.put("date", time);
			rs.put("ch1_data", dataList[0]);
			rs.put("ch1_result", dataList[1]);
			rs.put("ch2_data", dataList[2]);
			rs.put("ch2_result", dataList[3]);
			
//			HashMap rs = (HashMap) Main.chData.get(Main.chData.size() - i - 1);
			// 데이터 삽입
			for (int i = 0; i < Main.chData.size(); i++) {
				

				label = new jxl.write.Label(0, (i + 1), (String) rs.get("date"));
				sheet.addCell(label);
				label = new jxl.write.Label(1, (i + 1), (String) rs.get("ch1_data"));
				sheet.addCell(label);
				label = new jxl.write.Label(2, (i + 1), (String) rs.get("ch1_result"));
				sheet.addCell(label);
				label = new jxl.write.Label(3, (i + 1), (String) rs.get("ch2_data"));
				sheet.addCell(label);
				label = new jxl.write.Label(4, (i + 1), (String) rs.get("ch2_result"));
				sheet.addCell(label);
			}

			workbook.write();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
