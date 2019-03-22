
public class dailyRecord {
private String userId;
private String date ;
private String itemId;
private String price;


public String getUserId() {
	return userId; 
}


public void setUserId(String userID) {
	this.userId = userID;
}


public String getDate() {
	return date;
}


public void setDate(String date) {
	this.date = date;
}


public String getItemID() {
	return itemId;
}


public void setItemID(String item_ID) {
	this.itemId = item_ID;
}


public String getPrice() {
	return price;
}


public void setPrice(String data) {
	this.price = data;
}



@Override
public String toString(){
	return "\n User_ID="+getUserId()+"::Date and time "+getDate()+"::Item_ID="+getItemID()+"::Price="+getPrice();
}

}
