package th.ac.kmitl.it.slimdugong.database.entity.local;

import java.util.Date;

public class User {
	
	public static final String KEY_NAME = "name";
	public static final String KEY_SEX = "sex";
	public static final String KEY_CHARACTER = "character";
	
	public static final String KEY_HEIGHT = "height";
	public static final String KEY_WEIGHT = "weight";
	
	public static final String SEX_MALE = "male";
	public static final String SEX_FEMALE = "female";
	
	private String name;
	private String sex;
	private int[] character;
	private Integer height;
	private Integer weight;
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int[] getCharacter() {
		return character;
	}
	public void setCharacter(int[] character) {
		this.character = character;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
}
