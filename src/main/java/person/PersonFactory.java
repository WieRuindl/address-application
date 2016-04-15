package person;

import org.fluttercode.datafactory.impl.DataFactory;

import java.time.ZoneId;

public class PersonFactory {
    private static DataFactory dataFactory = new DataFactory();

    public static Person createPerson() {
        return new Person(
                dataFactory.getFirstName(),
                dataFactory.getLastName(),
                dataFactory.getStreetName(),
                dataFactory.getNumberBetween(1000, 9999),
                dataFactory.getCity(),
                dataFactory.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        );
    }
}
