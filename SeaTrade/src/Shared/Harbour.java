package Shared;

import sea.Position;

public class Harbour {

	private String _name;
	private Position _postion;
	
	public Harbour(String name, int x, int y) {
		this._name = name;
		this._postion = new Position(x, y);
	}

	public String get_name() {
		return _name;
	}

	public Position get_postion() {
		return _postion;
	}
}
