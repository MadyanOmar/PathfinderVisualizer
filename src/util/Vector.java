package util;

public class Vector {
	double x;
	double y;
	
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void add(Vector v) {
		this.x += v.getX();
		this.y += v.getY();
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
}
