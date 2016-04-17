package model;

import controller.PersonEditDialogController;
import controller.PersonOverviewController;
import controller.RootLayoutController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import person.Person;
import utils.PersonsContainer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.prefs.Preferences;

public class ApplicationHandler {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private PersonsContainer personsContainer = new PersonsContainer();

    public ApplicationHandler(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Address Application");

        this.primaryStage.getIcons().add(new Image("images/icon.png"));

        personsContainer.fillWithRandomPersons(8);

        initRootLayout();

        showPersonOverview();
    }

    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL resource = getClass().getClassLoader().getResource("view/RootLayout.fxml");
            loader.setLocation(resource);
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            RootLayoutController controller = loader.getController();
            controller.setApplication(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = getPersonFilePath();
        if (file != null) {
            loadPersonsDataFromFile(file);
        }
    }

    private void showPersonOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL resource = getClass().getClassLoader().getResource("view/PersonOverview.fxml");
            loader.setLocation(resource);
            AnchorPane personOverview = loader.load();

            rootLayout.setCenter(personOverview);

            PersonOverviewController controller = loader.getController();
            controller.setPersonsContainer(personsContainer);
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showPersonEditDialog(Person person) {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL resource = getClass().getClassLoader().getResource("view/PersonEditDialog.fxml");
            loader.setLocation(resource);
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("EditPerson");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            dialogStage.showAndWait();

            return  controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public File getPersonFilePath() {
        Preferences preferences = Preferences.userNodeForPackage(ApplicationHandler.class);
        String filePath = preferences.get("filepath", null);
        return filePath != null ? new File(filePath) : null;
    }

    public void setPersonsFilePath(File file) {
        Preferences preferences = Preferences.userNodeForPackage(ApplicationHandler.class);
        if (file != null) {
            preferences.put("filepath", file.getPath());
            primaryStage.setTitle("Address Application - " + file.getName());
        } else {
            preferences.remove("filepath");
            primaryStage.setTitle("Address Application");
        }
    }

    public void loadPersonsDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(PersonsContainer.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            this.personsContainer = (PersonsContainer) unmarshaller.unmarshal(file);
            setPersonsFilePath(file);
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Error while loading data from file");
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        }
    }

    public void savePersonsDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(PersonsContainer.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(this.personsContainer, file);

            setPersonsFilePath(file);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Error while saving data to file");
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public PersonsContainer getPersonsContainer() {
        return personsContainer;
    }
}
