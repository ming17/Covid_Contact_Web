public class Edge {
    private String src;
    private String dst;
    private double wt;

    public Edge(String s, String d, int w)
    {
        src = s;
        dst = d;
        wt = w;
    }

    public Edge(String s, String d)
    {
        src = s;
        dst = d;
        wt = 1;
    }
}
