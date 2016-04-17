package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import person.Person;

import java.text.DateFormatSymbols;
import java.util.Locale;

public class BirthdayStatisticsController {

    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private CategoryAxis xAxis;

    private ObservableList<String> months = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        barChart.setTitle("123");
        barChart.setAccessibleHelp("134");
        barChart.setAccessibleRoleDescription("1435");
        barChart.setAccessibleText("456");
        barChart.setId("567");

        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        this.months.addAll(months);

        xAxis.setCategories(this.months);
    }

    public void setPersonData(ObservableList<Person> persons) {
        int[] monthCounter = new int[months.size()];
        for (Person person : persons) {
            int month = person.getBirthday().getMonthValue() - 1;
            monthCounter[month]++;
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        for (int i = 0; i < monthCounter.length; i++) {
            series.getData().add(new XYChart.Data<>(months.get(i), monthCounter[i]));
        }

        barChart.getData().add(series);
    }
}
