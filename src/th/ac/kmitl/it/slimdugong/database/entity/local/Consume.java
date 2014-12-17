package th.ac.kmitl.it.slimdugong.database.entity.local;

import java.util.Date;

public class Consume {
	
	private Integer consumeId;
	private Integer foodId;
	private Date consumeTime;
	private Integer foodEnergy;
	private String foodName;
	
	public Date getConsumeTime() {
		return consumeTime;
	}

	public void setConsumeTime(Date consumeTime) {
		this.consumeTime = consumeTime;
	}

	public Consume(Integer foodId, Date consumeTime){
		this.foodId = foodId;
		this.consumeTime = consumeTime;
	}
	
	public Consume() {
		// TODO Auto-generated constructor stub
	}

	public Integer getConsumeId() {
		return consumeId;
	}
	public void setConsumeId(Integer consumeId) {
		this.consumeId = consumeId;
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
		result = prime * result
				+ ((consumeId == null) ? 0 : consumeId.hashCode());
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
		Consume other = (Consume) obj;
		if (consumeId == null) {
			if (other.consumeId != null)
				return false;
		} else if (!consumeId.equals(other.consumeId))
			return false;
		return true;
	}

	public Integer getFoodEnergy() {
		return foodEnergy;
	}

	public void setFoodEnergy(Integer foodEnergy) {
		this.foodEnergy = foodEnergy;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
}
