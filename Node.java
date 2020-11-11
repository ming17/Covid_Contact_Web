public class Node {
    private static final int MAX_EDGES = 1000;

    private String[] edges;
    private int numEdges;
    private String id;
    private String name;
    
    public Node(String n, String i)
    {
        name = n.toUpperCase();
        id = i;
        numEdges = 0;
        edges = new String[MAX_EDGES];
    }

    public Node(String i)
    {
        name = i.toUpperCase();
        id = i;
        numEdges = 0;
        edges = new String[MAX_EDGES];
    }

    public void addEdge(String dst)
    {
        if(this.hasEdge(dst) == -1)
        {
            edges[numEdges] = dst;
            numEdges++;
        }
    }

    public int hasEdge(String dst)
    {
        for(int i = 0; i < numEdges; i++)
        {
            if(dst.equals(edges[i]))
            {
                return i;
            }
        }
        return -1;
    }

    public void setId(String i)
    {
        id = i;
    }

    public void setName(String n)
    {
        name = n.toUpperCase();
    }

    public int numEdges()
    {
        return numEdges;
    }

    public String[] edges()
    {
        String[] es = new String[numEdges];
        for(int e = 0; e < numEdges; e++)
            es[e] = edges[e];

        return es;
    }

    public String getName()
    {
        return name;
    }

    public String getId()
    {
        return id;
    }
}
