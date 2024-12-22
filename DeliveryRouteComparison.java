import java.util.*;

public class DeliveryRouteComparison {

    private static int n; // Jumlah lokasi
    private static int[][] distance; // Matriks jarak
    private static boolean[] visited;
    private static int minDistanceBacktracking = Integer.MAX_VALUE;
    private static int minDistanceBruteForce = Integer.MAX_VALUE;
    private static List<Integer> bestRouteBacktracking = new ArrayList<>();
    private static List<Integer> bestRouteBruteForce = new ArrayList<>();
    private static List<List<Integer>> allRoutesBacktracking = new ArrayList<>();
    private static List<List<Integer>> allRoutesBruteForce = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input jumlah lokasi
        System.out.print("Masukkan jumlah lokasi: ");
        n = scanner.nextInt();
        distance = new int[n][n];
        visited = new boolean[n];

        // Input matriks jarak
        System.out.println("Masukkan matriks jarak (baris per baris):");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                distance[i][j] = scanner.nextInt();
            }
        }

        // Backtracking
        long startBacktracking = System.nanoTime();
        List<Integer> currentRoute = new ArrayList<>();
        currentRoute.add(0);
        visited[0] = true;
        backtrack(0, currentRoute, 0);
        long endBacktracking = System.nanoTime();
        long executionTimeBacktracking = endBacktracking - startBacktracking;

        // Output hasil Backtracking
        System.out.println("\n=== Backtracking ===");
        System.out.println("Riwayat rute:");
        for (List<Integer> route : allRoutesBacktracking) {
            System.out.println(route + " (Jarak: " + calculateTotalDistance(route) + ")");
        }
        System.out.println("Rute terbaik: " + bestRouteBacktracking);
        System.out.println("Jarak minimum: " + minDistanceBacktracking);
        System.out.println("Waktu eksekusi: " + executionTimeBacktracking + " ns");

        // Reset visited array untuk Brute Force
        Arrays.fill(visited, false);

        // Brute Force
        long startBruteForce = System.nanoTime();
        bruteForce();
        long endBruteForce = System.nanoTime();
        long executionTimeBruteForce = endBruteForce - startBruteForce;

        // Output hasil Brute Force
        System.out.println("\n=== Brute Force ===");
        System.out.println("Riwayat rute:");
        for (List<Integer> route : allRoutesBruteForce) {
            System.out.println(route + " (Jarak: " + calculateTotalDistance(route) + ")");
        }
        System.out.println("Rute terbaik: " + bestRouteBruteForce);
        System.out.println("Jarak minimum: " + minDistanceBruteForce);
        System.out.println("Waktu eksekusi: " + executionTimeBruteForce + " ns");

        scanner.close();
    }

    private static void backtrack(int currentNode, List<Integer> currentRoute, int currentDistance) {
        if (currentRoute.size() == n) {
            List<Integer> completeRoute = new ArrayList<>(currentRoute);
            completeRoute.add(0); // Add return to start
            int finalDistance = currentDistance + distance[currentNode][0];
            
            allRoutesBacktracking.add(new ArrayList<>(completeRoute));
            
            if (finalDistance < minDistanceBacktracking) {
                minDistanceBacktracking = finalDistance;
                bestRouteBacktracking = new ArrayList<>(completeRoute);
            }
            return;
        }

        for (int nextNode = 0; nextNode < n; nextNode++) {
            if (!visited[nextNode]) {
                visited[nextNode] = true;
                currentRoute.add(nextNode);
                backtrack(nextNode, currentRoute, currentDistance + distance[currentNode][nextNode]);
                visited[nextNode] = false;
                currentRoute.remove(currentRoute.size() - 1);
            }
        }
    }

    private static void bruteForce() {
        List<Integer> nodes = new ArrayList<>();
        for (int i = 1; i < n; i++) nodes.add(i);
        permute(nodes, new ArrayList<>());
    }

    private static void permute(List<Integer> nodes, List<Integer> currentRoute) {
        if (nodes.isEmpty()) {
            List<Integer> completeRoute = new ArrayList<>();
            completeRoute.add(0); // Add starting point
            completeRoute.addAll(currentRoute);
            completeRoute.add(0); // Add return to start
            
            int currentDistance = calculateTotalDistance(completeRoute);
            allRoutesBruteForce.add(new ArrayList<>(completeRoute));
            
            if (currentDistance < minDistanceBruteForce) {
                minDistanceBruteForce = currentDistance;
                bestRouteBruteForce = new ArrayList<>(completeRoute);
            }
            return;
        }

        for (int i = 0; i < nodes.size(); i++) {
            List<Integer> newNodes = new ArrayList<>(nodes);
            List<Integer> newRoute = new ArrayList<>(currentRoute);
            newRoute.add(newNodes.remove(i));
            permute(newNodes, newRoute);
        }
    }

    private static int calculateTotalDistance(List<Integer> route) {
        int totalDistance = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            totalDistance += distance[route.get(i)][route.get(i + 1)];
        }
        return totalDistance;
    }
}
