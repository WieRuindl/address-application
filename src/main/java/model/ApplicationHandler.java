package model;

import controller.PersonEditDialogController;
import controller.PersonOverviewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import person.Person;
import utils.DataContainer;

import java.io.IOException;
import java.net.URL;

public class ApplicationHandler {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private DataContainer dataContainer = new DataContainer();

    public ApplicationHandler(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Address Application");

        this.primaryStage.getIcons().add(new Image("images/icon.png"));

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
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
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
            controller.setDataContainer(dataContainer);
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

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public DataContainer getDataContainer() {
        return dataContainer;
    }
}
