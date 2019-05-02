import org.junit.Test;
import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.text.DateFormat;

public class EndangeredAnimalsTest {
    @Rule
    public DataBaseRule database = new DataBaseRule();

    @Test
    public void endangeredAnimal_instantiatesCorrectlyTrue() {
        EndangeredAnimals testEndangeredAnimals = new EndangeredAnimals("Koala", "healthy", "adult");
    }
    @Test
    public void endangeredAnimal_instantiatesWithHealth_true () {
        EndangeredAnimals testEndangeredAnimals = new EndangeredAnimals("wolf", "Sick", "adult");
        assertEquals("Sick", testEndangeredAnimals.getHealth());
    }
    @Test
    public void endageredAnimal_returnsAgeOfTheAnimal_true() {
        EndangeredAnimals testEndangeredAnimals = new EndangeredAnimals("wolf", "Sick", "adult");
        assertEquals("adult", testEndangeredAnimals.getAge());
    }
    @Test
    public void save_assignsIdANdSavesObjectToDatabase () {
        EndangeredAnimals testEndangeredAnimals =new EndangeredAnimals("wolf", "healthy", "adult");
        testEndangeredAnimals.save();
        EndangeredAnimals savedEndangeredAnimals = EndangeredAnimals.all().get(0);
        assertEquals(testEndangeredAnimals.getId(), savedEndangeredAnimals.getId());
}
    @Test
    public void returnsAllInstancesOfEndangeredANimals_true() {
        EndangeredAnimals firstEndangeredAnimals = new EndangeredAnimals("wolf", "sick", "adult");
        firstEndangeredAnimals.save();
        EndangeredAnimals secondEndangeredAnimals = new EndangeredAnimals("Bill", "sick", "adult");
        secondEndangeredAnimals.save();
        assertEquals(true, EndangeredAnimals.all().get(0).equals(firstEndangeredAnimals));
        assertEquals(true, EndangeredAnimals.all().get(1).equals(secondEndangeredAnimals));
    }

    @Test
    public void find_returnsAnimalWithSameId_secondAnimal() {
        EndangeredAnimals firstEndangeredAnimals = new EndangeredAnimals("wolf", "sick", "adult");
        firstEndangeredAnimals.save();
        EndangeredAnimals secondEndangeredAnimals = new EndangeredAnimals("wolf", "sick", "adult");
        secondEndangeredAnimals.save();
        assertEquals(EndangeredAnimals.find(secondEndangeredAnimals.getId()), secondEndangeredAnimals);
    }
    @Test
    public void update_updatesHealthAttribute_true() {
        EndangeredAnimals testEndangeredAnimals = new EndangeredAnimals("wolf", "sick", "adult");
        testEndangeredAnimals.save();
        testEndangeredAnimals.updateHealth("ill");
        assertEquals("ill", EndangeredAnimals.find(testEndangeredAnimals.getId()).getHealth());
    }
    @Test
    public void update_updatesAgeAttribute_true() {
        EndangeredAnimals testEndangeredAnimals = new EndangeredAnimals("wolf", "sick", "adult");
        testEndangeredAnimals.save();
        testEndangeredAnimals.updateAge("adult");
        assertEquals("adult", EndangeredAnimals.find(testEndangeredAnimals.getId()).getAge());
    }
    @Test
    public void getSightings_retrievesAllSightingsFromDatabase_sightingList() {
        EndangeredAnimals testEndangeredAnimals = new EndangeredAnimals("Hair", "healthy", "adult");
        testEndangeredAnimals.save();
        Sighting firstSighting = new Sighting("Bubbles", testEndangeredAnimals.getId(), "Bill");
        firstSighting.save();
        Sighting secondSighting = new Sighting("Spud", testEndangeredAnimals.getId(), "Mummy");
        secondSighting.save();
        Sighting[] sightings = new Sighting[]{firstSighting, secondSighting};
        assertTrue(testEndangeredAnimals.getSightings().containsAll(Arrays.asList(sightings)));
    }
    }

