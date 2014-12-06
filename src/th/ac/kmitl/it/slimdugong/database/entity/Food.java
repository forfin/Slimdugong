package th.ac.kmitl.it.slimdugong.database.entity;

public class Food {
	
	public static final String TABLE_NAME = "food";
    public static final String COLUMN_NAME_ENTRY_ID = "food_id";
    public static final String COLUMN_NAME_ENTRY_NAME = "food_name";
    public static final String COLUMN_NAME_ENTRY_CAL = "food_cal";
    public static final String COLUMN_NAME_ENTRY_FOODTYPE_ID = "food_type_id";
	
	private Integer foodId;
    private String foodName;
    private Integer foodCal;
	private Integer foodTypeId;
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((foodId == null) ? 0 : foodId.hashCode());
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
		Food other = (Food) obj;
		if (foodId == null) {
			if (other.foodId != null)
				return false;
		} else if (!foodId.equals(other.foodId))
			return false;
		return true;
	}

	public Integer getFoodId() {
		return foodId;
	}
	public void setFoodId(Integer foodId) {
		this.foodId = foodId;
	}
	public String getFoodName() {
		return foodName;
	}
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	public Integer getFoodCal() {
		return foodCal;
	}
	public void setFoodCal(Integer foodCal) {
		this.foodCal = foodCal;
	}
	public Integer getFoodTypeId() {
		return foodTypeId;
	}
	public void setFoodTypeId(Integer foodTypeId) {
		this.foodTypeId = foodTypeId;
	}
    

}
