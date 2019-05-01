import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class SightingTest {
    @Rule
    public DataBaseRule database = new DataBaseRule();

    @Test
    public void sighting_instantiatesCorrectly_true() {
        Sighting testSighting  = new Sighting ("Cloud Nine", 1, "Wolf");
        assertEquals(true, testSighting instanceof  Sighting);
    }
    @Test
    public void Sighting_instantiateswithname_String () {
        Sighting testSighting = new Sighting("Kendu", 1, "prisca");
        assertEquals("prisca", testSighting.getName());
            }
    @Test
    public void Sighting_instantiatesWithAnimalId_int () {
        Sighting testSighting = new Sighting("Mtaani", 1, "mummy");
        assertEquals(1, testSighting. getAnimalId());
            }
    @Test
    public void save_returnsTrueIfDescriptionsAretheSame () {
    Sighting testSighting = new Sighting("Grabbo", 1, "Baba Yao");
    testSighting.save();
    assertTrue (Sighting.all().get(0).equals(testSighting));
    }
    @Test
    public void all_returnsAllInstancesOfSightings_true() {
        Sighting firstSighting = new Sighting("Juja", 1, "Bazenga");
        firstSighting.save();
        Sighting secondSighting = new Sighting("Jujamaica", 1, "Bazengas");
        secondSighting.save();
        assertEquals(true, Sighting.all().get(0).equals(firstSighting));
        assertEquals(true, Sighting.all().get(1).equals(secondSighting));
    }
    @Test
    public void find_returnsSightingWithSameId_secondSighting() {
        Sighting firstSighting = new Sighting("Mummy", 1,"Spurs");
        firstSighting.save();
        Sighting secondSighting = new Sighting("Mummy", 1,"Spurs");
        secondSighting.save();
        assertEquals(Sighting.find(secondSighting.getId()), secondSighting);
    }
    @Test
    public void save_savesAnimalIdIntoDB_true() {
        Animal testAnimal = new Animal ("Wolf");
        testAnimal.save();
        Sighting testSighting = new Sighting("Riverside", testAnimal.getId(), "Bill");
        testSighting.save();
        Sighting savedSighting = Sighting.find(testSighting.getId());
        assertEquals (savedSighting.getAnimalId(), testAnimal.getId());
    }
}