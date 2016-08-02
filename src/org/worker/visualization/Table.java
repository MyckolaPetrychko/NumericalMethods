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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Table {

	private static ArrayList<XY> list;
	private static Stage window;

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

	private static void init(Stage stage) {
		window = stage;
		Group group = new Group();
		window.setScene(new Scene(group, 900, 600));
		// group.getChildren().add(initTable());
		window.setTitle("Table view");
		window.setResizable(false);
		window.initOwner(StartPage.getWindow());
		window.show();
	}

	public static void display(ArrayList<XY> list1) {
		list = list1;
		init(new Stage());
	}

	@SuppressWarnings("unchecked")
	public static HBox initTable(ArrayList<XY> list1) {
		list = list1;

		final ObservableList<XY> items = FXCollections.observableList(list);

		TableColumn<XY, Double> xColumn = new TableColumn<XY, Double>();
		xColumn.setText("x");
		xColumn.setCellValueFactory(new PropertyValueFactory<XY, Double>("x"));

		TableColumn<XY, Double> yColumn = new TableColumn<XY, Double>();
		yColumn.setText("y");
		yColumn.setCellValueFactory(new PropertyValueFactory<XY, Double>("y"));

		TableView<XY> table = new TableView<XY>();
		table.setMinWidth(400);
		table.setMinHeight(400);
		table.setMaxHeight(400);
		table.setItems(items);
		table.getColumns().addAll(xColumn, yColumn);
		HBox pane = new HBox(info(), table);
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
		box.setMinHeight(400);
		box.setMinWidth(500);
		box.setAlignment(Pos.BOTTOM_CENTER);
		return box;
	}

}
