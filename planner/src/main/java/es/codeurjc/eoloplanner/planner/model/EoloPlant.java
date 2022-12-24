package es.codeurjc.eoloplanner.planner.model;

public class EoloPlant {
	private Long id;

	private String city;
	private String planning;

	public EoloPlant(Long id, String city, String planning) {
		this.id = id;
		this.city = city;
		this.planning = planning;
	}

	public EoloPlant() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPlanning() {
		return planning;
	}

	public void setPlanning(String planning) {
		this.planning = planning;
	}

	@Override
	public String toString() {
		return "EoloPlant{" +
				"id=" + id +
				", city='" + city + '\'' +
				", planning='" + planning + '\'' +
				'}';
	}
}
