import java.awt.Point;
import java.util.Stack;

/**
 * 
 * This class implements the A* algorithm for path-finding. 
 * It uses a two dimensional array of integers to represent a graph to be traversed.
 * 0 = untouched
 * 1 = on the open list
 * 2 = on the closed list
 * Parent nodes and f-costs are also stored in two dimensional arrays
 * @author Jared Helm
 */
public class Main {
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		int width = 10;
		int height = 10;
		AStarNode[][] map = new AStarNode[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				map[i][j] = new AStarNode(new Point(i,j));
			}
		}
		map[4][2].passable = false;
		map[4][3].passable = false;
		map[4][4].passable = false;
		map[4][5].passable = false;
		map[4][6].passable = false;
		
		map[0][6].passable = false;
		map[1][6].passable = false;
		map[2][6].passable = false;
		map[3][6].passable = false;
		
		map[1][2].passable = false;
		map[2][2].passable = false;
		map[3][2].passable = false;
		Stack<Point> path = AStarPathFinder.AStar(map, new Point(1, 4), new Point(8, 5));
		while (!path.isEmpty()) {
			System.out.println(path.pop());
		}
		
		long end = System.currentTimeMillis();
		System.out.println("This took " + (end - start) + " milliseconds.");
	}
	
	
}






















