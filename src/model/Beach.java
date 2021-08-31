package model;

public class Beach {
	
	private int id;
	private String name;
	private int windDirection;
	private int waveDirection;
	
	public Beach() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(int windDirection) {
		this.windDirection = windDirection;
	}

	public int getWaveDirection() {
		return waveDirection;
	}

	public void setWaveDirection(int waveDirection) {
		this.waveDirection = waveDirection;
	}
	

}
