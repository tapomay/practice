package gksfrgks.graph.common;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class IntGraph
{
    private int V;   // No. of vertices
    private Map<Integer, List<Integer>> adj; //Adjacency Lists
    private Algorithm algo;
    
    // Constructor
    public IntGraph(Integer v, Algorithm algo)
    {
        V = v;
        this.algo = algo;
        adj = new HashMap();
        for (int i=0; i<v; ++i)
            adj.put(i, new LinkedList<Integer>());
    }
 
    // Function to add an edge into the graph
    public void addEdge(int v,int w)
    {
        adj.get(v).add(w);
    }
    
    public Map<Integer, List<Integer>> getAdj(){
    	return this.adj;
    }
    
    public Object process(Object... args){
    	return this.algo.execute(this, args);
    }
}