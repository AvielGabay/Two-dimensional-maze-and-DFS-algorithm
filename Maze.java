package graph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Maze implements GraphInterface<Place> {
	private Place[][] maze;
	private Place start, finish;
	private int size;

	public Maze(int size, int startx, int starty, int endx, int endy) {
		maze = new Place[size][size];
		start = new Place(startx, starty, size);
		finish = new Place(endx, endy, size);
		this.size = size;
		maze[startx][starty] = start;
		maze[endx][endy] = finish;
	}

	public boolean addWall(int x, int y) {
		Place wall = new Place(x, y, size);// if not in the maze- Place will throw exception.
		if (maze[x][y] == null) {
			maze[x][y] = wall;
			return true;
		}
		return false;
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (maze[i][j] == null)
					s.append(".");
				else if (maze[i][j] == start)
					s.append("S");
				else if (maze[i][j] == finish)
					s.append("E");
				else
					s.append("@");
			}
			s.append("\n"); // start new row
		}
		return s.toString();
	}

	public boolean isSolvable() {
		Graph<Place> graph = new Graph<>();
		Set<Place> s = new HashSet<>();
		try { 	// add the saved places to the graph and to the path
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (maze[i][j] == null || maze[i][j] == start || maze[i][j] == finish) {
						Place pl = new Place(i, j, size);
						s.add(pl);
						graph.addVertex(pl);
					}
				}
			}
			// search and add edges for the saved places
			for (Place val : s) {
				int x = val.getX();
				int y = val.getY();
				if ((x + 1) < size && !graph.hasEdge(val, new Place(x + 1, y, size))
						&& s.contains(new Place(x + 1, y, size)))
					graph.addEdge(val, new Place(x + 1, y, size));
				if ((y + 1) < size && !graph.hasEdge(val, new Place(x, y + 1, size))
						&& s.contains(new Place(x, y + 1, size)))
					graph.addEdge(val, new Place(x, y + 1, size));
				if ((x - 1) >= 0 && !graph.hasEdge(val, new Place(x - 1, y, size))
						&& s.contains(new Place(x - 1, y, size)))
					graph.addEdge(val, new Place(x - 1, y, size));
				if ((y - 1) >= 0 && !graph.hasEdge(val, new Place(x, y - 1, size))
						&& s.contains(new Place(x, y - 1, size)))
					graph.addEdge(val, new Place(x, y - 1, size));
			}
			if (graph.connected(start, finish))
				return true;
		} catch (GraphException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Collection<Place> neighbours(Place p) {
		Set<Place> list = new HashSet<>();
		int x = p.getX();
		int y = p.getY();
		if (maze[x][y] != null && maze[x][y] != start && maze[x][y] != finish)
			return list; 	// if p is a wall, finish
		// check all corners and add the neighbors
		if ((x - 1) >= 0 && (maze[x - 1][y] == null || maze[x - 1][y] == start || maze[x - 1][y] == finish))
			list.add(new Place(x - 1, y, size));
		if ((x + 1) < size && (maze[x + 1][y] == null || maze[x + 1][y] == start || maze[x + 1][y] == finish))
			list.add(new Place(x + 1, y, size));
		if ((y - 1) >= 0 && (maze[x][y - 1] == null || maze[x][y - 1] == start || maze[x][y - 1] == finish))
			list.add(new Place(x, y - 1, size));
		if ((y + 1) < size && (maze[x][y + 1] == null || maze[x][y + 1] == start || maze[x][y + 1] == finish))
			list.add(new Place(x, y + 1, size));
		return list;
	}
}