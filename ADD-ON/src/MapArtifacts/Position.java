package MapArtifacts;

public class Position {
	private int x;
	private int y;
	private int direction;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
		this.direction = 0; //�⺻ ��ġ�� EAST�� �ʱ�ȭ
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
		
	public String getPositionString() { //String������ Position ������ �����ش�
		return "(" + this.x + "," + this.y + ")";
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setDirection() {
		this.direction++;
		this.direction = this.getDirection() % 4;
	}
}