package org.worker.menu;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class TopMenu {

	private Button container;

	public Button getContainer() {
		return container;
	}

	public void setContainer(Button container) {
		this.container = container;
	}

	private HBox layout;
	private Group group;

	public TopMenu(String imageSource) {
		container = new Button("",
				new ImageView(new Image("file:src/org/worker/resources/" + imageSource, 80, 80, true, false)));
		container.setMinSize(80, 80);
		container.setMaxSize(100, 100);
		container.setAlignment(Pos.BASELINE_CENTER);
		container.getStyleClass().add("topButton");
		container.setOnMouseMoved(e -> {
			container.cursorProperty().set(Cursor.HAND);
			container.setScaleX(1.25);
			container.setScaleY(1.25);
		});
		container.setOnMouseExited(e -> {
			container.cursorProperty().set(Cursor.DEFAULT);
			container.setScaleX(1);
			container.setScaleY(1);
		});

		layout = new HBox(container);
		layout.setMinSize(130, 120);
		layout.setAlignment(Pos.BASELINE_CENTER);
		layout.getStyleClass().add("topLayout");

		setGroup(new Group(layout));
	}

	public Group getGroup() {
		return group;
	}

	private void setGroup(Group group) {
		this.group = group;
	}

}
