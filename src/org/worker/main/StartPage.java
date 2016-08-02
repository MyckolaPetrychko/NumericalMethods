package org.worker.main;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import org.worker.data.XY;
import org.worker.menu.MenuItem;
import org.worker.menu.TopMenu;
import org.worker.visualization.Graphic;
import org.worker.visualization.GraphicTable;
import org.worker.visualization.Table;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class StartPage extends Application {

	private double WIDTH = 1300, HEIGHT = 700;

	private static Stage window;

	private ArrayList<XY> list;

	private File fileName;

	@Override
	public void start(Stage stage) {

		list = new ArrayList<XY>();

		Group root = new Group();

		final Scene scene = new Scene(root, 1300, 700);
		scene.getStylesheets().add("org/worker/style/style.css");

		window = stage;
		window.setScene(scene);
		window.show();
		window.setTitle("Start");
		window.setResizable(false);

		window.getIcons().add(new Image("file:src/org/worker/resources/icon.png"));
		window.setOnCloseRequest(e -> {
			e.consume();
			exitConfirm();
		});

		Group top = new Group();
		HBox topE = new HBox();
		topE.setMinSize(1300, 150);
		topE.setMaxSize(1300, 150);
		topE.setAlignment(Pos.BASELINE_CENTER);
		topE.setStyle("-fx-padding: 10 10 10 10;");

		HBox topI = new HBox();
		topI.setMinSize(1280, 140);
		topI.setMaxSize(1280, 140);
		topI.setStyle("-fx-padding: 10; -fx-background-color: #000; -fx-opacity: 0.8; -fx-background-radius: 15; ");

		// StartPage.TopMenu project = new TopMenu("project.png");
		TopMenu open = new TopMenu("open.png");
		open.getContainer().setOnAction(e -> {
			FileChooser ch = new FileChooser();
			ch.setTitle("chose your file");
			this.fileName = ch.showOpenDialog(window);
			getFileContent(fileName);
		});
		// TopMenu importExport = new TopMenu("import_export.png");
		TopMenu save = new TopMenu("save.png");
		save.getContainer().setOnMouseClicked(e -> {
			if (list.size() > 1) {
				FileChooser ch = new FileChooser();
				ch.setTitle("Create file");
				this.fileName = ch.showSaveDialog(getWindow());
				saveToFile();
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Empty data");
				alert.setContentText("No data");
				alert.initOwner(window);
				alert.showAndWait();
			}
		});
		// TopMenu save_as = new TopMenu("save_as.png");

		topI.getChildren().addAll(open.getGroup(), save.getGroup());
		topI.setAlignment(Pos.BASELINE_CENTER);
		topE.getChildren().add(topI);
		top.getChildren().add(topE);

		HBox leftE = new HBox();
		leftE.setMinSize(300, 500);
		leftE.setAlignment(Pos.BASELINE_CENTER);
		leftE.setStyle("-fx-padding: 10 10 10 10");

		HBox leftI = new HBox();
		leftI.setMinSize(280, 470);
		leftI.setStyle("-fx-padding: 10; -fx-background-color: transparent;  -fx-background-radius: 15; ");
		leftI.setId("left");

		MenuItem about = new MenuItem("About", "about.png");
		MenuItem system = new MenuItem("System", "system.png");
		MenuItem modeling = new MenuItem("Modeling", "modeling.png");
		MenuItem parametr = new MenuItem("Options", "parametr.png");
		MenuItem testing = new MenuItem("Testing", "testing.png");
		MenuItem results = new MenuItem("Results", "results.png");
		MenuItem exit = new MenuItem("Exit", "exit.png");
		VBox menu = new VBox(about.getGroup(), system.getGroup(), parametr.getGroup(), modeling.getGroup(), testing.getGroup(), results.getGroup(), exit.getGroup());
		leftI.getChildren().add(menu);

		leftE.getChildren().add(leftI);

		Group left = new Group(leftE);

		HBox centerE = new HBox();
		centerE.setMinSize(980, 540);
		centerE.setAlignment(Pos.BASELINE_CENTER);
		centerE.setStyle("-fx-padding: 10 10 10 10");

		HBox centerI = new HBox();
		centerI.setMinSize(960, 520);
		centerI.setStyle("-fx-padding: 10; -fx-opacity: 0.8; -fx-background-radius: 15; ");
		HBox mcenter = new HBox();
		mcenter.setMinSize(950, 500);
		mcenter.getStyleClass().add("center");
		centerI.getChildren().add(mcenter);

		centerE.getChildren().add(centerI);
		mcenter.getChildren().add(setAbout());
		centerI.getStyleClass().add("center");

		Group center = new Group(centerE);

		BorderPane pane = new BorderPane();
		pane.setTop(top);
		pane.setLeft(left);
		pane.setCenter(center);

		StackPane mainPane = new StackPane(pane);
		mainPane.setMinSize(1300, 700);
		mainPane.maxHeightProperty().bind(scene.heightProperty());
		mainPane.maxWidthProperty().bind(scene.widthProperty());
		mainPane.setId("pane");

		root.getChildren().addAll(mainPane);

		Rectangle2D screen = Screen.getPrimary().getVisualBounds();
		double x = (screen.getWidth() - window.getWidth()) / 2;
		double y = (screen.getHeight() - window.getHeight()) / 2;
		window.setX(x);
		window.setY(y);

		about.getButton().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mcenter.getChildren().clear();
				mcenter.getChildren().add(setAbout());
			}

		});

		system.getButton().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mcenter.getChildren().clear();
				mcenter.getChildren().add(setSystem());
			}

		});

		modeling.getButton().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				mcenter.getChildren().clear();
				mcenter.getChildren().add(addMethodTab());
			}

		});

		parametr.getButton().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				mcenter.getChildren().clear();
				mcenter.getChildren().add(addParametrTab());
			}

		});

		testing.getButton().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				mcenter.getChildren().clear();
				mcenter.getChildren().add(addTestingTab());
			}

		});

		results.getButton().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				mcenter.getChildren().clear();
				mcenter.getChildren().add(addShowTab());
			}

		});

		exit.getButton().setOnMouseClicked(e -> exitConfirm());

	}

	public void change(Button g) {
		g.setId("active");
	}

	public void nchange(Button g) {
		g.setId("nactive");
	}

	public void exitConfirm() {
		Alert isExit = new Alert(AlertType.CONFIRMATION);
		isExit.setTitle("Exit");
		isExit.setContentText("Are you want to exit?");
		isExit.initOwner(window);

		Optional<ButtonType> btn = isExit.showAndWait();
		if (btn.get() == ButtonType.OK) {
			isExit.close();
			window.close();
		} else {
			isExit.close();
		}
	}

	public TabPane addMethodTab() {
		TabPane methodPane = new TabPane();
		methodPane.getStyleClass().add("Mpane");
		methodPane.setMinWidth(840);
		methodPane.setMinHeight(400);
		Tab method1 = new Tab("Method1");
		Tab method2 = new Tab("Method2");
		Tab method3 = new Tab("Method3");
		methodPane.getTabs().addAll(method1, method2, method3);
		methodPane.getStyleClass().add("tab-pane");
		methodPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		HBox container = new HBox(methodPane);
		container.setMinWidth(850);
		return methodPane;
	}

	public TabPane addParametrTab() {
		TabPane methodPane = new TabPane();
		methodPane.getStyleClass().add("Mpane");
		methodPane.setMinWidth(840);
		methodPane.setMinHeight(400);
		Tab par = new Tab("Set parametr");
		par.setContent(param());
		Tab model = new Tab("Set model");
		methodPane.getTabs().addAll(par, model);
		methodPane.getStyleClass().add("tab-pane");
		methodPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		HBox container = new HBox(methodPane);
		container.setMinWidth(850);
		return methodPane;
	}

	public TabPane addShowTab() {
		TabPane showPane = new TabPane();
		showPane.getStyleClass().add("Mpane");
		showPane.setMinWidth(840);
		showPane.setMinHeight(400);
		Tab graphic = new Tab("Chart");
		graphic.setGraphic(new ImageView(new Image("file:src/org/worker/resources/show_chart.png", 40, 50, false, true, false)));
		if (list.size() > 1) {
			graphic.setContent(Graphic.createChart(list, "Chart"));
		}

		Tab table = new Tab("Table");
		table.setGraphic(new ImageView(new Image("file:src/org/worker/resources/show_table.png", 40, 50, false, true, false)));

		Tab table_graphic = new Tab("Table+Graphic");
		table_graphic.setGraphic(new ImageView(new Image("file:src/org/worker/resources/table_graphic.png", 40, 50, false, true, false)));
		showPane.getTabs().addAll(graphic, table, table_graphic);
		showPane.getStyleClass().add("tab-pane");
		showPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		showPane.getSelectionModel().selectedItemProperty().addListener((obs, ov, nv) -> {
			if (list.size() > 1) {
				int n = showPane.getSelectionModel().getSelectedIndex();
				switch (n) {
				case 0:
					graphic.setContent(Graphic.createChart(list, "Chart"));
					break;
				case 1:
					table.setContent(Table.initTable(list));
					break;
				case 2:
					table_graphic.setContent(GraphicTable.display(list, "Table+Graphic"));
					break;
				}
			}
		});

		HBox container = new HBox(showPane);
		container.setMinWidth(850);
		return showPane;
	}

	public VBox setAbout() {
		Separator sep = new Separator();
		sep.setMinWidth(800);
		sep.getStyleClass().add("sep");

		Text t = new Text("About");
		t.getStyleClass().add("textStyle");

		HBox space = new HBox();
		space.setMinWidth(840);
		space.setMinHeight(50);
		space.backgroundProperty().set(Background.EMPTY);

		Text textAbout = new Text("This program is designed by Petrichko Myckola");
		textAbout.getStyleClass().add("text1");
		HBox im = new HBox(new ImageView(new Image("file:src/org/worker/resources/prof.gif", 200, 200, false, true, false)));
		im.setStyle("-fx-padding: 30 0 0 0 ");
		VBox cont = new VBox(t, sep, space, textAbout, im);
		cont.setMinWidth(840);
		cont.getStyleClass().add("centerPane");
		return cont;
	}

	public VBox setSystem() {
		Separator sep = new Separator();
		sep.setMinWidth(800);
		sep.getStyleClass().add("sep");

		Text t = new Text("System");
		t.getStyleClass().add("textStyle");

		HBox space = new HBox();
		space.setMinWidth(840);
		space.setMinHeight(50);
		space.backgroundProperty().set(Background.EMPTY);

		Text textAbout = new Text("This program is designed for work");
		textAbout.getStyleClass().add("text1");
		VBox cont = new VBox(t, sep, space, textAbout);
		cont.setMinWidth(840);
		cont.getStyleClass().add("centerPane");
		return cont;
	}

	public VBox param() {

		Separator sep = new Separator();
		sep.setPrefWidth(700);
		sep.getStyleClass().add("sep");

		HBox space = new HBox();
		space.setMinWidth(840);
		space.setMinHeight(50);
		space.backgroundProperty().set(Background.EMPTY);

		Text gr = new Text("Choose your function");
		gr.getStyleClass().add("text1");
		Text start = new Text("Start position");
		start.getStyleClass().add("text1");
		Text end = new Text("End position");
		end.getStyleClass().add("text1");
		Text step = new Text("Step");
		step.getStyleClass().add("text1");
		Text getFromFile = new Text("Get from file");
		getFromFile.getStyleClass().add("text1");

		ObservableList<String> str = FXCollections.observableArrayList("y=sin(x)", "y=cos(x)", "y=exp(x)");

		ComboBox<String> grBox = new ComboBox<String>(str);
		grBox.setPromptText("Function");

		TextField startField = new TextField();
		startField.setPromptText("Start position");
		TextField endField = new TextField();
		endField.setPromptText("End position");
		TextField stepField = new TextField();
		stepField.setPromptText("Step");

		ToggleGroup groupRadio = new ToggleGroup();
		RadioButton getFile = new RadioButton("Get from file");
		getFile.setId("text1");
		getFile.setToggleGroup(groupRadio);
		RadioButton setManually = new RadioButton("Set manually");
		setManually.setId("text1");
		setManually.setSelected(true);
		setManually.setToggleGroup(groupRadio);

		MenuItem gra = new MenuItem("Show graphic", "show_chart.png");
		Button showGraphic = gra.getButton();
		showGraphic.setOnAction(e -> {
			String text = ((RadioButton) groupRadio.getSelectedToggle()).getText();
			if (text.equals("Get from file")) {
				showGraphic("Unknown");
			} else {
				if (!startField.getText().equals("") && !endField.getText().equals("") && !step.getText().equals("")) {
					if (list.size() > 1)
						list.clear();
					int item = grBox.getSelectionModel().getSelectedIndex();
					double a = 0, b = 0, h = 0, y;
					try {
						a = Double.valueOf(startField.getText());
						b = Double.valueOf(endField.getText());
						h = Double.valueOf(stepField.getText());

						double x = a;
						while (x <= b) {
							switch (item) {
							case 0:
								y = Math.sin(x);
								break;
							case 1:
								y = Math.cos(x);
								break;
							case 2:
								y = Math.exp(x);
								break;
							default:
								y = Math.sin(x) * Math.cos(x);
								break;
							}
							list.add(new XY(x, y));

							x += h;
						}
						showGraphic(grBox.getSelectionModel().getSelectedItem());
					} catch (Exception ex) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Incorrect input");
						alert.setContentText("Failed to compute");
						alert.initOwner(window);
						alert.showAndWait();
					}
				} else {
					if (list.size() > 1) {
						showGraphic(grBox.getSelectionModel().getSelectedItem());
					} else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Empty data");
						alert.setContentText("No data");
						alert.initOwner(window);
						alert.showAndWait();
					}
				}
			}
		});

		MenuItem tab = new MenuItem("Show table", "show_table.png");
		Button showTable = tab.getButton();
		showTable.setOnAction(e -> {
			String text = (((RadioButton) groupRadio.getSelectedToggle()).getText());
			if (text.equals("Get from file")) {
				table();
			} else {
				if (!startField.getText().equals("") && !endField.getText().equals("") && !step.getText().equals("")) {
					list.clear();
					int item = grBox.getSelectionModel().getSelectedIndex();
					double a = 0, b = 0, h = 0, y;
					try {
						a = Double.valueOf(startField.getText());
						b = Double.valueOf(endField.getText());
						h = Double.valueOf(stepField.getText());
						if (a > b) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Incorrect input");
							alert.setContentText("Failed to compute");
							alert.initOwner(window);
							alert.showAndWait();
						} else {
							double x = a;
							boolean r = false;
							while (x <= b) {
								if (b - a < b - x) {
									r = true;
									break;
								}
								switch (item) {
								case 0:
									y = Math.sin(x);
									break;
								case 1:
									y = Math.cos(x);
									break;
								case 2:
									y = Math.exp(x);
									break;
								default:
									y = Math.sin(x) * Math.cos(x);
									break;
								}
								list.add(new XY(x, y));

								x += h;
							}
							if (r) {
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("Incorrect input");
								alert.setContentText("Failed to compute");
								alert.initOwner(window);
								alert.showAndWait();
							} else
								Table.display(list);
						}

					} catch (Exception ex) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Incorrect input");
						alert.setContentText("Failed to compute");
						alert.initOwner(window);
						alert.showAndWait();
					}
				} else {
					if (list.size() > 1) {
						Table.display(list);
					} else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Empty data");
						alert.setContentText("No data");
						alert.initOwner(window);
						alert.showAndWait();
					}
				}
			}
		});

		Button fileChooser = new Button("chose file");
		fileChooser.setOnAction(e -> {
			FileChooser ch = new FileChooser();
			ch.setTitle("chose your file");
			this.fileName = ch.showOpenDialog(window);
			getFileContent(fileName);
		});
		fileChooser.setDisable(true);

		groupRadio.selectedToggleProperty().addListener((ov, oldValue, newValue) -> {
			String text = ((RadioButton) groupRadio.getSelectedToggle()).getText();
			if (text.equals("Get from file")) {
				endField.setDisable(true);
				stepField.setDisable(true);
				fileChooser.setDisable(false);
				startField.setDisable(true);
			} else {
				startField.setDisable(false);
				endField.setDisable(false);
				stepField.setDisable(false);
				fileChooser.setDisable(true);
			}
		});

		/*
		 * MenuItem table_graphic = new MenuItem("Table + Graphic", "2.png");
		 * table_graphic.getButton().setOnAction(e -> { String text =
		 * (((RadioButton) groupRadio.getSelectedToggle()).getText()); if
		 * (text.equals("Get from file")) { if (list.size() > 1) {
		 * GraphicTable.draw(list, grBox.getSelectionModel().getSelectedItem());
		 * } else { Alert alert = new Alert(AlertType.ERROR); alert.setTitle(
		 * "Empty data"); alert.setContentText("No data");
		 * alert.initOwner(window); alert.showAndWait(); } } else {
		 * 
		 * if (!startField.getText().equals("") &&
		 * !endField.getText().equals("") && !step.getText().equals("")) {
		 * list.clear(); int item =
		 * grBox.getSelectionModel().getSelectedIndex(); double a = 0, b = 0, h
		 * = 0, y; try { a = Double.valueOf(startField.getText()); b =
		 * Double.valueOf(endField.getText()); h =
		 * Double.valueOf(stepField.getText()); if (a > b) { Alert alert = new
		 * Alert(AlertType.ERROR); alert.setTitle("Incorrect input");
		 * alert.setContentText("Failed to compute"); alert.initOwner(window);
		 * alert.showAndWait(); } else { double x = a; boolean r = false; while
		 * (x <= b) { if (b - a < b - x) { r = true; break; } switch (item) {
		 * case 0: y = Math.sin(x); break; case 1: y = Math.cos(x); break; case
		 * 2: y = Math.exp(x); break; default: y = Math.sin(x) Math.cos(x);
		 * break; } list.add(new XY(x, y));
		 * 
		 * x += h; } if (r) { Alert alert = new Alert(AlertType.ERROR);
		 * alert.setTitle("Incorrect input"); alert.setContentText(
		 * "Failed to compute"); alert.initOwner(window); alert.showAndWait(); }
		 * else GraphicTable.draw(list,
		 * grBox.getSelectionModel().getSelectedItem()); }
		 * 
		 * } catch (Exception ex) { Alert alert = new Alert(AlertType.ERROR);
		 * alert.setTitle("Incorrect input"); alert.setContentText(
		 * "Failed to compute"); alert.initOwner(window); alert.showAndWait(); }
		 * } else { if (list.size() > 1) { GraphicTable.draw(list,
		 * grBox.getSelectionModel().getSelectedItem()); } else { Alert alert =
		 * new Alert(AlertType.ERROR); alert.setTitle("Empty data");
		 * alert.setContentText("No data"); alert.initOwner(window);
		 * alert.showAndWait(); } } } });
		 */

		MenuItem save = new MenuItem("Save", "save1.png");

		save.getButton().setOnAction(e -> {
			String text = ((RadioButton) groupRadio.getSelectedToggle()).getText();
			if (text.equals("Get from file")) {

			} else {
				if (!startField.getText().equals("") && !endField.getText().equals("") && !step.getText().equals("")) {
					if (list.size() > 1)
						list.clear();
					int item = grBox.getSelectionModel().getSelectedIndex();
					double a = 0, b = 0, h = 0, y;
					try {
						a = Double.valueOf(startField.getText());
						b = Double.valueOf(endField.getText());
						h = Double.valueOf(stepField.getText());

						double x = a;
						while (x <= b) {
							switch (item) {
							case 0:
								y = Math.sin(x);
								break;
							case 1:
								y = Math.cos(x);
								break;
							case 2:
								y = Math.exp(x);
								break;
							default:
								y = Math.sin(x) * Math.cos(x);
								break;
							}
							list.add(new XY(x, y));

							x += h;
						}
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Saving");
						alert.setContentText("Data is saved");
						alert.initOwner(window);
						alert.showAndWait();
					} catch (Exception ex) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Incorrect input");
						alert.setContentText("Failed to compute");
						alert.initOwner(window);
						alert.showAndWait();
					} finally {

					}
				} else {
					if (list.size() > 1) {
						showGraphic(grBox.getSelectionModel().getSelectedItem());
					} else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Empty data");
						alert.setContentText("No data");
						alert.initOwner(window);
						alert.showAndWait();
					}
				}
			}
		});

		Separator sep1 = new Separator();

		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(15);
		GridPane.setConstraints(getFile, 0, 0);
		GridPane.setConstraints(setManually, 1, 0);
		GridPane.setConstraints(gr, 0, 1);
		GridPane.setConstraints(grBox, 1, 1);
		GridPane.setConstraints(start, 0, 2);
		GridPane.setConstraints(startField, 1, 2);
		GridPane.setConstraints(end, 0, 3);
		GridPane.setConstraints(endField, 1, 3);
		GridPane.setConstraints(step, 0, 4);
		GridPane.setConstraints(stepField, 1, 4);
		GridPane.setConstraints(getFromFile, 0, 5);
		GridPane.setConstraints(fileChooser, 1, 5);
		GridPane.setConstraints(sep1, 0, 6, 2, 1);
		GridPane.setConstraints(save.getButton(), 0, 7, 2, 1);

		grid.getChildren().addAll(getFile, setManually, gr, grBox, start, startField, end, endField, step, stepField, getFromFile, fileChooser, sep1, save.getButton());

		grid.setAlignment(Pos.BASELINE_CENTER);

		VBox title = new VBox(sep, space, grid);
		title.setMinWidth(840);
		title.getStyleClass().add("centerPane");
		fileChooser.setMinWidth(fileChooser.getParent().getBoundsInLocal().getWidth());
		return title;
	}

	public static Stage getWindow() {
		return window;
	}

	public void table() {
		if (list.size() > 1) {
			Table.display(list);
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Empty data");
			alert.setContentText("No data");
			alert.initOwner(window);
			alert.showAndWait();
		}
	}

	public void showGraphic(String name) {
		if (list.size() > 1) {
			Graphic.draw(list, name);
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Empty data");
			alert.setContentText("No data");
			alert.initOwner(window);
			alert.showAndWait();
		}
	}

	public VBox option() {

		Text res = new Text("Adding parametr");
		res.getStyleClass().add("textStyle");

		Separator sep = new Separator();
		sep.setPrefWidth(700);
		sep.getStyleClass().add("sep");

		HBox space = new HBox();
		space.setMinWidth(840);
		space.setMinHeight(50);
		space.setMaxHeight(50);
		space.backgroundProperty().set(Background.EMPTY);

		Text gr = new Text("Choose your function");
		gr.getStyleClass().add("text1");
		Text start = new Text("Start position");
		start.getStyleClass().add("text1");
		Text end = new Text("End position");
		end.getStyleClass().add("text1");
		Text step = new Text("Step");
		step.getStyleClass().add("text1");
		Text getFromFile = new Text("Get from file");
		getFromFile.getStyleClass().add("text1");

		ObservableList<String> str = FXCollections.observableArrayList("y=sin(x)", "y=cos(x)", "y=exp(x)");

		ComboBox<String> grBox = new ComboBox<String>(str);
		grBox.setPromptText("Function");

		TextField startField = new TextField();
		startField.setPromptText("Start position");
		TextField endField = new TextField();
		endField.setPromptText("End position");
		TextField stepField = new TextField();
		stepField.setPromptText("Step");

		ToggleGroup groupRadio = new ToggleGroup();
		RadioButton getFile = new RadioButton("Get from file");
		getFile.setId("text1");
		getFile.setToggleGroup(groupRadio);
		RadioButton setManually = new RadioButton("Set manually");
		setManually.setId("text1");
		setManually.setSelected(true);
		setManually.setToggleGroup(groupRadio);

		Button fileChooser = new Button("chose file");
		fileChooser.setOnAction(e -> {
			FileChooser ch = new FileChooser();
			ch.setTitle("chose your file");
			this.fileName = ch.showOpenDialog(window);
			getFileContent(fileName);
		});
		fileChooser.setDisable(true);

		groupRadio.selectedToggleProperty().addListener((ov, oldValue, newValue) -> {
			String text = ((RadioButton) groupRadio.getSelectedToggle()).getText();
			if (text.equals("Get from file")) {
				endField.setDisable(true);
				stepField.setDisable(true);
				fileChooser.setDisable(false);
				startField.setDisable(true);
			} else {
				startField.setDisable(false);
				endField.setDisable(false);
				stepField.setDisable(false);
				fileChooser.setDisable(true);
			}
		});

		Separator sep1 = new Separator();

		MenuItem save = new MenuItem("Save", "save1.png");

		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(15);
		GridPane.setConstraints(getFile, 0, 0);
		GridPane.setConstraints(setManually, 1, 0);
		GridPane.setConstraints(gr, 0, 1);
		GridPane.setConstraints(grBox, 1, 1);
		GridPane.setConstraints(start, 0, 2);
		GridPane.setConstraints(startField, 1, 2);
		GridPane.setConstraints(end, 0, 3);
		GridPane.setConstraints(endField, 1, 3);
		GridPane.setConstraints(step, 0, 4);
		GridPane.setConstraints(stepField, 1, 4);
		GridPane.setConstraints(getFromFile, 0, 5);
		GridPane.setConstraints(fileChooser, 1, 5);
		GridPane.setConstraints(sep1, 0, 6, 2, 1);
		GridPane.setConstraints(save.getGroup(), 0, 7, 2, 1);

		grid.getChildren().addAll(getFile, setManually, gr, grBox, start, startField, end, endField, step, stepField, getFromFile, fileChooser, sep1, save.getGroup());

		grid.setAlignment(Pos.BASELINE_CENTER);

		VBox layout = new VBox(res, sep, space, grid);
		layout.setMinWidth(840);
		layout.getStyleClass().add("centerPane");
		return layout;
	}

	public void getFileContent(File file) {
		if (file != null) {
			Scanner scn = null;
			list.clear();
			try {
				FileReader fr = new FileReader(file.getAbsolutePath());
				scn = new Scanner(fr);
				list = new ArrayList<XY>();
				while (scn.hasNext()) {
					double x = Double.valueOf(scn.next());
					double y = Double.valueOf(scn.next());
					list.add(new XY(x, y));
				}
			} catch (Exception e1) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Cannot open the file");
				alert.setContentText("Error while parsing the file");
				alert.initOwner(window);
				alert.showAndWait();
				e1.printStackTrace();
			} finally {
				if (scn != null)
					scn.close();
			}
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Saving");
			alert.setContentText("Data is saved");
			alert.initOwner(window);
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Cannot open the file");
			alert.setContentText("Error while parsing file");
			alert.initOwner(window);
		}
	}

	public void saveToFile() {
		if (fileName != null) {

			Writer r = null;

			try {
				if (fileName.createNewFile()) {
					r = new FileWriter(fileName);
					for (XY s : list) {
						r.write(s.getX() + " " + s.getY());
						r.write("\r\n");
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (r != null) {
					try {
						r.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}
	}

	public TabPane addTestingTab() {
		TabPane methodPane = new TabPane();
		methodPane.getStyleClass().add("Mpane");
		methodPane.setMinWidth(840);
		methodPane.setMinHeight(400);
		Tab method1 = new Tab("Test1");
		Tab method2 = new Tab("Test2");
		methodPane.getTabs().addAll(method1, method2);
		methodPane.getStyleClass().add("tab-pane");
		methodPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		HBox container = new HBox(methodPane);
		container.setMinWidth(850);
		return methodPane;
	}

	public double getWIDTH() {
		return WIDTH;
	}

	public void setWIDTH(double wIDTH) {
		WIDTH = wIDTH;
	}

	public double getHEIGHT() {
		return HEIGHT;
	}

	public void setHEIGHT(double hEIGHT) {
		HEIGHT = hEIGHT;
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
