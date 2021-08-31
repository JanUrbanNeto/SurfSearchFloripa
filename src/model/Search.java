package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Search {
	
	private int id;
	private int idUser;
	private int windDirection;
	private int waveDirection;
	private Timestamp timeStamp;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
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

	public Timestamp getTimestamp() {
		return timeStamp;
	}

	public void setTimestamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}
	
}
