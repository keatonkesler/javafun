import java.util.Random;
import java.util.ArrayList;
import java.awt.Point;

public class RandomWalk
{
	private final Point START; // bottomLeft (0, gridSize - 1) 
	private final Point END; // topRight (gridSize - 1, 0) 
    private ArrayList<Point> path;
    private boolean done;
	private Point current;

    private Random random;

    private enum Direction { NORTH, EAST, SOUTH, WEST };

    public RandomWalk(int gridSize)
    {
        path = new ArrayList<Point>();
        random = new Random();

        done = false;
		START = new Point(0, gridSize - 1);
		END = new Point(gridSize - 1, 0);

		current = new Point(START.x, START.y);
        path.add(current);
    }
    
    public RandomWalk(int gridSize, long seed)
    {
        this(gridSize);
        random.setSeed(seed);
    }

    public void step()
    {
		int x = (int) current.getX();
		int y = (int) current.getY();

        // Get next direction
        switch(getNextDirection())
        {
            case NORTH:
                y--;
                break;
            case EAST:
                x++;
                break;
            default:
                System.err.println("Invalid direction.");
        }

        // Add the point
		current = new Point(x, y);
        path.add(current);

        // Check if done (topRight)
        if(current.equals(END)) {
            done = true;
		}
    }

    private Direction getNextDirection()
    {
        // can we only move one way?
        if(current.getX() == END.getX()) {
            return Direction.NORTH;
		}
        if(current.getY() == END.getY()) {
            return Direction.EAST;
		}

        // we can move anywhere, so randomly choose.
        int rand = random.nextInt(2);
        if(rand == 0)
            return Direction.EAST;
        else
            return Direction.NORTH;
    }

    public void createWalk()
    {
        while(!done)
        {
            step();
        }
    }

    public boolean isDone()
    {
        return done;
    }

    public ArrayList<Point> getPath()
    {
        return path;
    }

    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        for(Point p : path)
        {
            builder.append("[" + p.x + "," + p.y + "] ");
        }
        return builder.toString();
    }
}