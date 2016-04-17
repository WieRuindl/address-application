package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import person.Person;
import person.PersonFactory;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "persons")
public class PersonsContainer {

    private ObservableList<Person> personsData;

    public PersonsContainer() {
        personsData = FXCollections.observableArrayList();
    }

    public void fillWithRandomPersons(int count) {
        for (int i = 0; i < count; i++) {
            personsData.add(PersonFactory.createPerson());
        }
    }

    @XmlElement(name = "person")
    public ObservableList<Person> getPersonsData() {
        return personsData;
    }

    public void addPerson(Person person) {
        personsData.add(person);
    }

    public void clear() {
        personsData.clear();
    }
}
