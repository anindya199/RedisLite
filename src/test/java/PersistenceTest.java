
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersistenceTest {

    @Test
    public void testPersistence() {

        //two storages
        Database db1 = new Database();
        Database db2 = new Database();
        //make a persistence object
        Persistence p1 = new Persistence();

        //set values in database 1
        db1.SET("name",new Value(ValueType.STRING,"Anindya"));
        db1.SET("age",new Value(ValueType.INTEGER,26));
        db1.SET("price",new Value(ValueType.DOUBLE,3.5));

        //now create file
        p1.createFile();
        //write data from storage to file
        p1.writeToFile(db1.getStorage());
        //read data from file to 2nd storage
        p1.readFromFile(db2.getStorage());

        //check the values of storage1 and storage 2
        assertEquals("Anindya",db2.GET("name").getValue());
        assertEquals(26,db2.GET("age").getValue());
        assertEquals(3.5,db2.GET("price").getValue());
    }
}
