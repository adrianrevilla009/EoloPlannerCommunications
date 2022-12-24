package es.codeurjc.eoloplanner.planner.model;

public class EoloPlantResponse {
    private Long id;
    private String city;
    private String planning;
    private int progress;
    private Boolean completed;

    public EoloPlantResponse(Long id, String city, String planning, int progress, Boolean completed) {
        this.id = id;
        this.city = city;
        this.planning = planning;
        this.progress = progress;
        this.completed = completed;
    }

    public EoloPlantResponse() {
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

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

}
