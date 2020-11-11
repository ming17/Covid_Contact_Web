public class ContactWebRunner {
    public static void main(String [] args)
    {
        ContactWeb test = new ContactWeb();

        // Add Nodes
        test.addNode("Michael", 8562202644L);
        test.addNode("Henry", 1234567890L);
        test.addNode("Anna", 4255162317L);
        test.addNode("Max", 4123042064L);
        test.addNode("Kate", 987654321L);
        test.addNode("Hoff", 7086255705L);
        test.addNode("Scott");
        test.addNode("Myles");
        test.addNode("Helenski");
        test.addNode("Kevin");
        test.addNode("Alex");
        test.addNode("Aiden");
        test.addNode("Harry");
        test.addNode("Marcel");

        // Add Edges
        test.addEdge("Michael", "Henry");
        test.addEdge("Michael", "Anna");
        test.addEdge("Henry", "Kate");
        test.addEdge("Michael", "Max");
        test.addEdges("Max", 15);
        test.addEdge("Henry", "Hoff");
        test.addEdge("Hoff", "Scott");
        test.addEdge("Henry", "Scott");
        test.addEdge("Myles", "Helenski");
        test.addEdge("Helenski", "Kevin");
        test.addEdge("Kevin", "Myles");
        test.addEdges("Myles", 12);
        test.addEdges("Kate", 4);
        test.addEdge("Anna", "Max");
        test.addEdges("Scott", 6);
        test.addEdge("Marcel", "Harry");
        test.addEdge("Marcel", "Aiden");
        test.addEdge("Aiden", "Harry");
        test.addEdge("Aiden", "Max");
        test.addEdge("Alex", "Max");
        test.addEdge("Aiden", "Alex");
        test.addEdges("Alex", 3);

        System.out.println("There are " + test.getNumNodes() + " total nodes");
        
        String[] names = {"Michael", "Anna", "Max", "Henry", "Helenski"};
        int levels = 3;

        for(String name : names)
        {
            for(int l = 1; l <= levels; l++)
                System.out.println(name + "'s level " + l + " web size: " + test.getWebSize(name, l));
            System.out.println("\n" + name + "'s web is " + test.getList(name) + "\n\n");
        }
    }
}
