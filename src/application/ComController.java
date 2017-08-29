package application;

import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;

import gnu.io.CommPortIdentifier;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.Label;

public class ComController implements Initializable {

	@FXML
	private Label portStatus;

	@FXML
	private Button connectBtn;

	@FXML
	private ComboBox portListCombo;

	@FXML
	private ComboBox baudRate;

	@FXML
	private ComboBox dataBits;

	@FXML
	private ComboBox parityBits;

	@FXML
	private ComboBox flowControl;

	@FXML
	private Button exitBtn;

	public boolean isConnected = false;

	ObservableList<String> portListStrings = FXCollections.observableArrayList();
	ObservableList<Integer> baudRateStrings = FXCollections.observableArrayList(4800, 9600, 14400, 19200, 38400, 57600,
			76800, 115200);
	ObservableList<Integer> dataBitsStrings = FXCollections.observableArrayList(8, 7);
	ObservableList<String> parityBitsStrings = FXCollections.observableArrayList("None", "Odd", "Even", "Mark",
			"Space");
	ObservableList<String> flowControlStrings = FXCollections.observableArrayList("NONE", "XON/XOFF", "RTS/CTS",
			"XON/XOFF & RTS/CTS", "DTR/DSR", "XON/XOFF & DTR/DSR");

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		baudRate.setItems(baudRateStrings);
		dataBits.setItems(dataBitsStrings);
		parityBits.setItems(parityBitsStrings);
		flowControl.setItems(flowControlStrings);

		ObservableList<String> portList = FXCollections.observableArrayList();
		Enumeration<?> portEnum = CommPortIdentifier.getPortIdentifiers();
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier portId = (CommPortIdentifier) portEnum.nextElement();
			portList.add(portId.getName());
		}
		portListCombo.setItems(portList);

		// portList.setItems(portListStrings);
		//
		// portList.setValue(Main.serialConnection.serialPort.getName());
		// baudRate.setValue(Main.serialConnection.serialPort.getBaudRate());
		// dataBits.setValue(Main.serialConnection.serialPort.getDataBits());
		// parityBits.setValue(Main.serialConnection.serialPort.getParity());
		// flowControl.setValue(Main.serialConnection.serialPort.getFlowControlMode());

		connectBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String portNum = portListCombo.valueProperty().get().toString();
				if (isConnected == true) {
					Main.serialConnection.close();
					isConnected = false;
					Platform.runLater(() -> {
						connectBtn.setText("Connect");
					});
				} else {
					Main.serialConnection = null;
					Main.serialConnection = new SerialTest();
					Main.serialConnection.setPort(portNum);
//					try {
//						switch (baudRate.getValue().toString()) {
//						case "4800":
//							Main.serialConnection.serialPort.setBaudBase(4800);
//							break;
//						case "9600":
//							Main.serialConnection.serialPort.setBaudBase(9600);
//							break;
//						case "14400":
//							Main.serialConnection.serialPort.setBaudBase(14400);
//							break;
//						case "19200":
//							Main.serialConnection.serialPort.setBaudBase(19200);
//							break;
//						case "38400":
//							Main.serialConnection.serialPort.setBaudBase(38400);
//							break;
//						case "57600":
//							Main.serialConnection.serialPort.setBaudBase(57600);
//							break;
//						case "76800":
//							Main.serialConnection.serialPort.setBaudBase(76800);
//							break;
//						case "115200":
//							Main.serialConnection.serialPort.setBaudBase(115200);
//							break;
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
					
					isConnected = Main.serialConnection.initialize();
					Platform.runLater(() -> {
						connectBtn.setText("Disconnect");
					});
				}
				if (isConnected) {
					Platform.runLater(() -> {
						portStatus.setText("Connected");
						portStatus.setStyle("-fx-text-fill:blue");
					});
				} else {
					Platform.runLater(() -> {
						portStatus.setText("Disconnected");
						portStatus.setStyle("-fx-text-fill:red");
					});
				}
			}
		});
		exitBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Main.scene.setRoot(Main.layouts.get("main"));
			}
		});
	}

}
