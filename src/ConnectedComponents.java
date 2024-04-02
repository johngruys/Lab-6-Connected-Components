import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConnectedComponents {

    /*
     * TODO
     */
    public static <V> void
    connected_components(Graph<V> G, Map<V, V> representative) {
        for (V node : G.vertices()) {
            if (!representative.containsKey(node)) {
                representative.put(node, node);
                connected_components_helper(G, representative, node, G.adjacent(node));
            }
        }
    }

    public static <V> void connected_components_helper(Graph<V> G, Map<V, V> representative, V rep, Iterable<V> adjacent) {
        for (V adjacent_node : adjacent) {
            if (!representative.containsKey(adjacent_node)) {
                representative.put(adjacent_node, rep);
                connected_components_helper(G, representative, rep, G.adjacent(adjacent_node));
            }
        }
    }


}
