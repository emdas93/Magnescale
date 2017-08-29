package application;

import java.io.File;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MainController implements Initializable {

	/* COUNT */
	@FXML
	Label totalCount;
	@FXML
	Label okCount;
	@FXML
	Label ngCount;
	
	@FXML
	Button resetBtn;
	
	/* 상단 버튼 들 */
	@FXML
	private Button setBtn;

	@FXML
	private Button comBtn;

	@FXML
	private Button exitBtn;

	/* 채널 데이터 */
	@FXML
	private TextField ch1_textField;

	@FXML
	private TextField ch2_textField;

	/* 채널 체크 텍스트 */
	@FXML
	private Label ch1_label;

	@FXML
	private Label ch2_label;

	/* 채널 범위 */
	@FXML
	Label ch1_range;

	@FXML
	Label ch2_range;

	public MainController mainController = this;

	public void initialize(URL location, ResourceBundle resources) {
		// resetButton Click
		resetBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Main.totalCnt = 0;
				Main.okCnt = 0;
				Main.ngCnt = 0;
				File f = new File("./count.ini");
				f.delete();
				Platform.runLater(new Runnable() {
					public void run() {
						Main.mainController.totalCount.setText("0");
						Main.mainController.okCount.setText("0");
						Main.mainController.ngCount.setText("0");
					}
				});
			}
		});
		
		// 레이아웃 SET으로 변경
		setBtn.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				Main.scene.setRoot(Main.layouts.get("set"));
			}
		});

		// 레이아웃 COM으로 변경
		comBtn.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				Main.scene.setRoot(Main.layouts.get("com"));
			}
		});
		exitBtn.setOnAction((event) -> {
			Platform.exit();
			System.exit(0);
		});
	}

	public void receiveData() {
		HashMap<String, String> dataMap = new HashMap();
		Main.serialData = Main.serialData.replace("\u0010", "");
		Main.serialData = Main.serialData.replace("\u0013", "");
		Main.serialData = Main.serialData.replace("\n", "");
		Main.serialData = Main.serialData.replace("\r", "");
		Main.serialData = Main.serialData.replace("A", "");
		Main.serialData = Main.serialData.replace("B", "");
		Main.serialData = Main.serialData.replace("null", "");

		System.out.println(Main.serialData);

		Main.dataList = Main.serialData.split(" ");

		ch1_textField.setText(Main.dataList[0]);
		ch2_textField.setText(Main.dataList[1]);
		dataMap.put("ch1_data", Main.dataList[0]);
		dataMap.put("ch2_data", Main.dataList[1]);
		Platform.runLater(() -> {
			boolean ch1_result = false;
			boolean ch2_result = false;

			ch1_result = checkData(Main.dataList[0], 1);
			ch2_result = checkData(Main.dataList[1], 2);

			if (ch1_result) {
				dataMap.put("ch1_result", "OK");
				ch1_label.setText("OK");
				ch1_label.setStyle("-fx-text-fill:blue");
			} else {
				if (Main.dataList[0].equals("E")) {
					dataMap.put("ch1_result", "ERROR");
					ch1_label.setText("ERROR");
					ch1_label.setStyle("-fx-text-fill:red");
				} else {
					dataMap.put("ch1_result", "NG");
					ch1_label.setText("NG");
					ch1_label.setStyle("-fx-text-fill:red");
				}
			}

			if (ch2_result) {
				dataMap.put("ch2_result", "OK");
				ch2_label.setText("OK");
				ch2_label.setStyle("-fx-text-fill:blue");
			} else {
				if (Main.dataList[1].equals("E")) {
					dataMap.put("ch2_result", "ERROR");
					ch2_label.setText("ERROR");
					ch2_label.setStyle("-fx-text-fill:red");
				} else {
					dataMap.put("ch2_result", "NG");
					ch2_label.setText("NG");
					ch2_label.setStyle("-fx-text-fill:red");
				}
			}

			Calendar cal = Calendar.getInstance();
			String time = String.valueOf(cal.get(Calendar.YEAR)) + "년" + String.valueOf((cal.get(Calendar.MONTH) + 1))
					+ "월" + String.valueOf(cal.get(Calendar.DATE)) + "일" + String.valueOf(cal.get(Calendar.HOUR)) + "시"
					+ String.valueOf(cal.get(Calendar.MINUTE)) + "분" + String.valueOf(cal.get(Calendar.SECOND)) + "초";
			// 날짜, 1채널데이터, 판정, 2채널 데이터, 판정
			String data = time + "," + dataMap.get("ch1_data") + "," + dataMap.get("ch1_result") + ","
					+ dataMap.get("ch2_data") + "," + dataMap.get("ch2_result");
			if (dataMap.get("ch1_result").equals("OK") && dataMap.get("ch2_result").equals("OK")) {
				new SaveToTxt("OK").save(data);
				Main.okCnt++;
			} else {
				new SaveToTxt("NG").save(data);
				Main.ngCnt++;
			}
			Main.totalCnt++;
			Platform.runLater(new Runnable() {
				public void run() {
					totalCount.setText(String.valueOf(Main.totalCnt));
					okCount.setText(String.valueOf(Main.okCnt));
					ngCount.setText(String.valueOf(Main.ngCnt));
				}
			});
			new SaveToTxt("TOTAL").save(data);
			new SaveSetting("count.ini", String.valueOf(Main.totalCnt), String.valueOf(Main.okCnt),
					String.valueOf(Main.ngCnt));
			Main.serialData = "";
		});
	}

	public static boolean checkData(String data, int channel) {
		boolean result = false;
		--channel;
		double checkData;
		if (data.equals("E")) {
			result = false;
		} else {
			checkData = Double.parseDouble(data);
			double lowData = Double.parseDouble(Main.chData.get(channel).get("lowLimitValue").toString());
			double upData = Double.parseDouble(Main.chData.get(channel).get("upLimitValue").toString());
			if (checkData > lowData && checkData < upData)
				result = true;
			else
				result = false;
		}

		return result;
	}

}
