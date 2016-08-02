package org.worker.visualization;

import java.util.ArrayList;

import org.worker.data.XY;
import org.worker.main.StartPage;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GraphicTable {

	private static Stage graphic;
	private static ArrayList<XY> list;
	private static String name;

	private static StringProperty s1 = new SimpleStringProperty();
	private static StringProperty s2 = new SimpleStringProperty();
	private static StringProperty s3 = new SimpleStringProperty();
	private static StringProperty s4 = new SimpleStringProperty();

	private static Text minText;
	private static Text maxText;
	private static Text xText;
	private static Text yText;
	private static TextField minField;
	private static TextField maxField;
	private static TextField minField1;
	private static TextField maxField1;

	public static void init(Stage stage) {
		graphic = stage;
		Group group = new Group();
		graphic.setScene(new Scene(group, 900, 600));
		group.getChildren().addAll(createChart());
		graphic.setResizable(false);
		graphic.setTitle("Table + Graphic");
		graphic.initOwner(StartPage.getWindow());
		graphic.show();

		BorderPane pane = new BorderPane();
		pane.setLeft(createChart());
		pane.setCenter(initTable());
		pane.setBottom(info());

		group.getChildren().add(pane);
	}

	public static VBox createChart() {
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		LineChart<Number, Number> chart = new LineChart<Number, Number>(xAxis, yAxis);

		// setup chart
		chart.setTitle("Chart");
		xAxis.setLabel("x");
		yAxis.setLabel("y");

		// add data
		XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
		series.setName(name);

		for (int i = 0; i < list.size(); i++) {
			series.getData().add(new XYChart.Data<Number, Number>(list.get(i).getX(), list.get(i).getY()));
		}

		chart.getData().add(series);
		chart.setAnimated(true);
		chart.setMinSize(600, 250);
		chart.setMaxSize(600, 250);
		chart.legendVisibleProperty().set(false);

		VBox root = new VBox(chart);
		return root;
	}

	@SuppressWarnings("unchecked")
	private static VBox initTable() {
		final ObservableList<XY> items = FXCollections.observableList(list);

		TableColumn<XY, Double> xColumn = new TableColumn<XY, Double>();
		xColumn.setText("x");
		xColumn.setCellValueFactory(new PropertyValueFactory<XY, Double>("x"));

		TableColumn<XY, Double> yColumn = new TableColumn<XY, Double>();
		yColumn.setText("y");
		yColumn.setCellValueFactory(new PropertyValueFactory<XY, Double>("y"));

		TableView<XY> table = new TableView<XY>();
		table.setMinWidth(300);
		table.setMinHeight(280);
		table.setMaxHeight(280);
		table.setItems(items);
		table.getColumns().addAll(xColumn, yColumn);
		VBox pane = new VBox(table);
		return pane;
	}

	private static HBox info() {
		double minX = list.get(0).getX(), minY = list.get(0).getY(), maxX = list.get(0).getX(),
				maxY = list.get(0).getY();
		for (int i = 0; i < list.size(); i++) {
			if (minX > list.get(i).getX())
				minX = list.get(i).getX();
			if (maxX < list.get(i).getX())
				maxX = list.get(i).getX();

			if (minY > list.get(i).getY())
				minY = list.get(i).getY();
			if (maxY < list.get(i).getY())
				maxY = list.get(i).getY();
		}
		s1.set(Double.toString(minX));
		s2.set(Double.toString(maxX));
		s3.set(Double.toString(minY));
		s4.set(Double.toString(maxY));

		minText = new Text("Min");
		maxText = new Text("Max");
		xText = new Text("x");
		yText = new Text("y");
		minText.setFill(Color.WHITE);
		xText.setFill(Color.WHITE);
		yText.setFill(Color.WHITE);
		maxText.setFill(Color.WHITE);
		minField = new TextField();
		maxField = new TextField();
		minField1 = new TextField();
		maxField1 = new TextField();

		minField.setEditable(false);
		minField.textProperty().bindBidirectional(s1);
		maxField.setEditable(false);
		maxField.textProperty().bindBidirectional(s2);
		minField1.setEditable(false);
		minField1.textProperty().bindBidirectional(s3);
		maxField1.setEditable(false);
		maxField1.textProperty().bindBidirectional(s4);

		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(15);
		GridPane.setConstraints(new Text(""), 0, 0);
		GridPane.setConstraints(minText, 1, 0);
		GridPane.setConstraints(maxText, 2, 0);
		GridPane.setConstraints(xText, 0, 1);
		GridPane.setConstraints(minField, 1, 1);
		GridPane.setConstraints(maxField, 2, 1);
		GridPane.setConstraints(yText, 0, 2);
		GridPane.setConstraints(minField1, 1, 2);
		GridPane.setConstraints(maxField1, 2, 2);
		grid.getChildren().addAll(new Text(""), minText, maxText, xText, minField, maxField, yText, minField1,
				maxField1);

		HBox box = new HBox(grid);
		box.setMinHeight(100);
		box.setMaxHeight(100);
		box.setMinWidth(500);
		box.setAlignment(Pos.BASELINE_CENTER);
		return box;
	}

	public static VBox display(ArrayList<XY> list1, String name1) {
		list = list1;
		name = name1;
		HBox root = new HBox(createChart(), initTable());

		return new VBox(root, info());
	}

	public static void draw(ArrayList<XY> list1, String name1) {
		list = list1;
		name = name1;
		init(new Stage());
	}

}
