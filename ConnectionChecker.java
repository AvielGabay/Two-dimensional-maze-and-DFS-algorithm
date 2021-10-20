package graph;

import java.util.HashSet;
import java.util.Set;

public class ConnectionChecker<V> {
	private GraphInterface<V> g;
	private Set<V> Keepvertices = new HashSet<>();
	
	public ConnectionChecker(GraphInterface<V> g) {
		this.g = g;
	}
	public boolean check(V v1, V v2) {
		// if they are neighbors, return true
		if (g.neighbours(v1).contains(v2))
			return true;
		// if they are not neighbors go over v1, if it is new vertices, save it and
		// start again recursively
		for (V v : g.neighbours(v1)) {
			if (!Keepvertices.contains(v)) {
				Keepvertices.add(v);
				if (check(v, v2))
					return true;
			}
		}
		return false;
	}
}