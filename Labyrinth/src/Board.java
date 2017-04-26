
/**
 *
 * @author AlexBeard
 *
 *This is an instance of board - stores tile array, sets tiles initial positions
 *
 */
import javafx.scene.layout.*;
import java.util.ArrayList;
import java.util.Random;

public class Board extends Pane {
    
    //only used for extra tile board
    Board tileBoard;
    int x;
    int y;
    Tile extraTile;

    public static final int rows = 7;
    public static final int cols = 7;
    public static final int boardSize = 500;
    public static final double squareSize = boardSize / rows;

    //constants
    static final int CORNER_TILE = 16;
    static final int TEE_TILE = 6;
    static final int LINE_TILE = 12;

    //array of tiles
    Tile[][] tiles;
    Tile extra_tile;

    //constructor (no args needed) - standard board
    public Board() {
        tiles = new Tile[cols][rows];
        this.setPrefHeight(boardSize);
        this.setPrefWidth(boardSize);

        // Create movable tile pool:
        ArrayList<Tile> tilePool = new ArrayList<Tile>();
        for (int i = 0; i < CORNER_TILE; i++) {
            tilePool.add(new Tile(this, 0, 0, false, true, true, false, true, false));
        }
        for (int i = 0; i < TEE_TILE; i++) {
            tilePool.add(new Tile(this, 0, 0, true, true, true, false, true, false));
        }
        for (int i = 0; i < LINE_TILE; i++) {
            tilePool.add(new Tile(this, 0, 0, true, false, true, false, true, false));
        }
        Random rand = new Random();

        int upperLeftX = 0;
        int upperLeftY = 0;

        //all tiles that can move, with all 4 directions 
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                // non-movable tiles
                if (i == 0 && j == 0) {
                    tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, false, true, true, false, false, true);
                } else if (i == 0 && j == 2) {
                    tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, false, true, true, true, false, false);
                } else if (i == 0 && j == 4) {
                    tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, false, true, true, true, false, false);
                } else if (i == 0 && j == 6) {
                    tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, false, false, true, true, false, false);
                } else if (i == 2 && j == 0) {
                    tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, true, true, true, false, false, false);
                } else if (i == 2 && j == 2) {
                    tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, true, true, true, false, false, false);
                } else if (i == 2 && j == 4) {
                    tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, false, true, true, true, false, false);
                } else if (i == 2 && j == 6) {
                    tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, true, false, true, true, false, false);
                } else if (i == 4 && j == 0) {
                    tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, true, true, true, false, false, false);
                } else if (i == 4 && j == 2) {
                    tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, true, true, false, true, false, false);
                } else if (i == 4 && j == 4) {
                    tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, true, false, true, true, false, false);
                } else if (i == 4 && j == 6) {
                    tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, true, false, true, true, false, false);
                } else if (i == 6 && j == 0) {
                    tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, true, true, false, false, false, false);
                } else if (i == 6 && j == 2) {
                    tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, true, true, false, true, false, false);
                } else if (i == 6 && j == 4) {
                    tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, true, true, false, true, false, false);
                } else if (i == 6 && j == 6) {
                    tiles[i][j] = new Tile(this, upperLeftX, upperLeftY, true, false, false, true, false, true);
                }
                // movable tiles - pick a random tile from the tilepool
                else {
                    tiles[i][j] = tilePool.remove(rand.nextInt(tilePool.size()));

                    // Update position
                    tiles[i][j].setPosition(upperLeftX, upperLeftY);

                    // Random orientation
                    tiles[i][j].rotateRight(rand.nextInt(4));

                    tiles[i][j].updateColors();
                }

                //increment X component
                upperLeftX += squareSize;
            }
            //implement Y component
            upperLeftY += squareSize;

            //reset X component
            upperLeftX = 0;

        }
        // The only tile left is the extra maze tile
        extra_tile = tilePool.remove(0);
        setTreasure(tiles);
    }
    
    //constructor for extraTile Board
    public Board(Board tileBoard, int x, int y) {
        this.setPrefHeight(squareSize);
        this.setPrefWidth(squareSize);
        
        this.tileBoard= tileBoard;
        this.x = x;
        this.y = y;
                
        Tile extraTile = new Tile(tileBoard.extra_tile, this);
        extraTile.setPosition(x, y);
        
    }
    
    //used only for extra tile board
    public void updateExtraTileBoard() {
        
        extraTile = new Tile(tileBoard.extra_tile, this);
        extraTile.setPosition(x, y);
        extraTile.extraTileTreasure();
    }

    public void setTreasure(Tile[][] tiles) {
        tiles[0][2].setTileTreasure('A');
        tiles[0][4].setTileTreasure('B');
        tiles[2][0].setTileTreasure('C');
        tiles[2][2].setTileTreasure('D');
        tiles[2][4].setTileTreasure('E');
        tiles[2][6].setTileTreasure('F');
        tiles[4][0].setTileTreasure('G');
        tiles[4][2].setTileTreasure('H');
        tiles[4][4].setTileTreasure('I');
        tiles[4][6].setTileTreasure('J');
        tiles[6][2].setTileTreasure('K');
        tiles[6][4].setTileTreasure('L');
        
        for (int i = 12; i < 24; i++) {
            Random randX = new Random();
            Random randY = new Random();
            int x = randX.nextInt(rows);
            int y = randY.nextInt(cols);

            Tile currTile = tiles[x][y];

            while ((currTile.hasTreasure()) || currTile.getIsStart()) {
                currTile = tiles[randX.nextInt(rows)][randY.nextInt(cols)];
            }
            currTile.setTileTreasure((char) (i + 65));
        }
    }

    public int[] getTileCoordinates(double x, double y) {

        int roundX = (int) Math.round(x);
        int roundY = (int) Math.round(y);
        int roundSquareSize = (int) Math.round(squareSize);

        return new int[]{(roundY - (roundY % roundSquareSize)) / roundSquareSize, (roundX - (roundX % roundSquareSize)) / roundSquareSize};
    }

    public int[] getPlayerLocation(Player player) {
        int i = 0;
        int j = 0;
        int[] location = new int[2];
        for (Tile[] trow : tiles) {
            for (Tile t : trow) {
                if (t.hasPlayer(player)) {
                    location[0] = i;
                    location[1] = j;
                }
                j++;
            }
            j = 0;
            i++;
        }
        return location;
    }

    public boolean addPlayer(Player player, int[] locTile) {
        Tile currTile = tiles[locTile[0]][locTile[1]];
        currTile.setPlayer(player);
        if (currTile.hasTreasure()) {
            return checkTreasureMatch(player, currTile);
        } 
        return false;
    }

    public void removeTreasure(int[] locTile) {
        Tile currTile = tiles[locTile[0]][locTile[1]];
        currTile.removeTreasure();
        currTile.printTileTreasure();
    }

    public void removePlayer(Player player) {
        int[] tileCoordinates = getPlayerLocation(player);
        tiles[tileCoordinates[0]][tileCoordinates[1]].removePlayer(player);

    }

    public boolean checkTreasureMatch(Player p1, Tile tile) {
        return p1.getTreasure().getValueAsString().charAt(0) == tile.getTreasure();
    }

    /**
     *
     * @param xIndex - x coordinate of where new tile goes //@param yIndex - y
     * coordinate of where new tile goes //@param tile to be inserted
     *
     */
    public void insertTileTop(int xIndex) {

        //move all tiles down 1 (tile at bottom is replaced - essentially "pushed off edge")
        Tile tempLastTile = new Tile(tiles[(rows - 1)][xIndex]);

        for (int i = rows - 1; i > 0; i--) {
            tiles[i][xIndex] = new Tile(tiles[i - 1][xIndex]);
            tiles[i][xIndex].setPosition(tiles[i][xIndex].upperLeftX, tiles[i][xIndex].upperLeftY + squareSize);
            tiles[i][xIndex].printTileTreasure();
        }

        //insert new tile
        tiles[0][xIndex] = new Tile(extra_tile);
        tiles[0][xIndex].setPosition(tiles[1][xIndex].upperLeftX, 0.0);

        // Move "kicked off" player to new tile if applicable
        ArrayList<Player> currentPlayers = new ArrayList<>(tempLastTile.playersOnTile);
        for (Player p : currentPlayers) {
            tiles[0][xIndex].setPlayer(p);
            tempLastTile.removePlayer(p);
        }
        
        extra_tile = tempLastTile;
        tiles[0][xIndex].printTileTreasure();
    }

    public void insertTileBottom(int xIndex) {

        //move all tiles down 1 (tile at bottom is replaced - essentially "pushed off edge")
        Tile tempLastTile = new Tile(tiles[(0)][xIndex]);

        for (int i = 0; i < rows - 1; i++) {
            tiles[i][xIndex] = new Tile(tiles[i + 1][xIndex]);
            tiles[i][xIndex].setPosition(tiles[i][xIndex].upperLeftX, tiles[i][xIndex].upperLeftY - squareSize);
            tiles[i][xIndex].printTileTreasure();
        }

        //insert new tile
        tiles[rows - 1][xIndex] = new Tile(extra_tile);
        tiles[rows - 1][xIndex].setPosition(tiles[rows - 2][xIndex].upperLeftX, tiles[rows - 2][xIndex].upperLeftY + squareSize);

        // Move "kicked off" player to new tile if applicable
        ArrayList<Player> currentPlayers = new ArrayList<>(tempLastTile.playersOnTile);
        for (Player p : currentPlayers) {
            tiles[rows - 1][xIndex].setPlayer(p);
            tempLastTile.removePlayer(p);
        }

        extra_tile = tempLastTile;
        tiles[rows - 1][xIndex].printTileTreasure();
    }

    public void insertTileLeft(int yIndex) {

        //move all tiles down 1 (tile at bottom is replaced - essentially "pushed off edge")
        Tile tempLastTile = new Tile(tiles[yIndex][rows - 1]);
        for (int i = rows - 1; i > 0; i--) {
            tiles[yIndex][i] = new Tile(tiles[yIndex][i - 1]);
            tiles[yIndex][i].setPosition(tiles[yIndex][i].upperLeftX + squareSize, tiles[yIndex][i].upperLeftY);
            tiles[yIndex][i].printTileTreasure();
            tiles[yIndex][i].printTileTreasure();
        }

        //insert new tile
        tiles[yIndex][0] = new Tile(extra_tile);
        tiles[yIndex][0].setPosition(tiles[yIndex][1].upperLeftX - squareSize, tiles[yIndex][1].upperLeftY);

        // Move "kicked off" player to new tile if applicable
        ArrayList<Player> currentPlayers = new ArrayList<>(tempLastTile.playersOnTile);
        for (Player p : currentPlayers) {
            tiles[yIndex][0].setPlayer(p);
            tempLastTile.removePlayer(p);
        }

        extra_tile = tempLastTile;
        tiles[yIndex][0].printTileTreasure();
        tiles[yIndex][0].printTileTreasure();

    }

    public void insertTileRight(int yIndex) {

        //move all tiles down 1 (tile at bottom is replaced - essentially "pushed off edge")
        Tile tempLastTile = new Tile(tiles[yIndex][0]);

        for (int i = 0; i < rows - 1; i++) {
            tiles[yIndex][i] = new Tile(tiles[yIndex][i + 1]);
            tiles[yIndex][i].setPosition(tiles[yIndex][i].upperLeftX - squareSize, tiles[yIndex][i].upperLeftY);
            tiles[yIndex][i].printTileTreasure();
        }

        //insert new tile
        tiles[yIndex][rows - 1] = new Tile(extra_tile);
        tiles[yIndex][rows - 1].setPosition(tiles[yIndex][rows - 2].upperLeftX + squareSize, tiles[yIndex][rows - 2].upperLeftY);

        // Move "kicked off" player to new tile if applicable
        ArrayList<Player> currentPlayers = new ArrayList<>(tempLastTile.playersOnTile);
        for (Player p : currentPlayers) {
            tiles[yIndex][rows - 1].setPlayer(p);
            tempLastTile.removePlayer(p);
        }

        extra_tile = tempLastTile;
        tiles[yIndex][rows - 1].printTileTreasure();
    }

    public int getX_DIM() {
        return rows;
    }

    public int getY_DIM() {
        return cols;
    }


}
