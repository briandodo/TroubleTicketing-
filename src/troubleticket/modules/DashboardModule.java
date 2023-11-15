package troubleticket.modules;

import java.text.DecimalFormat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import troubleticket.daos.DashboardDAOAurora;

public class DashboardModule implements ITabPaneModule {

	VBox vBox = null;

	public VBox start() {

		DecimalFormat df = new DecimalFormat("0.00");
		DashboardDAOAurora dashboardDAOAurrora = new DashboardDAOAurora();
		int noOfOpened = dashboardDAOAurrora.getNoOfOpened();
		int noOfAssigned = dashboardDAOAurrora.getNoOfAssigned();
		int noOfResolved = dashboardDAOAurrora.getNoOfResolved();
		int noOfClosed = dashboardDAOAurrora.getNoOfClosed();
		int noOfDeleted = dashboardDAOAurrora.getNoOfDeleted();

		int total = noOfOpened + noOfAssigned + noOfResolved + noOfClosed + noOfDeleted;
		double portionOfOpened = ((double) noOfOpened / total) * 100;
		double portionOfAssigned = ((double) noOfAssigned / total) * 100;
		double portionOfResolved = ((double) noOfResolved / total) * 100;
		double portionOfClosed = ((double) noOfClosed / total) * 100;
		double portionOfDeleted = ((double) noOfDeleted / total) * 100;

		String opened = "Opened " + df.format(portionOfOpened) + "%";
		String assigned = "Assigned " + df.format(portionOfAssigned) + "%";
		String resolved = "Resloved " + df.format(portionOfResolved) + "%";
		String closed = "Closed " + df.format(portionOfClosed) + "%";
		String deleted = "Deleted " + df.format(portionOfDeleted) + "%";

		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data(opened, noOfOpened), new PieChart.Data(assigned, noOfAssigned),
				new PieChart.Data(resolved, noOfResolved), new PieChart.Data(closed, noOfClosed),
				new PieChart.Data(deleted, noOfDeleted));
		PieChart pieChart = new PieChart(pieChartData);
		pieChart.setTitle("Ticket Status");
		pieChart.setClockwise(true);
		pieChart.setLabelLineLength(50);
		pieChart.setLabelsVisible(true);
		pieChart.setStartAngle(180);
		
		DecimalFormat df2 = new DecimalFormat("0.00");
		int noOfEmergency = dashboardDAOAurrora.getNoOfEmergency();
		int noOfHigh = dashboardDAOAurrora.getNoOfHigh();
		int noOfLow = dashboardDAOAurrora.getNoOfLow();
		int noOfNormal = dashboardDAOAurrora.getNoOfNormal();

		int total2 = noOfEmergency + noOfHigh + noOfLow + noOfNormal;
		double portionOfEmergency = ((double) noOfEmergency / total2) * 100;
		double portionOfHigh = ((double) noOfHigh / total2) * 100;
		double portionOfLow = ((double) noOfLow / total2) * 100;
		double portionOfNormal = ((double) noOfNormal / total2) * 100;

		String emergency = "Emergency " + df2.format(portionOfEmergency) + "%";
		String high = "High " + df2.format(portionOfHigh) + "%";
		String low = "Low " + df2.format(portionOfLow) + "%";
		String normal = "Normal " + df2.format(portionOfNormal) + "%";
		

		ObservableList<PieChart.Data> pieChartData2 = FXCollections.observableArrayList(
				new PieChart.Data(emergency, noOfEmergency), new PieChart.Data(high, noOfHigh),
				new PieChart.Data(low, noOfLow), new PieChart.Data(normal, noOfNormal));
		PieChart pieChart2 = new PieChart(pieChartData2);
		pieChart2.setTitle("Ticket Priority");
		pieChart2.setClockwise(true);
		pieChart2.setLabelLineLength(50);
		pieChart2.setLabelsVisible(true);
		pieChart2.setStartAngle(180);
		
		CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("SLA");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number");

        BarChart barChart = new BarChart(xAxis, yAxis);

        XYChart.Series dataSeries1 = new XYChart.Series();
        XYChart.Series dataSeries2 = new XYChart.Series();
        XYChart.Series dataSeries3 = new XYChart.Series();
        XYChart.Series dataSeries4 = new XYChart.Series();
        
        dataSeries1.setName("Within 1 hour");
        dataSeries2.setName("Within 1 day");
        dataSeries3.setName("Within 1 week");
        dataSeries4.setName("Within 30 days");
        
        int noOfHour = dashboardDAOAurrora.getNoOfHour();
        int noOfDay = dashboardDAOAurrora.getNoOfDay();
        int noOfWeek = dashboardDAOAurrora.getNoOfWeek();
        int noOfThirty = dashboardDAOAurrora.getNoOfThirty();
        

        dataSeries1.getData().add(new XYChart.Data("", noOfHour));
        dataSeries2.getData().add(new XYChart.Data(""  , noOfDay));
        dataSeries3.getData().add(new XYChart.Data(""  , noOfWeek));
        dataSeries4.getData().add(new XYChart.Data(""  , noOfThirty));

        barChart.getData().add(dataSeries1);
        barChart.getData().add(dataSeries2);
        barChart.getData().add(dataSeries3);
        barChart.getData().add(dataSeries4);

		vBox = new VBox(pieChart, pieChart2, barChart);

		return vBox;
	}

}
