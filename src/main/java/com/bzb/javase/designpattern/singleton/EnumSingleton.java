package com.bzb.javase.designpattern.singleton;

public enum EnumSingleton {
	
	INSTANCE(1, "Singleton");

	private final int id;
	private final String name;

	EnumSingleton(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
