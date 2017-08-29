package application;
	
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

	public static int totalCnt;
	public static int okCnt;
	public static int ngCnt;
	
	// 레이아웃 저장 맵
	public static HashMap<String, Parent> layouts = new HashMap<String, Parent>();
	
	// 씬을 바꿔주기 위해 전역 객체
	public static Scene scene;
	
	// 메인컨트롤러
	public static MainController mainController;
	
	// 시리얼통신 객체 [전역]
	public static SerialTest serialConnection;
	
	// 데이터 오차 범위 설정 저장
	public static ArrayList<HashMap> chData = new ArrayList();
	public static HashMap ch1_data = new HashMap();
	public static HashMap ch2_data = new HashMap();

	// 시리얼로 부터 받은 데이터 저장 변수
	public static String serialData;
	public static String[] dataList;
	
	@Override
	public void start(Stage stage) {
		try {
			Main.chData.add(ch1_data);
			Main.chData.add(ch2_data);
			File f = new File("./setting.ini");
			if(f.exists()) {
				FileReader rd = new FileReader(f);
				BufferedReader br = new BufferedReader(rd);
				String s;
				String data = "";
				while((s = br.readLine()) != null) {
					data += s;
				}
				String[] limitList = data.split(" ");
				String[] ch1_limit = limitList[0].split("\\~");
				String[] ch2_limit = limitList[1].split("\\~");
				Main.chData.get(0).put("lowLimitValue", ch1_limit[0]);
				Main.chData.get(0).put("upLimitValue", ch1_limit[1]);
				Main.chData.get(1).put("lowLimitValue", ch2_limit[0]);
				Main.chData.get(1).put("upLimitValue", ch2_limit[1]);
				Platform.runLater(new Runnable() {
					public void run() {
						Main.mainController.ch1_range.setText((String) Main.chData.get(0).get("lowLimitValue") + " ~ " + (String)Main.chData.get(0).get("upLimitValue"));
						Main.mainController.ch2_range.setText((String) Main.chData.get(1).get("lowLimitValue") + " ~ " + (String)Main.chData.get(1).get("upLimitValue"));
					}
				});
			}
			File cntFile = new File("./count.ini");
			if(cntFile.exists()) {
				FileReader rd = new FileReader(cntFile);
				BufferedReader br = new BufferedReader(rd);
				String s;
				String data = "";
				while((s = br.readLine()) != null) {
					data += s;
				}
				String[] cntList = data.split(" ");
				Main.totalCnt = Integer.parseInt(cntList[0]);
				Main.okCnt = Integer.parseInt(cntList[1]);
				Main.ngCnt = Integer.parseInt(cntList[2]);
				Platform.runLater(new Runnable() {
					public void run() {
						Main.mainController.totalCount.setText(String.valueOf(Main.totalCnt));
						Main.mainController.okCount.setText(String.valueOf(Main.okCnt));
						Main.mainController.ngCount.setText(String.valueOf(Main.ngCnt));
					}
				});
			}
			// 시리얼 연결
//			Main.serialConnection = new SerialTest();
//			Main.serialConnection.setPort("COM3");
//			Main.serialConnection.initialize();
			
			// FXML 불러오기
			FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("main.fxml"));
			FXMLLoader setLoader = new FXMLLoader(getClass().getResource("set.fxml"));
			FXMLLoader comLoader = new FXMLLoader(getClass().getResource("com.fxml"));
			
			Main.layouts.put("main", (Parent) mainLoader.load());
			Main.layouts.put("set", (Parent) setLoader.load());
			Main.layouts.put("com", (Parent) comLoader.load());
			
			Main.mainController = mainLoader.getController();
			
			Main.scene = new Scene(Main.layouts.get("main"),1024, 700);
			
			Main.scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
			stage.setOnCloseRequest(e -> {
				Platform.exit();
				System.exit(0);
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ch1_data.put("titleName", "CH1_DATA");
		ch1_data.put("upLimitValue", "0");
		ch1_data.put("lowLimitValue", "0");
		ch1_data.put("specUpLimitValue", "0");
		ch1_data.put("specLowLimitValue", "0");
		
		ch2_data.put("titleName", "CH2_DATA");
		ch2_data.put("upLimitValue", "0");
		ch2_data.put("lowLimitValue", "0");
		ch2_data.put("specUpLimitValue", "0");
		ch2_data.put("specLowLimitValue", "0");
		launch(args);
	}
}
