import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;

public class EndangeredAnimals extends Animals {
    public boolean endangered;
    private  String health;
    private String age;

    public EndangeredAnimals(String name, String health, String age) {
        this.name = name;
        this.id = id;
        this.health = health;
        this.age = age;
    }
    public String getHealth () {
        return health;
    }
    public String getAge () {
        return age;
    }
    @Override
    public boolean equals(Object otherEndangeredAnimals) {
        if (!(otherEndangeredAnimals instanceof EndangeredAnimals)) {
            return false;
        } else {
            EndangeredAnimals newEndangeredAnimals = (EndangeredAnimals) otherEndangeredAnimals;
            return this.getName().equals(newEndangeredAnimals.getName()) &&
            this.getAge().equals(newEndangeredAnimals.getAge()) &&
            this.getHealth().equals(newEndangeredAnimals.getHealth());
        }
    }
    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO endangered_animals (name, health, age ) VALUES (:name, :health, :age)";
            this.id = (int)
                    con.createQuery(sql, true)
                            .addParameter("name", this.name)
                            .addParameter("age", this.age)
                            .addParameter("health", this.health)
                            .executeUpdate()
                            .getKey();
        }
    }
    public static List<EndangeredAnimals> all () {
              try(Connection con = DB.sql2o.open()) {
                  String sql = "SELECT * FROM endangered_animals;";
                  return con.createQuery(sql)
                          .executeAndFetch(EndangeredAnimals.class);
        }
    }
    public static EndangeredAnimals find(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM endangered_animals WHERE id=:id;";
            EndangeredAnimals EndangeredAnimals = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(EndangeredAnimals.class);
            return EndangeredAnimals;
        }
    }
    public void updateHealth(String health) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "UPDATE endangered_animals SET health=:health WHERE id=:id;";
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("health", health)
                    .executeUpdate();
        }
    }
    public void updateAge(String age) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "UPDATE endangered_animals SET age=:age WHERE id=:id;";
            con.createQuery(sql)
                    .addParameter("age", age)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }
    public List<Sighting> getSightings() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM sightings WHERE animalid=:id;";
            List<Sighting> sighting = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetch(Sighting.class);
            return sighting;
        }
    }
}
