package org.worker.menu;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class MenuItem {
	private HBox layout;
	private HBox group;
	private Button button;

	public MenuItem(String text, String im) {

		Image image = new Image("file:src/org/worker/resources/" + im, 50, 50, false, true, false);
		ImageView imageView = new ImageView(image);

		button = new Button(text, imageView);
		button.setMinHeight(50);
		button.setMaxHeight(50);
		button.setMinWidth(250);
		button.getStyleClass().add("button1");
		button.setAlignment(Pos.BASELINE_LEFT);
		button.setOnMouseMoved(e -> button.setCursor(Cursor.HAND));
		layout = new HBox(button);
		layout.setStyle("-fx-padding: 5");
		/*
		 * Polygon poly = new Polygon(); poly.getPoints().addAll(new Double[] {
		 * 25.0, 30.0, 35.0, 35.0, 25.0, 40.0 });
		 */

		group = new HBox(layout);
		group.setMinWidth(204);
		group.setMinHeight(40);
		group.getStyleClass().add("first");
	}

	public HBox getLayout() {
		return layout;
	}

	public void setLayout(HBox layout) {
		this.layout = layout;
	}

	public HBox getGroup() {
		return group;
	}

	public void setGroup(HBox group) {
		this.group = group;
	}

	public Button getButton() {
		return button;
	}

	public void setButton(Button button) {
		this.button = button;
	}

}
