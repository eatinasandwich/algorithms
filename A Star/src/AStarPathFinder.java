import java.awt.Point;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * 
 * This class implements a static A* method. 
 *
 */
public class AStarPathFinder {
	
	public static Stack<Point> AStar(final AStarNode[][] map, final Point start, final Point end) {
		int width = map.length;
		int height = map[0].length;
		
		PriorityQueue<AStarNode> open_list = new PriorityQueue<AStarNode>(width * height);
		ArrayList<AStarNode> closed_list = new ArrayList<AStarNode>();
		open_list.add((map[start.x][start.y]));
		while (!open_list.isEmpty()) {
			AStarNode temp = open_list.remove();
			closed_list.add(temp);
			if (temp.location.equals(end)) {
				Stack<Point> ret = new Stack<Point>();
				AStarNode current_node = temp;
				while (current_node.parent != null) {
					ret.push(current_node.location);
					current_node = current_node.parent;
				}
				ret.push(current_node.location);
				return ret;
			}
			if (isPassable(map, temp.location.x - 1, temp.location.y) && !closed_list.contains(map[temp.location.x - 1][temp.location.y])) {
				AStarNode westNode = map[temp.location.x - 1][temp.location.y];
				if (open_list.contains(westNode)) {
					if (westNode.g > temp.g + 10) {
						westNode.g = temp.g + 10;
						westNode.parent = temp;
					}
				} else {
					westNode.parent = temp;
					calcGHF(westNode, 10, end);
					open_list.add(westNode);
				}
			}
			if (isPassable(map, temp.location.x - 1, temp.location.y - 1) && !closed_list.contains(map[temp.location.x - 1][temp.location.y - 1])) {
				AStarNode northwestNode = map[temp.location.x - 1][temp.location.y - 1];
				if (open_list.contains(northwestNode)) {
					if (northwestNode.g > temp.g + 14) {
						northwestNode.g = temp.g + 10;
						northwestNode.parent = temp;
					}
				} else {
					northwestNode.parent = temp;
					calcGHF(northwestNode, 14, end);
					open_list.add(northwestNode);
				}
			}
			if (isPassable(map, temp.location.x, temp.location.y - 1) && !closed_list.contains(map[temp.location.x][temp.location.y - 1])) {
				AStarNode northNode = map[temp.location.x][temp.location.y - 1];
				if (open_list.contains(northNode)) {
					if (northNode.g > temp.g + 10) {
						northNode.g = temp.g + 10;
						northNode.parent = temp;
					}
				} else {
					northNode.parent = temp;
					calcGHF(northNode, 10, end);
					open_list.add(northNode);
				}
			}
			if (isPassable(map, temp.location.x + 1, temp.location.y - 1) && !closed_list.contains(map[temp.location.x + 1][temp.location.y - 1])) {
				AStarNode northeastNode = map[temp.location.x + 1][temp.location.y - 1];
				if (open_list.contains(northeastNode)) {
					if (northeastNode.g > temp.g + 14) {
						northeastNode.g = temp.g + 14;
						northeastNode.parent = temp;
					}
				} else {
					northeastNode.parent = temp;
					calcGHF(northeastNode, 14, end);
					open_list.add(northeastNode);
				}
			}
			if (isPassable(map, temp.location.x + 1, temp.location.y) && !closed_list.contains(map[temp.location.x + 1][temp.location.y])) {
				AStarNode eastNode = map[temp.location.x + 1][temp.location.y];
				if (open_list.contains(eastNode)) {
					if (eastNode.g > temp.g + 10) {
						eastNode.g = temp.g + 10;
						eastNode.parent = temp;
					}
				} else {
					eastNode.parent = temp;
					calcGHF(eastNode, 10, end);
					open_list.add(eastNode);
				}
			}
			if (isPassable(map, temp.location.x + 1, temp.location.y + 1) && !closed_list.contains(map[temp.location.x + 1][temp.location.y + 1])) {
				AStarNode southeastNode = map[temp.location.x + 1][temp.location.y + 1];
				if (open_list.contains(southeastNode)) {
					if (southeastNode.g > temp.g + 14) {
						southeastNode.g = temp.g + 14;
						southeastNode.parent = temp;
					}
				} else {
					southeastNode.parent = temp;
					calcGHF(southeastNode, 14, end);
					open_list.add(southeastNode);
				}
			}
			if (isPassable(map, temp.location.x, temp.location.y + 1) && !closed_list.contains(map[temp.location.x][temp.location.y + 1])) {
				AStarNode southNode = map[temp.location.x][temp.location.y + 1];
				if (open_list.contains(southNode)) {
					if (southNode.g > temp.g + 10) {
						southNode.g = temp.g + 10;
						southNode.parent = temp;
					}
				} else {
					southNode.parent = temp;
					calcGHF(southNode, 10, end);
					open_list.add(southNode);
				}
			}
			if (isPassable(map, temp.location.x - 1, temp.location.y + 1) && !closed_list.contains(map[temp.location.x - 1][temp.location.y + 1])) {
				AStarNode southwestNode = map[temp.location.x - 1][temp.location.y + 1];
				if (open_list.contains(southwestNode)) {
					if (southwestNode.g > temp.g + 14) {
						southwestNode.g = temp.g + 14;
						southwestNode.parent = temp;
					}
				} else {
					southwestNode.parent = temp;
					calcGHF(southwestNode, 14, end);
					open_list.add(southwestNode);
				}
			}
				
				
		}
		
		return null;
	}
	
	private static void calcGHF(final AStarNode node, final int gCost, final Point end) {
		if (node.parent != null) {
			node.g = node.parent.g + gCost;
			node.h = Math.abs(end.x - node.location.x) * 10 + Math.abs(end.y - node.location.y) * 10;
			node.f = node.g + node.h;
		}
	}
	
	private static boolean isPassable(final AStarNode[][] map, final int x, final int y) {
		return x >= 0 && y >= 0 && 
				x < map.length && y < map[0].length &&
				map[x][y].passable;
	}
	
	
	
}
