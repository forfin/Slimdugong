package th.ac.kmitl.it.slimdugong.database.entity;

public class Athletic {
	
	private Integer AthId;
	private String AthName;
	private Integer AthBph;
	public Integer getAthId() {
		return AthId;
	}
	public void setAthId(Integer athId) {
		AthId = athId;
	}
	public String getAthName() {
		return AthName;
	}
	public void setAthName(String athName) {
		AthName = athName;
	}
	public Integer getAthBph() {
		return AthBph;
	}
	public void setAthBph(Integer athBph) {
		AthBph = athBph;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((AthId == null) ? 0 : AthId.hashCode());
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
		Athletic other = (Athletic) obj;
		if (AthId == null) {
			if (other.AthId != null)
				return false;
		} else if (!AthId.equals(other.AthId))
			return false;
		return true;
	}

}
