package th.ac.kmitl.it.slimdugong.database.entity.local;

public class User {
	
	public static final String KEY_NAME = "name";
	public static final String KEY_SEX = "sex";
	public static final String KEY_CHARACTER = "character";
	
	public static final String SEX_MALE = "male";
	public static final String SEX_FEMALE = "female";
	
	private String name;
	private String sex;
	private int[] character;
	
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
	
}
