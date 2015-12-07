package Foundation;

public class Position {
	private int x;
	private int y;
	private int direction;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
		this.direction = 1; //기본 위치를 EAST로 초기화
	}
	
	public Position() {
		
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getDirection() {
		return this.direction;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setDirection(int d) {
		this.direction = d;
	}
	
	public String toString() {
		return "(" + this.x + ", " + this.y + ") D : " + this.direction;
	}
}