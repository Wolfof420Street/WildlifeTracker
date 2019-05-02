import org.junit.*;
import static org.junit.Assert.*;
import org.junit.rules.ExternalResource;
import org.sql2o.*;
import java.util.Arrays;

public class AnimalTest {
    @Rule
    public DataBaseRule dataBase = new DataBaseRule();

    @Test
    public void animal_instantiatesCOrrectly_true() {
        Animal testAnimal = new Animal("Lion");
        assertEquals(true, testAnimal instanceof Animal);
    }

    @Test
    public void getName_animalInstantiatesWithName_Lion() {
        Animal testAnimal = new Animal("Lion");
        assertEquals("Lion", testAnimal.getName());
    }

    @Test
    public void equals_returnsTrueIfNamesAreSame_true() {
        Animal firstAnimal = new Animal("Lion");
        Animal otherAnimal = new Animal("Lion");
        assertTrue(firstAnimal.equals(otherAnimal));


    }

    @Test
    public void all_returnsAllInstancesOfPerson_true() {
        Animal firstAnimal = new Animal("Wolf");
        firstAnimal.save();
        Animal secondAnimal = new Animal("Hare");
        secondAnimal.save();
        assertEquals(true, Animal.all().get(0).equals(firstAnimal));
        assertEquals(true, Animal.all().get(1).equals(secondAnimal));
    }

    @Test
    public void save_insertsObjectIntoDatabase_Animal() {
        Animal testAnimal = new Animal("Lion");
        testAnimal.save();
        assertTrue(Animal.all().get(0).equals(testAnimal));
    }

    @Test
    public void save_assignsIdToObject() {
        Animal testAnimal = new Animal("Lion");
        testAnimal.save();
        Animal savedAnimal = Animal.all().get(0);
        assertEquals(testAnimal.getId(), savedAnimal.getId());
    }

    @Test
    public void find_returnsAnimalWithSameId_secondPerson() {
        Animal firstAnimal = new Animal("Wolf");
        firstAnimal.save();
        Animal secondAnimal = new Animal("Lion");
        secondAnimal.save();
        assertEquals(Animal.find(secondAnimal.getId()), secondAnimal);
    }

    @Test
    public void getSightings_retrievesAllSightingsFromDatabase_sightingList() {
        Animal testAnimal = new Animal("Henry");
        testAnimal.save();
        Sighting firstSighting = new Sighting("Bubbles", testAnimal.getId(), "Bill");
        firstSighting.save();
        Sighting secondSighting = new Sighting("Spud", testAnimal.getId(), "Mummy");
        secondSighting.save();
        Sighting[] sightings = new Sighting[]{firstSighting, secondSighting};
        assertTrue(testAnimal.getSightings().containsAll(Arrays.asList(sightings)));
    }
}