package application;


import javafx.beans.property.StringProperty;

public class DataModel {
	private StringProperty titleName;
	private StringProperty upLimitValue;
	private StringProperty lowLimitValue;
	private StringProperty specUpLimitValue;
	private StringProperty specLowLimitValue;
	
	DataModel(StringProperty titleName, StringProperty upLimitValue, StringProperty lowLimitValue, StringProperty specUpLimitValue, StringProperty specLowLimitValue){
		this.titleName 			= titleName;
		this.upLimitValue 		= upLimitValue;
		this.lowLimitValue 		= lowLimitValue;
	}
	
	public StringProperty getTitleName() {
		return this.titleName;
	}
	
	public void setTitleName(String titleName) {
		this.titleName.set(titleName);
	}
	
	public StringProperty getUpLimitValue() {
		return this.upLimitValue;
	}
	
	public void setUpLimitValue(String upLimitValue) {
		this.upLimitValue.set(upLimitValue);
	}
	
	public StringProperty getLowLimitValue() {
		return this.lowLimitValue;
	}
	
	public void setLowLimitValue(String lowLimitValue) {
		this.lowLimitValue.set(lowLimitValue);
	}
}
