package graph;

public class Place {
	private int x, y, size;

	public Place(int x, int y, int bound){
		if (0 <= x && x <= bound - 1 && 0 <= y && y <= bound - 1) {
			this.x = x;
			this.y = y;
			size = bound;
		} else
			throw new IllegalArgumentException("x or y is not in bound size");
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Place) || o == null)
			return false;
		Place temp = (Place) o;
		return (x == temp.getX() && y == temp.getY());
	}

	@Override
	public int hashCode() {
		return x + y*size;
	}
}