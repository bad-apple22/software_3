import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryBridgeWordsTest {

    private Map<String, Map<String, Integer>> graph;

    @Before
    public void setUp() {
        graph = new HashMap<>();
    }

    private void addEdge(String from, String to) {

        // 添加节点a和它的邻接表
        if (!graph.containsKey(from)) {
            graph.put(from, new HashMap<>());
        }

        graph.get(from).put(to, 1); // 添加边a -> b

        // 添加节点b和它的邻接表
        if (!graph.containsKey(to)) {
            graph.put(to, new HashMap<>());
        }

    }

    private void showDirectedGraph() {
        for (Map.Entry<String, Map<String, Integer>> entry : graph.entrySet()) {
            System.out.print(entry.getKey() + " -> ");
            for (Map.Entry<String, Integer> edge : entry.getValue().entrySet()) {
                System.out.print(edge.getKey() + "(" + edge.getValue() + ") ");
            }
            System.out.println();
        }
    }

    @Test
    public void testCase1() {
        addEdge("A", "B");
        addEdge("B", "C");
        showDirectedGraph(); // Print graph to verify its structure

        String result = queryBridgeWords(graph, "A", "C");
        assertEquals("The bridge word from A to C is: B.", result);
    }

    @Test
    public void testCase2() {
        addEdge("A", "B");
        addEdge("C", "D");
        String result = queryBridgeWords(graph, "A", "D");
        assertEquals("No bridge words from A to D!", result);
    }

    @Test
    public void testCase3() {
        addEdge("A", "B");
        addEdge("B", "C");
        String result = queryBridgeWords(graph, "A", "Y");
        assertEquals("No word1 or word2 in the graph!", result);
    }

    @Test
    public void testCase4() {
        addEdge("A", "B");
        addEdge("B", "C");
        String result = queryBridgeWords(graph, "X", "C");
        assertEquals("No word1 or word2 in the graph!", result);
    }

    @Test
    public void testCase5() {
        addEdge("A", "B");
        addEdge("B", "C");
        String result = queryBridgeWords(graph, "X", "Y");
        assertEquals("No word1 or word2 in the graph!", result);
    }

    @Test
    public void testCase6() {
        String result = queryBridgeWords(graph, "A", "B");
        assertEquals("No word1 or word2 in the graph!", result);
    }

    @Test
    public void testCase7() {
        addEdge("A", "A");
        showDirectedGraph(); // Print graph to verify its structure
        String result = queryBridgeWords(graph, "A", "A");
        assertEquals("No bridge words from A to A!", result);
    }

    @Test
    public void testCase8() {
        addEdge("A", "B");
        addEdge("A", "C");
        addEdge("B", "D");
        addEdge("C", "D");

        String result = queryBridgeWords(graph, "A", "D");
        assertEquals("The bridge words from A to D are: B, C.", result);
    }


    // This is the method under test
    //查询桥接词
    private String queryBridgeWords(Map<String, Map<String, Integer>> graph, String word1, String word2) {

        if (!graph.containsKey(word1) || !graph.containsKey(word2)) {
            return "No word1 or word2 in the graph!";
        }

        List<String> bridgeWords = new ArrayList<>();
        Map<String, Integer> edges1 = graph.get(word1);

        for (String intermediate : edges1.keySet()) {
            // 排除与word1或word2相同的单词作为桥接词
            if (!intermediate.equals(word1) && !intermediate.equals(word2) && graph.containsKey(intermediate) && graph.get(intermediate).containsKey(word2)) {
                bridgeWords.add(intermediate);
            }
        }

        if (bridgeWords.isEmpty()) {
            return "No bridge words from " + word1 + " to " + word2 + "!";
        } else if (bridgeWords.size() == 1) {
            return "The bridge word from " + word1 + " to " + word2 + " is: " + bridgeWords.get(0) + ".";
        } else {
            StringBuilder result = new StringBuilder("The bridge words from " + word1 + " to " + word2 + " are: ");
            for (int i = 0; i < bridgeWords.size(); i++) {
                result.append(bridgeWords.get(i));
                if (i < bridgeWords.size() - 1) {
                    result.append(", ");
                }
            }
            System.out.println("无效的选择，请重试。");
            result.append(".");
            return result.toString();
        }
    }
}