import org.sql2o.Connection;
import org.sql2o.*;

import java.util.ArrayList;
import java.util.List;

public class Animal {
    private String name;
    private int id;



    public Animal (String name) {
        this.name = name;

    }
    public String getName () {
        return name;
    }
    @Override
    public boolean equals(Object otherAnimal){
        if (!(otherAnimal instanceof Animal)) {
            return false;
        } else {
            Animal newAnimal = (Animal) otherAnimal;
            return this.getName().equals(newAnimal.getName());
        }
    }
    public static List <Animal> all () {
        String sql ="SELECT * FROM animals";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Animal.class);
        }
    }
    public static Animal find(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM animals where id=:id";
            Animal animal= con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Animal.class);
            return animal;
        }
    }
    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO animals (name) VALUES (:name)";
            this.id = (int)
                    con.createQuery(sql, true)
                    .addParameter("name", this.name)
                    .executeUpdate()
                    .getKey();
        }
    }
    public int getId() {
        return id;
    }
    public List <Sighting> getSightings() {
        try(Connection con = DB.sql2o.open()){
            String sql = "SELECT * FROM sightings where animalid=:id";
            return  con.createQuery(sql)
                    .addParameter("id", this.id)
                    .executeAndFetch(Sighting.class);
        }
    }
    }
