package th.ac.kmitl.it.slimdugong.database.entity;

public class Barcode {
	
	public static final String TABLE_NAME = "barcode";
    public static final String COLUMN_NAME_ENTRY_ID = "bar_id";
    public static final String COLUMN_NAME_ENTRY_CODE = "bar_code";
    public static final String COLUMN_NAME_ENTRY_FOOD_ID = "food_id";
	
	private Integer barId;
	private String barCode;
	private Integer foodId;
	public Integer getBarId() {
		return barId;
	}
	public void setBarId(Integer barId) {
		this.barId = barId;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public Integer getFoodId() {
		return foodId;
	}
	public void setFoodId(Integer foodId) {
		this.foodId = foodId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((barId == null) ? 0 : barId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Barcode other = (Barcode) obj;
		if (barId == null) {
			if (other.barId != null)
				return false;
		} else if (!barId.equals(other.barId))
			return false;
		return true;
	}
	
	

}
