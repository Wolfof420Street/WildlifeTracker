import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;
import java.util.List;


public class RangerTest {
    @Rule
    public DataBaseRule database = new DataBaseRule();

    @Test
    public void Ranger_instantiatesCorrectly_true() {
        Ranger testRanger = new Ranger("Wolf", "254");
        assertEquals(true, testRanger instanceof Ranger);
    }
    @Test
    public void getName_RangerInstantiatesWithName_true() {
        Ranger testRanger = new Ranger("Wolf", "567");
        assertEquals("Wolf", testRanger.getName());
    }
    @Test
    public void getName_RangerInstantiatesWithDescription_String() {
        Ranger testRanger = new Ranger("Wolf", "254");
        assertEquals("254", testRanger.getDescription());
    }
    @Test
    public void equals_returnsTrueIfNameAndDescriptionAreSame_true() {
        Ranger testRanger = new Ranger("Fire Enthusiasts", "Flame on!");
        Ranger anotherRanger = new Ranger("Fire Enthusiasts", "Flame on!");
        assertTrue(testRanger.equals(anotherRanger));
    }
    @Test
    public void save_insertsObjectIntoDatabase_Ranger() {
        Ranger testRanger = new Ranger("Fire Enthusiasts", "Flame on!");
        testRanger.save();
        assertEquals(true, Ranger.all().get(0).equals(testRanger));
    }
    @Test
    public void all_returnsAllInstancesOfRanger_true() {
        Ranger firstRanger = new Ranger("Fire Enthusiasts", "Flame on!");
        firstRanger.save();
        Ranger secondRanger = new Ranger("Water Enthusiasts", "Lovers of all things water monsters!");
        secondRanger.save();
        assertEquals(true, Ranger.all().get(0).equals(firstRanger));
        assertEquals(true, Ranger.all().get(1).equals(secondRanger));
    }
    @Test
    public void addSighting_addsSightingToRanger() {
        Ranger testRanger = new Ranger("Fire Enthusiasts", "Flame on!");
        testRanger.save();
        Sighting testSighting = new Sighting("Henry", 1, "Wolf" );
        testSighting.save();
        testRanger.addSighting(testSighting);
        Sighting savedSighting = testRanger.getSightings().get(0);
        assertTrue(testSighting.equals(savedSighting));
    }
    @Test
    public void getSightings_returnsAllSightings_List() {
        Ranger testRanger = new Ranger("Fire Enthusiasts", "Flame on!");
        testRanger.save();
        Sighting testSighting = new Sighting("Henry", 1,"fa");
        testSighting.save();
        testRanger.addSighting(testSighting);
        List savedSightings = testRanger.getSightings();
        assertEquals(savedSightings.size(), 1);
    }
    @Test
    public void delete_deletesRanger_true() {
        Ranger testRanger = new Ranger("Fire Enthusiasts", "Flame on!");
        testRanger.save();
        testRanger.delete();
        assertEquals(0, Ranger.all().size());
    }
    @Test
    public void delete_deletesAllSightingsAndCommunitiesAssociations() {
        Ranger testRanger = new Ranger("Fire Enthusiasts", "Flame on!");
        testRanger.save();
        Sighting testSighting = new Sighting("Henry", 1, "Wolf");
        testSighting.save();
        testRanger.addSighting(testSighting);
        testRanger.delete();
        assertEquals(0, testSighting.getRangers().size());
    }
}
