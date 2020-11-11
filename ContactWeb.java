import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.Random;

import java.util.Iterator;
import java.util.ArrayList;

public class ContactWeb {
    private static final boolean DEBUG = false;
    private static final int NUM_ID_CHARS = 26; //72;
    private static final int ID_LEN = 6;
    private static final int MAX_NODES = (int)Math.pow(NUM_ID_CHARS, ID_LEN);
    private Node[] nodes;
    private int numNodes;

    public ContactWeb()
    {
        nodes = new Node[MAX_NODES];
        numNodes = 0;
    }

    public int getWebSize(String srcN, int depth)
    {
        ArrayList<String> nodesFound = new ArrayList<String>();
        String srcId = nodes[this.hasName(srcN)].getId();
        nodesFound.add(srcId);

        return this.recursiveWebSize(srcId, nodesFound, depth);
    }

    public int recursiveWebSize(String nId, ArrayList<String> nf, int d)
    {
        int webSize = 0;
        String[] dsts = nodes[this.hasNode(nId)].edges();
        boolean found;

        ArrayList<String> tempList = new ArrayList<String>();

        if(d == 1)
        {
            for(String dst : dsts)
            {
                found = false;
                if(nf.size() > 0)
                {
                    for(String node : nf)
                    {
                        if(dst.equals(node))
                            found = true;
                    }
                }
                if(!found) 
                {
                    nf.add(dst);
                    webSize++;
                }
            }
        }
        else
        {
            for(String dst : dsts)
            {
                found = false;
                for(String node : nf)
                {
                    if(dst.equals(node))
                        found = true;
                }
                if(!found) 
                {
                    tempList.add(dst);
                    nf.add(dst);
                }
            }

            if(tempList.size() > 0)
            {
                for(String t : tempList)
                {
                    webSize++;
                    webSize += this.recursiveWebSize(t, nf, d-1);
                }
            }
        }

        return webSize;
    }

    // // adds a new edge based on given src id and dst id
    // public void addEdgeByID(String src, String dst)
    // {
    //     int posS = this.addNode(src);
    //     int posD = this.addNode(dst);

    //     nodes[posS].addEdge(dst);
    //     nodes[posD].addEdge(src);
    // }

    // // adds new edges (and thus new nodes) to a given nodeId
    // //      @param numE - number of edges (and nodes) to add
    // //      @param src - source node id to add edges to
    // public void addEdgesByID(String src, int numE)
    // {
    //     int posS = this.addNode(src);

    //     int posD;

    //     for(int i = 0; i < numE; i++)
    //     {
    //         posD = this.addNode();

    //         nodes[posS].addEdge(nodes[posD].getId());
    //         nodes[posD].addEdge(src);
    //     }
    // }

    // adds a new edge based on given src name and dst name
    //      if these names don't exist, adds nodes with associated names
    public void addEdge(String srcN, String dstN)
    {
        int posS = this.addNode(srcN);
        int posD = this.addNode(dstN);

        nodes[posS].addEdge(nodes[posD].getId());
        nodes[posD].addEdge(nodes[posS].getId());
    }

    // adds new edges (and thus new nodes) to a given node by name
    //      @param numE - number of edges (and nodes) to add
    //      @param srcN - source node name to add edges to
    public int[] addEdges(String srcN, int numE)
    {
        int posS = this.addNode(srcN);
        int posD;
        int[] positions = new int[numE];

        for(int i = 0; i < numE; i++)
        {
            posD = this.addNode();
            positions[i] = posD;

            nodes[posS].addEdge(nodes[posD].getId());
            nodes[posD].addEdge(nodes[posS].getId());
        }

        return positions;
    }

    // adds new node with randomly generated id and returns position
    public int addNode()
    {
        int pos = numNodes;
        String id = this.generateNewId();

        nodes[numNodes] = new Node(id);
        numNodes++;

        return pos;
    }

    // adds node with given name if doesn't already exist
    public int addNode(String name)
    {
        int pos = -1;

        if((pos = this.hasName(name)) == -1)
        {
            String id = this.generateNewId();
            nodes[numNodes] = new Node(name, id);
            pos = numNodes;
            numNodes++;
        }

        return pos;
    }

    // adds node with given phone number seed if doesn't already exist
    public int addNode(long phoneNum)
    {
        String id = "";
        int pos = -1;

        id = this.getId(phoneNum);

        if((pos = this.hasNode(id)) == -1)
        {
            nodes[numNodes] = new Node(id);
            pos = numNodes;
            numNodes++;
        }

        return pos;
    }

    // adds node with given name and phone number if doesn't already exist
    //      if node does exist, update id using new phone number
    public int addNode(String name, long phoneNum)
    {
        String id = "";
        int pos = -1;

        id = this.getId(phoneNum);

        if((pos = this.hasName(name)) == -1)
        {
            nodes[numNodes] = new Node(name, id);
            pos = numNodes;
            numNodes++;
        }
        else
        {
            nodes[pos].setId(id);
        }

        return pos;
    }

