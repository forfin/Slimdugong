package th.ac.kmitl.it.slimdugong.database.entity;

public class FoodType {
	
	private Integer foodTypeId;
    private String foodTypeName;
    
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((foodTypeId == null) ? 0 : foodTypeId.hashCode());
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
		FoodType other = (FoodType) obj;
		if (foodTypeId == null) {
			if (other.foodTypeId != null)
				return false;
		} else if (!foodTypeId.equals(other.foodTypeId))
			return false;
		return true;
	}
	public Integer getFoodTypeId() {
		return foodTypeId;
	}
	public void setFoodTypeId(Integer foodTypeId) {
		this.foodTypeId = foodTypeId;
	}
	public String getFoodTypeName() {
		return foodTypeName;
	}
	public void setFoodTypeName(String foodTypeName) {
		this.foodTypeName = foodTypeName;
	}
    
    

}
