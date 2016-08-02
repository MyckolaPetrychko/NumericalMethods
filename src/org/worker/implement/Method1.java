package org.worker.implement;

import org.worker.interfaces.Method;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Method1 implements Method {
	private VBox layout;

	@Override
	public void solve() {

	}

	@Override
	public void draw() {
		Button btn = new Button("method1");
		TextField field = new TextField();
		field.setPromptText("method1");
		layout = new VBox(btn, field);
	}

	@Override
	public VBox getLayout() {
		return layout;
	}

}
