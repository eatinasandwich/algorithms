import java.awt.Point;


public class AStarNode implements Comparable<AStarNode> {
	Point location;
	int f;
	int g;
	int h;
	AStarNode parent;
	boolean passable;
	
	public AStarNode(final Point the_location) {
		location = the_location;
		f = 0;
		g = 0;
		h = 0;
		passable = true;
	}
	
	public boolean equals(final Object other) {
		if (!(other instanceof AStarNode)) {
			return false;
		} 
		AStarNode node = (AStarNode) other;
		if (node.location.x == location.x 
				&& node.location.y == location.y) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(AStarNode other) {
		AStarNode that_node;
		if (other instanceof AStarNode) {
			that_node = (AStarNode) other;
			return f - that_node.f;
		} else {
			return 0;
		}
	}
	
	@Override
	public String toString() {
		return "location: (" + location.x + ", " + location.y + ") f: " + f + " g: " + g + " h: " + h;
	}
}
