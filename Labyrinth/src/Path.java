import sun.awt.image.ImageWatched;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Eric on 3/25/2017.
 */
public class Path {

    /**
    * Determines which game board tiles are reachable from the provided tile.
    *  The tile location is provided as integer array: [row, column]
    * @param board being checked
    * @param locTile coordinates of tile currently on
    * @return arraylist of reachable tiles
    */
    public static ArrayList<Integer> getReachableTiles(Board board, int[] locTile){


        boolean[] move = new boolean[4];
        int BOARDSIZE = board.tiles.length;  // Assume square board
        ArrayList<LinkedList<Integer>> adjList = createAdjList(BOARDSIZE*BOARDSIZE);

        // Create an adjacency list representing the connected tiles on the board.
        for (int i = 0; i < BOARDSIZE; i++){
            for (int j = 0; j < BOARDSIZE; j++){
                int[] tileLoc = {i,j};
                // 'move' represents which directions tile [i,j] can move.
                move[Direction.up.getValue()] = canMove(board, tileLoc, Direction.up);
                move[Direction.right.getValue()] = canMove(board, tileLoc, Direction.right);
                move[Direction.down.getValue()] = canMove(board, tileLoc, Direction.down);
                move[Direction.left.getValue()] = canMove(board, tileLoc, Direction.left);

                addNode(adjList, tileLoc, move, BOARDSIZE);
            }
        }
        ArrayList<Integer> reachableTiles = breadthFirstSearch(adjList, getTileIndex(locTile, BOARDSIZE));

        return reachableTiles;
    }

    /**
     * Initialize an empty adjacency list with nNodes.
     * @param nNodes
     * @return adj list
     */
    private static ArrayList<LinkedList<Integer>> createAdjList(int nNodes){

        ArrayList<LinkedList<Integer>> adjList = new ArrayList<LinkedList<Integer>>();
        for (int i = 0; i < nNodes; i++){
            adjList.add(new LinkedList<Integer>());
        }
        return adjList;
    }

    /**
    * 
    *  Flattens the grid so that the first row is 0...size-1, the second is size...size+size-1, etc.
    *  If the specified tile is off the grid, return -1.
    * @param tileLoc
    * @param size
    * @return tileIndex
    */
    public static int getTileIndex(int[] tileLoc, int size){
        /*
        Flattens the grid so that the first row is 0...size-1, the second is size...size+size-1, etc.
        If the specified tile is off the grid, return -1.
         */
        int tileIndex = tileLoc[0]*size + tileLoc[1];
        if (tileIndex < 0){
            tileIndex = -1;
        }
        return tileIndex;
    }

    /**
    *Gets the tile coordinates based on its index (inverse of getTileIndex).
    * @param tileIndex
    * @param size
    * @return 
    */
    public static int[] getTileCoordinates(int tileIndex, int size){

        int[] tileCoordinates = new int[2];
        tileCoordinates[0] = (int)(tileIndex / size);
        tileCoordinates[1] = tileIndex % size;
        return tileCoordinates;
    }

    /**
     * Determines if it is possible to move from provided tile to the adjacent tile in moveDirection.
     * @param board
     * @param locCurrentTile
     * @param moveDirection
     * @return true if can move in direction - false if else
     */
    public static boolean canMove(Board board, int[] locCurrentTile, Direction moveDirection){
   
        boolean canMoveDirection = false;
        int board_size = board.tiles.length;
        Tile moveToTile;
        Tile currentTile = board.tiles[locCurrentTile[0]][locCurrentTile[1]];

        // If the current tile has a path up and it is not on the top row
        if     (moveDirection ==
                    Direction.up &&
                    currentTile.up &&
                    locCurrentTile[0] > 0){

            moveToTile = board.tiles[locCurrentTile[0] - 1][locCurrentTile[1]];

            if (moveToTile.down){
                canMoveDirection = true;
            }
        }

        // If the current tile has a path right and it is not on the right column
        else if (moveDirection ==
                     Direction.right &&
                     currentTile.right &&
                     locCurrentTile[1] < board_size-1){

            moveToTile = board.tiles[locCurrentTile[0]][locCurrentTile[1] + 1];

            if (moveToTile.left){
                canMoveDirection = true;
            }
        }

        // If the current tile has a path down and it is not on the bottom row
        else if (moveDirection ==
                     Direction.down &&
                     currentTile.down &&
                     locCurrentTile[0] < board_size-1){

            moveToTile = board.tiles[locCurrentTile[0] + 1][locCurrentTile[1]];

            if (moveToTile.up){
                canMoveDirection = true;
            }
        }

        // If the current tile has a path left and it is not on the left column
        else if (moveDirection ==
                Direction.left &&
                currentTile.left &&
                locCurrentTile[1] > 0){

            moveToTile = board.tiles[locCurrentTile[0]][locCurrentTile[1] - 1];

            if (moveToTile.right){
                canMoveDirection = true;
            }
        }
        return canMoveDirection;
    }

    /**
     * 
     *  Updates the input adjacency list so that the tile at 'tileLoc' is connected to the tiles adjacent to it
     *  if it is possible to move from the current tile to the adjacent tile.  It is possible to move from the 'tileLoc'
     *  tile to an adjacent tile if the corresponding direction of the adjacent tile is marked true in 'move'.
     * @param adjList
     * @param tileLoc
     * @param move
     * @param size 
     */
    public static void addNode(ArrayList<LinkedList<Integer>> adjList, int[] tileLoc, boolean[] move, int size){
        /*
        Updates the input adjacency list so that the tile at 'tileLoc' is connected to the tiles adjacent to it
        if it is possible to move from the current tile to the adjacent tile.  It is possible to move from the 'tileLoc'
        tile to an adjacent tile if the corresponding direction of the adjacent tile is marked true in 'move'.
         */

        // Find the integer representation of the tile at tileLoc (0-48)
        int tileIndex = getTileIndex(tileLoc, size);

        // Find the integer representation of adjacent tiles [up, right, down, left]
        int[] adjTiles = {getTileIndex(new int[]{tileLoc[0]-1, tileLoc[1]}, size),
                          getTileIndex(new int[]{tileLoc[0], tileLoc[1]+1}, size),
                          getTileIndex(new int[]{tileLoc[0]+1, tileLoc[1]}, size),
                          getTileIndex(new int[]{tileLoc[0], tileLoc[1]-1}, size)};

        int i = 0;
        for (boolean direction:move){
            if (direction){
                //Connect the tile at 'tileLoc' to the appropriate adjacent tile.
                adjList.get(tileIndex).add(adjTiles[i]);
            }
            i++;
        }
    }

    /**
     * Breadth First Search - returns list of nodes that are reachable from the start node.
     *
     * @param adjList
     * @param startNode
     * @return 
     */
    public static ArrayList<Integer> breadthFirstSearch(ArrayList<LinkedList<Integer>> adjList, int startNode){

        ArrayList<Integer> reachableNodes = new ArrayList<>();
        int nNodes = adjList.size();
        boolean[] discovered = new boolean[nNodes];
        Queue<Integer> Q = new LinkedList<Integer>();
        int currentNode;

        // Initialize
        Q.add(startNode);
        for (int i = 0; i < nNodes; i++){
            discovered[i] = false;
        }

        while (!Q.isEmpty()){
            currentNode = Q.poll();
            if (!discovered[currentNode]){
                discovered[currentNode] = true;
                reachableNodes.add(currentNode);

                for (int n:adjList.get(currentNode)){
                    if (!discovered[n]){
                        Q.add(n);
                    }
                }
            }
        }

        return reachableNodes;

    }
}
