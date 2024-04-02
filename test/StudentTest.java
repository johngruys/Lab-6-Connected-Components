import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class StudentTest {
    @Test
    public void test() {
        connected_components_test();
        unassigned_test();
        inorder_test();
        inorder2_test();
        disconnected_test();
        random_test();
        null_test();
    }

    @Test
    public void connected_components_test() {
        UndirectedAdjacencyList graph1 = new UndirectedAdjacencyList(10);
        HashMap<Integer, Integer> representative = new HashMap<>();


        graph1.addEdge(0, 0);
        graph1.addEdge(0, 1);
        graph1.addEdge(0, 2);
        graph1.addEdge(0, 3);
        graph1.addEdge(0, 4);
        graph1.addEdge(5, 5);
        graph1.addEdge(5, 6);
        graph1.addEdge(5, 7);
        graph1.addEdge(5, 8);
        graph1.addEdge(5, 9);

        ConnectedComponents.connected_components(graph1, representative);

//        int i = 10;
//        for (Integer key : representative.keySet()) {
//            switch (key) {
//                case 10:
//                    assertEquals(representative.get(key), 10);
//                    break;
//                case 20:
//                    assertEquals(representative.get(key), 20);
//                case 30:
//                    assertEquals(representative.get(key), 30);
//            }
//        }

        for (Integer key : representative.keySet()) {
            if (key < 5) {
                assertEquals(0, representative.get(key));
//            } else if (key < 20) {
//                assertEquals(representative.get(key), 10);
            } else {
                assertEquals(5, representative.get(key));
            }
        }

        // Oracle map
        HashMap<Integer, Integer> oracle_map = new HashMap<>();
        connected_test_oracle(graph1, oracle_map);
        assertEquals(representative, oracle_map);

        assertEquals(graph1.numVertices(), oracle_map.size());
        assertEquals(graph1.numVertices(), representative.size());

    }

    @Test
    public void unassigned_test() {
        UndirectedAdjacencyList graph = new UndirectedAdjacencyList(10);
        HashMap<Integer, Integer> representative = new HashMap<>();

        ConnectedComponents.connected_components(graph, representative);
        // Oracle map
        HashMap<Integer, Integer> oracle_map = new HashMap<>();
        connected_test_oracle(graph, oracle_map);
        assertEquals(representative, oracle_map);

        for (int i = 0; i < 10; i++) {
            Integer value = representative.get(i);
            assertEquals(i, value);
        }

        assertEquals(graph.numVertices(), oracle_map.size());

    }

    @Test
    public void inorder_test() {
        UndirectedAdjacencyList graph = new UndirectedAdjacencyList(10);
        HashMap<Integer, Integer> representative = new HashMap<>();

        graph.addEdge(0, 0);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(0, 3);
        graph.addEdge(0, 4);
        graph.addEdge(0, 5);
        graph.addEdge(0, 6);
        graph.addEdge(0, 7);
        graph.addEdge(0, 8);
        graph.addEdge(0, 9);

        ConnectedComponents.connected_components(graph, representative);
        // Oracle map
        HashMap<Integer, Integer> oracle_map = new HashMap<>();
        connected_test_oracle(graph, oracle_map);
        assertEquals(representative, oracle_map);

        for (int i = 0; i < 10; i++) {
            Integer value = representative.get(i);
            assertEquals(0, value);
        }
        assertEquals(graph.numVertices(), oracle_map.size());
    }

    @Test
    public void inorder2_test() {
        UndirectedAdjacencyList graph = new UndirectedAdjacencyList(10);
        HashMap<Integer, Integer> representative = new HashMap<>();

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        graph.addEdge(5, 6);
        graph.addEdge(6, 7);
        graph.addEdge(7, 8);
        graph.addEdge(8, 9);
        graph.addEdge(9, 0);

        ConnectedComponents.connected_components(graph, representative);
        // Oracle map
        HashMap<Integer, Integer> oracle_map = new HashMap<>();
        connected_test_oracle(graph, oracle_map);
        assertEquals(representative, oracle_map);

        for (int i = 0; i < 10; i++) {
            Integer value = representative.get(i);
            assertEquals(0, value);
        }
        assertEquals(graph.numVertices(), oracle_map.size());
    }

    @Test
    public void disconnected_test() {
        UndirectedAdjacencyList graph = new UndirectedAdjacencyList(10);
        HashMap<Integer, Integer> representative = new HashMap<>();



        // First Line
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);


        // Second line
        graph.addEdge(5, 6);
        graph.addEdge(6, 7);
        graph.addEdge(7, 8);
        graph.addEdge(8, 9);
//        graph.addEdge(9, 10);


        ConnectedComponents.connected_components(graph, representative);

        // Oracle map
        HashMap<Integer, Integer> oracle_map = new HashMap<>();
        connected_test_oracle(graph, oracle_map);
        assertEquals(representative, oracle_map);

        // Test first line
        for (int i = 0; i < 5; i++) {
            Integer value = representative.get(i);
            assertEquals(0, value);
        }

        // Test second line
        for (int i = 5; i < 10; i++) {
            Integer value = representative.get(i);
            assertEquals(5, value);
        }


        assertEquals(graph.numVertices(), oracle_map.size());
    }

    // "Known correct implementation" for testing
    public static <V> void
    connected_test_oracle(Graph<V> G, Map<V, V> representative) {
        for (V node : G.vertices()) {
            if (!representative.containsKey(node)) {
                representative.put(node, node);
                helper(G, representative, node, G.adjacent(node));
            }
        }

    }

    public static <V> void helper(Graph<V> G, Map<V, V> representative, V rep, Iterable<V> adjacent) {
        for (V adjacent_node : adjacent) {
            if (!representative.containsKey(adjacent_node)) {
                representative.put(adjacent_node, rep);
                helper(G, representative, rep, G.adjacent(adjacent_node));
            }
        }
    }

    @Test
    public void random_test() {
        Random rand = new Random(314159265);
        UndirectedAdjacencyList graph = new UndirectedAdjacencyList(10000);
        UndirectedAdjacencyList true_graph = new UndirectedAdjacencyList(10000);
        HashMap<Integer, Integer> map = new HashMap<>();
        HashMap<Integer, Integer> true_map = new HashMap<>();

        for (int i = 0; i < 10000; i++) {
            Integer first = rand.nextInt(10000);
            Integer second = rand.nextInt(10000);
            graph.addEdge(first, second);
            true_graph.addEdge(first, second);
        }

        ConnectedComponents.connected_components(graph, map);
        connected_test_oracle(true_graph, true_map);
        assertEquals(map.size(), true_map.size());
        assertEquals(map.size(), graph.numVertices());
        assertEquals(map, true_map);
    }


    @Test
    public void null_test() {
        UndirectedAdjacencyList graph = new UndirectedAdjacencyList(10);
        HashMap<Integer, Integer> representative = new HashMap<>();
        ConnectedComponents.connected_components(graph, representative);
        assertEquals(graph.numVertices(), representative.size());

    }

}
