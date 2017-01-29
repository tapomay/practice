package gksfrgks.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gksfrgks.graph.common.Algorithm;
import gksfrgks.graph.common.IntGraph;

public class BFS implements Algorithm{

	public static void main(String args[])
    {
		BFS algo = new BFS();
        IntGraph g = new IntGraph(4, algo);
 
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(2, 0);
        g.addEdge(2, 3);
        g.addEdge(3, 3);
 
        System.out.println("Following is Breadth First Traversal "+
                           "(starting from vertex 2)");
 
        List<Integer> res = (List<Integer>) g.process(2);
        System.out.println(res);
    }

	@Override
	public Object execute(IntGraph g, Object... args) {

		Integer root = (Integer) args[0];
		LinkedList<Integer> queue = new LinkedList<>();
		Map<Integer, List<Integer>> adj = g.getAdj();
		Set<Integer> visited = new HashSet<>();
		List<Integer> ret = new ArrayList<Integer>();
		queue.add(root);
		
		while(!queue.isEmpty()) {
			Integer node = queue.poll();
			visited.add(node);
			ret.add(node);
			List<Integer> nodesAdj = adj.get(node);
			for(Integer n : nodesAdj) {
				if (!visited.contains(n)) {
					queue.add(n);
				}
			}
		}
		return ret;
	}
}
