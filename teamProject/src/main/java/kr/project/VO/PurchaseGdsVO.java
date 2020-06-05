package kr.project.VO;

import org.springframework.stereotype.Repository;

// 결제 내역 VO
@Repository
public class PurchaseGdsVO {
	
	private int idx;	// 1
	private String BuyerID;	// 2
	private String seller_id; //-1
	private String goods_name;//-2
	private int num;	// 8
	private int price;		//-3
	private String category;//-4
	private String DOP;	// 3
	private String image_name; //-5
	private String del_status; // 7
	private String name;	// 4
	private String phonenum;	// 5
	private String address;	// 6
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getBuyerID() {
		return BuyerID;
	}
	public void setBuyerID(String buyerID) {
		BuyerID = buyerID;
	}
	public String getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDOP() {
		return DOP;
	}
	public void setDOP(String dOP) {
		DOP = dOP;
	}
	public String getDel_status() {
		return del_status;
	}
	public void setDel_status(String del_status) {
		this.del_status = del_status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhonenum() {
		return phonenum;
	}
	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getImage_name() {
		return image_name;
	}
	public void setImage_name(String image_name) {
		this.image_name = image_name;
	}
	@Override
	public String toString() {
		return "PurchaseGdsVO [idx=" + idx + ", BuyerID=" + BuyerID + ", seller_id=" + seller_id + ", goods_name="
				+ goods_name + ", num=" + num + ", price=" + price + ", category=" + category + ", DOP=" + DOP
				+ ", image_name=" + image_name + ", del_status=" + del_status + ", name=" + name + ", phonenum="
				+ phonenum + ", address=" + address + "]";
	}
	
	
}