    public void updatePhoneNum(String name, long phoneNum)
    {
        int pos;
        String id = this.getId(phoneNum);
        
        if((pos = this.hasName(name)) == -1)
        {
            System.err.println("ERROR: Node " + name + " does not exist");
            return;
        }
        else
            nodes[pos].setId(id);
    }

    public void updateName(String name, String newName)
    {
        int pos;
        if((pos = this.hasName(name)) == -1)
        {
            System.err.println("ERROR: Node " + name + " does not exist");
            return;
        }
        else
            nodes[pos].setName(newName);
    }

    public int hasNode(String id)
    {
        for(int i = 0; i < numNodes; i++)
        {
            if(id.equals(nodes[i].getId()))
            {
                return i;
            }
        }
        return -1;
    }

    public int hasName(String name)
    {
        for(int i = 0; i < numNodes; i++)
        {
            if(name.toUpperCase().equals(nodes[i].getName()))
            {
                return i;
            }
        }
        return -1;
    }

    public int getNumNodes()
    {
        return numNodes;
    }

    //  Returns full printable web in indented list form
    public String getList()
    {
        String fullList = "";
        ArrayList<String> nodesFound = new ArrayList<String>();

        for(Node n : nodes)
        {
            nodesFound.add(n.getName());
            fullList += "\n" + n.getName().toUpperCase() + "\n"  + this.recurseList(n.getName(), nodesFound, 1);
        }
        return fullList;
    }

    //  Returns printable web in indented list form for particular name
    public String getList(String headNode)
    {
        ArrayList<String> nodesFound = new ArrayList<String>();
        String list = "\n" + headNode.toUpperCase() + "\n";
        nodesFound.add(headNode.toUpperCase());

        return list += this.recurseList(headNode.toUpperCase(), nodesFound, 1);
    }

    //  Returns printable web in indented list form for particular name
    public String recurseList(String curr, ArrayList<String> nf, int iter)
    {
        String list = "";
        boolean found;
        String name;

        // for(int i = 0; i < iter-1; i++)
        //     list += "\t";
        // list += (curr + "\n");

        String[] dsts = nodes[this.hasName(curr)].edges();
        ArrayList<String> tempList = new ArrayList<String>();
        
        for(String dst : dsts)
        {
            found = false;
            name = nodes[this.hasNode(dst)].getName();
            if(DEBUG)
                System.out.println(curr + " checking " + name);

            if(nf.size() > 0)
            {
                for(String node : nf)
                {
                    if(DEBUG)
                        System.out.println("Checking " + name + " against " + node);
                    if(name.equals(node))
                        found = true;
                }
            }
            if(!found) 
            {
                tempList.add(name);
                nf.add(name);  
            }
        }
        
        if(tempList.size() > 0)
        {
            for(String t : tempList)
            {
                for(int i = 0; i < iter; i++)
                    list += "\t";
                list += (t + "\n");
                list += this.recurseList(t, nf, iter+1);
            }
        }

        return list;
    }

    private String generateNewId()
    {
        String id = "";
        Random rand = new Random();
        int n;

        do {
            for(int i = 0; i < ID_LEN; i++)
            {
                n = rand.nextInt(NUM_ID_CHARS) + 97;
                id += (char)n;
            }
        } while (this.hasNode(id) != -1);

        return id;
    }

    private String getId(long phoneNum)
    {
        BigDecimal num = new BigDecimal(phoneNum);
        Random rand = new Random();

        rand.setSeed(phoneNum);
        BigDecimal randNum = new BigDecimal(rand.nextInt(90)+10);

        String mult = num.multiply(randNum).toString();
        String id = "";

        boolean odd = (mult.length() % 2 == 1);
        for(int i = 0; i < mult.length(); i+=2)
        {
            if(odd && i == 0)
            {
                id += getChar(mult.charAt(0));
                i--;
            }
            else
                id += getChar(Integer.parseInt(mult.substring(i, i+2)));
        }

        if(DEBUG)
            System.out.println("phonenum is " + phoneNum + " rand num is " + randNum.intValue() + " combined num is " + mult + " string is " + id);
        return id;
    }

    private char getChar(int val)
    {
        int mod = val % NUM_ID_CHARS;
        return (char) (mod + 97);
        
        // if(mod <= 19)
        // {
        //     return (char) ((mod % 10) + 48);
        // }
        // else if (mod <= 45)
        // {
        //     return (char) (mod + 45);
        // }
        // else if (mod <= 71)
        // {
        //     return (char) (mod + 51);
        // }
        // else
        // {
        //     System.err.println("Error! Index " + mod + " out of bounds!");
        //     return ' ';
        // }
    }
}