package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph<V> {
	private Set<V> vertices = new HashSet<>();
	private Map<V, Set<V>> edges = new HashMap<>();
	private Set<V> mark = new HashSet<>();;

	public void addVertex(V v) throws GraphException {
		if (!vertices.contains(v))
			vertices.add(v);
		else
			throw new GraphException("The vertex is in the graph");
	}

	public void addEdge(V v1, V v2) throws GraphException {
		if (!vertices.contains(v1) || !vertices.contains(v2))// check if vertices exists
			throw new GraphException("No such vertice");
		if (hasEdge(v1, v2) == true)
			throw new GraphException("Edge already exists"); // check if the edge is already exists
		if (!edges.containsKey(v1)) // check if v1 is already mapped and adds edge with v2
			edges.put(v1, new HashSet<V>());
		edges.get(v1).add(v2);	
		if (!edges.containsKey(v2)) // check if v2 is already mapped and adds edge with v1
			edges.put(v2, new HashSet<V>());
		edges.get(v2).add(v1);
	}

	public boolean hasEdge(V v1, V v2) {
		return (edges.containsKey(v1) && edges.get(v1).contains(v2)
				|| edges.containsKey(v2) && edges.get(v2).contains(v1));
	}

	public boolean connected(V v1, V v2) throws GraphException {
		if (!vertices.contains(v1) || !vertices.contains(v2))
			throw new GraphException("No such vertice");
		if (v1.equals(v2))
			return true;
		if (hasEdge(v1, v2))
			return true;
		return func(v1, v2);
	}
	
	private boolean func(V v1, V v2) {
		for (V val : edges.get(v1)) {
			if (hasEdge(val, v2))
				return true;
		}
		for (V val : edges.get(v1)) {
			if (!mark.contains(val)) {
				mark.add(val);
				if (func(val, v2))
					return true;
			}
		}
		return false;
	}
}