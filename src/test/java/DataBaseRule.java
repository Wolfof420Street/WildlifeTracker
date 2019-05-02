import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DataBaseRule extends ExternalResource {
    @Override
    protected void before() {
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker_test", "wolf", "WolfGang");
    }


    @Override
protected void after() {
    try(Connection con = DB.sql2o.open()) {
        String deleteAnimalsQuery = "DELETE FROM animals *;";
        String deleteSigtingQuery = "DELETE FROM sightings*;";
        String deleteEndangeredAnimalsQuery ="DELETE FROM endangered_animals*;";
        String deleteRangersQuery ="DELETE FROM rangers*;";
        String deleteJoinsQuery = "DELETE FROM rangers_sightings*;";
        con.createQuery(deleteAnimalsQuery).executeUpdate();
        con.createQuery(deleteSigtingQuery).executeUpdate();
        con.createQuery(deleteEndangeredAnimalsQuery).executeUpdate();
        con.createQuery(deleteRangersQuery).executeUpdate();
        con.createQuery(deleteJoinsQuery).executeUpdate();

    }
}
}
