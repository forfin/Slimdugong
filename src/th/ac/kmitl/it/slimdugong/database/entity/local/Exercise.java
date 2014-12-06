package th.ac.kmitl.it.slimdugong.database.entity.local;

import java.util.Date;

public class Exercise {
	
	private Integer exerId;
	private Integer AthId;
	private Integer enegyBurn;
	private Integer exerDuration;
	private Date exerTime;
	public Integer getExerId() {
		return exerId;
	}
	public Integer getExerDuration() {
		return exerDuration;
	}
	public void setExerDuration(Integer exerDuration) {
		this.exerDuration = exerDuration;
	}
	public void setExerId(Integer exerId) {
		this.exerId = exerId;
	}
	public Integer getAthId() {
		return AthId;
	}
	public void setAthId(Integer athId) {
		AthId = athId;
	}
	public Integer getEnegyBurn() {
		return enegyBurn;
	}
	public void setEnegyBurn(Integer enegyBurn) {
		this.enegyBurn = enegyBurn;
	}
	public Date getExerTime() {
		return exerTime;
	}
	public void setExerTime(Date exerTime) {
		this.exerTime = exerTime;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((exerId == null) ? 0 : exerId.hashCode());
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
		Exercise other = (Exercise) obj;
		if (exerId == null) {
			if (other.exerId != null)
				return false;
		} else if (!exerId.equals(other.exerId))
			return false;
		return true;
	}	

}
