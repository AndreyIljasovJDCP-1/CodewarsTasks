package codewars.com;

import java.util.*;

public class TripletsTopologicalGraph {
    public static void main(String[] args) {

        char[][] triplets = {
                {'t', 'u', 'p'},
                {'w', 'h', 'i'},
                {'t', 's', 'u'},
                {'a', 't', 's'},
                {'h', 'a', 'p'},
                {'t', 'i', 's'},
                {'w', 'h', 's'}
        };


        System.out.println(recoverSecret(triplets));
    }

    /**
     * @see <a href="https://www.codewars.com/kata/53f40dff5f9d31b813000774">Recover a secret string
     *     from random triplets</a>
     * @param triplets triplets
     * @return secret string "whatisup"
     */
    public static String recoverSecret(char[][] triplets) {
        Map<Character, Integer> map = new HashMap<>();
        for (char[] triplet : triplets) {
            for (int i = 0; i < triplet.length; i++) {
                map.merge(triplet[i], i, Math::max);
            }
        }
        System.out.println(map);

        for (int i = 0; i < 3; i++) {
            boolean restart = true;
            while (restart) {
                restart = false;
                for (char[] triplet : triplets) {
                    int index = map.get(triplet[i]);
                    if (index <= i) continue;
                    int diff = index - i;
                    for (int j = i; j < 3; j++) {
                        int indexMax = map.get(triplet[j]);
                        if (j + diff > indexMax) {
                            map.put(triplet[j], j + diff);
                            restart = true;
                            break;
                        }
                    }
                    if (restart) break;
                }
            }
        }

        System.out.println(map);
        char[] secret = new char[map.size()];
        map.forEach((key, value) -> secret[value] = key);
        return new String(secret);
        /*String[] secret = new String[map.size()];
        map.forEach((key, value) -> secret[value] = String.valueOf(key));*/
        // return String.join("", secret);
    }

    public String recoverSecretGraph(char[][] triplets) {
        Digraph dg = new Digraph(triplets); // build digraph
        Topological top = new Topological(dg); // discover topological order

        // Build secret from topological order
        StringBuilder secret = new StringBuilder();
        for (Integer ch : top.topological())
            secret.append((char) (ch + 'a'));

        return secret.toString();
    }

    private class Digraph {
        final int V = 26;
        final Set<Integer>[] G;
        final boolean[] isVertex;

        Digraph(char[][] triplets) {
            // This is a sparse array
            isVertex = new boolean[V];

            // Initialize digraph array
            G = (HashSet<Integer>[]) new HashSet[V];
            for (int i = 0; i < V; i++)
                G[i] = new HashSet<>();

            // Build the digraph from input, offsetting by int-value of 'a'
            for (char[] triplet: triplets) {
                addEdge(triplet[0] - 'a', triplet[1] - 'a');
                addEdge(triplet[0] - 'a', triplet[2] - 'a');
                addEdge(triplet[1] - 'a', triplet[2] - 'a');
            }
        }

        // Add edge from v -> w
        private void addEdge(int v, int w) {
            isVertex[v] = isVertex[w] = true;
            G[v].add(w);
        }

        // Return set of vertices pointed to by `v`
        Iterable<Integer> to(int v) {
            return G[v];
        }

        // Size of array (number of vertices <= size of array)
        int size() {
            return V;
        }

        // true if this array index points to a vertex; false otherwise
        boolean isVertex(int v) {
            return isVertex[v];
        }
    }

    // This class uses the depth-first search algorithm to find the
    // topological order
    class Topological {
        private boolean[] marked;
        private Deque<Integer> order;

        // Build the Iterable of topo-order
        Topological(Digraph dg) {
            final int V = dg.size();
            order = new ArrayDeque<>();
            marked = new boolean[V];

            for (int v = 0; v < V; v++)
                if (!marked[v] && dg.isVertex[v]) dfs(dg, v);
        }

        // Depth-first search
        private void dfs(Digraph dg, int v) {
            marked[v] = true;
            for (int w : dg.to(v))
                if (!marked[w]) dfs(dg, w);
            order.addFirst(v);
        }

        // Returns the topological ordering as an Iterable
        Iterable<Integer> topological() {
            return order;
        }
    }
}
