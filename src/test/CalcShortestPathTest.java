import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

public class CalcShortestPathTest {
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


    @Test
    public void test1() {
        addEdge("A", "B");
        addEdge("B", "C");
        addEdge("C", "D");
        addEdge("B", "D");
        String result = calcShortestPath(graph, "E", "V");
        assertEquals("起始单词或结束单词不存在于图中。", result);
    }

    @Test
    public void test2() {
        addEdge("A", "B");
        addEdge("B", "C");
        addEdge("C", "D");
        addEdge("B", "D");
        String result = calcShortestPath(graph, "A", "R");
        assertEquals("起始单词或结束单词不存在于图中。", result);
    }

    @Test
    public void test3() {
        addEdge("A", "B");
        addEdge("B", "C");
        addEdge("C", "D");
        addEdge("B", "D");
        String result = calcShortestPath(graph, "A", "A");
        assertEquals("没有路径从 A 到 A", result);
    }
    @Test
    public void test4() {
        addEdge("A", "B");
        addEdge("B", "C");
        addEdge("C", "D");
        addEdge("B", "D");
        String result = calcShortestPath(graph, "C", "B");
        assertEquals("没有路径从 C 到 B", result);
    }
    @Test
    public void test5() {
        addEdge("A", "B");
        addEdge("B", "C");
        addEdge("C", "D");
        addEdge("B", "D");
        String result = calcShortestPath(graph, "A", "D");
        assertEquals("A -> B -> D", result);
    }

     //计算最短路径
    private String calcShortestPath(Map<String, Map<String, Integer>> graph,String word1, String word2) {
        // 检查起始单词和结束单词是否在图中
        if (!graph.containsKey(word1) || !graph.containsKey(word2)) {
            return "起始单词或结束单词不存在于图中。";
        }

        // 初始化距离和前驱节点的映射
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> predecessors = new HashMap<>();

        // 使用优先队列来管理节点的处理顺序，按距离的升序排列
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        // 设置起始节点的距离为0，其他节点的距离默认为无穷大
        distances.put(word1, 0);
        pq.add(word1);

        // 主循环，直到处理完所有节点或找到目标节点
        while (!pq.isEmpty()) {
            // 取出距离最近的节点
            String current = pq.poll();

            // 如果已经到了目标节点，跳出循环
            if (current.equals(word2)) break;

            // 遍历当前节点的所有邻居
            for (Map.Entry<String, Integer> neighbor : graph.get(current).entrySet()) {
                // 计算从当前节点到邻居节点的新距离
                int newDist = distances.get(current) + neighbor.getValue();

                // 如果新距离比已有的距离短，更新距离和前驱节点
                if (newDist < distances.getOrDefault(neighbor.getKey(), Integer.MAX_VALUE)) {
                    distances.put(neighbor.getKey(), newDist);
                    predecessors.put(neighbor.getKey(), current);
                    pq.add(neighbor.getKey());
                }
            }
        }

        // 如果前驱节点中没有目标节点，说明没有路径
        if (!predecessors.containsKey(word2)) {
            return "没有路径从 " + word1 + " 到 " + word2;
        }

        // 反向构建从终点到起点的路径
        List<String> path = new LinkedList<>();
        for (String at = word2; at != null; at = predecessors.get(at)) {
            path.add(at);
        }

        // 反转路径，使其从起点到终点
        Collections.reverse(path);
        /*这里为简便注释掉无关函数
        String dotFilePath = "G:/homework/software1/untitled/myGraph1.dot";
        try {
            writeToDotFile1(graph, dotFilePath, path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        */
        // 返回路径的字符串表示形式
        return String.join(" -> ", path);
    }
}
