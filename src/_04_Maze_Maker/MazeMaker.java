package _04_Maze_Maker;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MazeMaker {

	private static int width;
	private static int height;

	private static Maze maze;

	private static Stack<Cell> uncheckedCells = new Stack<Cell>();
	


	public static Maze generateMaze(int w, int h) {
		width = w;
		height = h;
		maze = new Maze(width, height);

		// 4. select a random cell to start
		
		// 5. call selectNextPath method with the randomly selected cell

		try {
			selectNextPath(maze.getCell(new Random().nextInt(width), new Random().nextInt(height)));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return maze;
	}

	// 6. Complete the selectNextPathMethod
	private static void selectNextPath(Cell currentCell) {
		// A. mark cell as visited
		currentCell.setBeenVisited(true);
		// B. check for unvisited neighbors using the cell
		ArrayList<Cell> neighbors = getUnvisitedNeighbors(currentCell);
		// C. if has unvisited neighbors,
		if (neighbors.size() > 0) {
			System.out.println("a"+neighbors.size());
		// C1. select one at random.
			Cell rando = neighbors.get(new Random().nextInt(neighbors.size()));
		// C2. push it to the stack
			uncheckedCells.push(rando);
		// C3. remove the wall between the two cells
			removeWalls(rando, currentCell);
		// C4. make the new cell the current cell and mark it as visited
			currentCell = rando;
			rando.setBeenVisited(true);
		// C5. call the selectNextPath method with the current cell
			selectNextPath(currentCell);
		// D. if all neighbors are visited
		} else {
		// D1. if the stack is not empty
			if (!uncheckedCells.isEmpty()) {
		// D1a. pop a cell from the stack
				
		// D1b. make that the current cell
				currentCell = uncheckedCells.pop();
				System.out.println("b");
		// D1c. call the selectNextPath method with the current cell
				selectNextPath(currentCell);
			}
		}

	}

	// 7. Complete the remove walls method.
	// This method will check if c1 and c2 are adjacent.
	// If they are, the walls between them are removed.
	private static void removeWalls(Cell c1, Cell c2) {
		//if (getUnvisitedNeighbors(c1).contains(c2)) {
			if (c1.getY() < c2.getY()) {
				c2.setNorthWall(false);
				c1.setSouthWall(false);
			} else if (c1.getY() > c2.getY()) {
				c2.setSouthWall(false);
				c1.setNorthWall(false);
			} else if (c1.getX() < c2.getX()) {
				c2.setWestWall(false);
				c1.setEastWall(false);
			} else if (c1.getX() > c2.getX()) {
				c2.setEastWall(false);
				c1.setWestWall(false);
			} 
		//}
	}

	// 8. Complete the getUnvisitedNeighbors method
	// Any unvisited neighbor of the passed in cell gets added
	// to the ArrayList
	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {
		ArrayList<Cell> cells = new ArrayList<Cell>();
		
		try {
		Cell[] neigh = { maze.getCell(c.getX(), c.getY() - 1),
			maze.getCell(c.getX() - 1, c.getY()),
			maze.getCell(c.getX() + 1, c.getY()),
			maze.getCell(c.getX(), c.getY() + 1),
			
			};
		
		for (Cell c1 : neigh) {
			if (c1 != null && !c1.hasBeenVisited()) {
				cells.add(c1);
			}
		}
		
		for (int i = 0; i < cells.size(); i++) {
			if (cells.get(i) == null) {
				cells.remove(i);
				i--;
			}
		}
		} catch (Exception e) {
			System.out.println("out of bounds");
		}
		
		return cells;
	}
}
