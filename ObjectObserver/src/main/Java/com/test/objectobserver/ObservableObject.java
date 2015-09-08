package com.test.objectobserver;

import java.util.Observable;

public class ObservableObject extends Observable {
	private static final String TAG = ObservableObject.class.getName();
	private static ObservableObject instance;

	private String name = "";

	public static ObservableObject initialize() {
		return instance == null ? instance = new ObservableObject() : instance;
	}

	public static ObservableObject getInstance() {
		return instance;
	}

	public String getValue() {
		return name;
	}

	public void setValue(String name) {
		this.name = name;
		setChanged();
		notifyObservers();
	}
}
