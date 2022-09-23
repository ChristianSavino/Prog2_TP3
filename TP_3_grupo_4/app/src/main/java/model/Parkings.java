package model;

public class Parkings {

    private int id;
    private String patent;
    private int time;
    private int user_id;

    public Parkings(int id, String patent, int time, int user_id) {
        this.id = id;
        this.patent = patent;
        this.time = time;
        this.user_id = user_id;
    }

    public Parkings() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPatent() {
        return patent;
    }

    public void setPatent(String patent) {
        this.patent = patent;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Parkings{" +
                "id=" + id +
                ", patent='" + patent + '\'' +
                ", time=" + time +
                ", user_id=" + user_id +
                '}';
    }
}
