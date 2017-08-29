package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;

public class SetController implements Initializable{
	// Button
	@FXML
	private Button exitBtn;

	@FXML
	private Button saveBtn;

	// VALUE
	@FXML
	private TableView<DataModel> dataTable;
	@FXML
	private TableColumn<DataModel, String> titleNameColumn;

	@FXML
	private TableColumn<DataModel, String> upLimitValueColumn;

	@FXML
	private TableColumn<DataModel, String> lowLimitValueColumn;

	@FXML
	private TableColumn<DataModel, String> specUpLimitValueColumn;

	@FXML
	private TableColumn<DataModel, String> specLowLimitValueColumn;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		ObservableList<DataModel> myList = FXCollections.observableArrayList(
				new DataModel(new SimpleStringProperty((String) Main.chData.get(0).get("titleName")),
						new SimpleStringProperty((String) Main.chData.get(0).get("upLimitValue")),
						new SimpleStringProperty((String) Main.chData.get(0).get("lowLimitValue")),
						new SimpleStringProperty((String) Main.chData.get(0).get("specUpLimitValue")),
						new SimpleStringProperty((String) Main.chData.get(0).get("specLowLimitValue"))),
				new DataModel(new SimpleStringProperty((String) Main.chData.get(1).get("titleName")),
						new SimpleStringProperty((String) Main.chData.get(1).get("upLimitValue")),
						new SimpleStringProperty((String) Main.chData.get(1).get("lowLimitValue")),
						new SimpleStringProperty((String) Main.chData.get(1).get("specUpLimitValue")),
						new SimpleStringProperty((String) Main.chData.get(1).get("specLowLimitValue"))));
		titleNameColumn.setCellValueFactory(cellData -> cellData.getValue().getTitleName());
		upLimitValueColumn.setCellValueFactory(cellData -> cellData.getValue().getUpLimitValue());
		lowLimitValueColumn.setCellValueFactory(cellData -> cellData.getValue().getLowLimitValue());
		dataTable.setItems(myList);
		dataTable.setEditable(true);

		upLimitValueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		upLimitValueColumn.setOnEditCommit(new EventHandler<CellEditEvent<DataModel, String>>() {
			@Override
			public void handle(CellEditEvent<DataModel, String> event) {
				((DataModel) event.getTableView().getItems().get(event.getTablePosition().getRow()))
						.setUpLimitValue(event.getNewValue());
				TablePosition pos = dataTable.getSelectionModel().getSelectedCells().get(0);
				int row = pos.getRow();
				DataModel item = dataTable.getItems().get(row);
				TableColumn col = pos.getTableColumn();
				String data = (String) col.getCellObservableValue(item).getValue();
				Main.chData.get(row).put("upLimitValue", data);
				Main.mainController.ch1_range.setText((String)Main.chData.get(0).get("lowLimitValue") + "~" + (String)Main.chData.get(0).get("upLimitValue"));
				Main.mainController.ch2_range.setText((String)Main.chData.get(1).get("lowLimitValue") + "~" + (String)Main.chData.get(1).get("upLimitValue"));

			}

		});

		lowLimitValueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		lowLimitValueColumn.setOnEditCommit(new EventHandler<CellEditEvent<DataModel, String>>() {
			@Override
			public void handle(CellEditEvent<DataModel, String> event) {
				((DataModel) event.getTableView().getItems().get(event.getTablePosition().getRow()))
						.setLowLimitValue(event.getNewValue());
				TablePosition pos = dataTable.getSelectionModel().getSelectedCells().get(0);
				int row = pos.getRow();
				DataModel item = dataTable.getItems().get(row);
				TableColumn col = pos.getTableColumn();
				String data = (String) col.getCellObservableValue(item).getValue();
				Main.chData.get(row).put("lowLimitValue", data);
				Main.mainController.ch1_range.setText((String)Main.chData.get(0).get("lowLimitValue") + "~" + (String)Main.chData.get(0).get("upLimitValue"));
				Main.mainController.ch2_range.setText((String)Main.chData.get(1).get("lowLimitValue") + "~" + (String)Main.chData.get(1).get("upLimitValue"));
				
			}

		});

		exitBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				String setting = Main.chData.get(0).get("lowLimitValue") + "~" + (String)Main.chData.get(0).get("upLimitValue");
				String setting2 = Main.chData.get(1).get("lowLimitValue") + "~" + (String)Main.chData.get(1).get("upLimitValue");
				new SaveSetting("setting.ini",setting,setting2);
				Main.scene.setRoot(Main.layouts.get("main"));
			}
		});
	}

}
