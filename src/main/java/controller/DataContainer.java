package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import person.Person;
import person.PersonFactory;

public class DataContainer {

    private ObservableList<Person> personData = FXCollections.observableArrayList();

    public DataContainer() {
        for (int i = 0; i < 8; i++) {
            personData.add(PersonFactory.createPerson());
        }
    }

    public ObservableList<Person> getPersonData() {
        return personData;
    }


}
