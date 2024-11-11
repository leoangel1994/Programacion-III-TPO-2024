import java.util.Arrays;

class Dijkstra {
    public static void dijkstra(int[][] graph, int source) {
        int count = graph.length;
        int[] dist = new int[count];
        boolean[] visited = new boolean[count];

        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        for (int i = 0; i < count - 1; i++) {
            int u = findMinimumDistance(dist, visited);
            visited[u] = true;

            for (int v = 0; v < count; v++) {
                if (!visited[v] && graph[u][v] != 0 && (dist[u] + graph[u][v] < dist[v])) {
                    dist[v] = dist[u] + graph[u][v];
                }
            }
        }

        printSolution(dist);
    }

    public static int findMinimumDistance(int[] dist, boolean[] visited) {
        int minIndex = -1;
        for (int i = 0; i < dist.length; i++) {
            if (!visited[i] && (minIndex == -1 || dist[i] < dist[minIndex])) {
                minIndex = i;
            }
        }
        return minIndex;
    }

    public static void printSolution(int[] dist) {
        System.out.println("Vertex\t\tDistance from Source");
        for (int i = 0; i < dist.length; i++) {
            System.out.println(i + "\t\t" + dist[i]);
        }
    }
}