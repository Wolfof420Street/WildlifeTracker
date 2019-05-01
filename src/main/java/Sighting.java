import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Sighting {
    private String rangerName;
    private int animalId;
    private int id;

    @Override
    public boolean equals(Object otherSighting){
        if (!(otherSighting instanceof Sighting)) {
            return false;
        } else {
            Sighting newSighting = (Sighting) otherSighting;
            return this.getName().equals(newSighting.getName()) &&
                    this.getAnimalId() == newSighting.getAnimalId();
        }
    }
    public Sighting(String location, int animalId, String rangerName) {
    this.rangerName = rangerName;
    this.animalId = animalId;
    }
    public static List<Sighting> all() {
        String sql = "SELECT * FROM sightings";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Sighting.class);
        }
    }
    public static Sighting find(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM sightings where id=:id";
            Sighting sighting = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Sighting.class);
            return sighting;
        }
    }
    public String getName(){
        return rangerName;
    }

    public int getId () {
        return id;
    }

    public int getAnimalId(){
        return animalId;
    }

    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO sightings (rangerName, animalid) VALUES (:rangerName, :animalId)";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("rangerName", this.rangerName)
                    .addParameter("animalId", this.animalId)
                    .executeUpdate()
                    .getKey();

        }
    }

}
