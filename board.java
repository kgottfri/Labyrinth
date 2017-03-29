import javafx.scene.layout.*;

public class board extends Pane{
	public static final int rows = 7;
	public static final int cols = 7;
	public static final int squareSize=100;
	private int score;
	public LabSquare[][] filledSquares = new LabSquare[cols + 1][rows + 1];
	LabSquare[] boardName;
	
	LabSquare shapeAt(int x, int y){
		return boardName[(y*rows)+x];
	}
	
	public board(){
		this.setPrefHeight(cols*squareSize);
		this.setPrefWidth(rows*squareSize);
	}
	
	public int getX_DIM(){
		return rows;
	}
	public int getY_DIM(){
		return cols;
	}
	public void addSquare(LabSquare square) {
        filledSquares[square.getY()][square.getX()] = square;
    } 
    public boolean checkLocation(int sqY, int sqX) {
        return filledSquares[sqY][sqX] == null;
    }

    

    public void checkRows() {

       
        
        int count=0;
        for (int row = 0; row < filledSquares.length; row++) {
            count = 0;
            
            for (int col = 0; col < filledSquares[row].length; col++) {

                if (filledSquares[row][col] == null) {
                    count++;
                }
            }
            
            if (count == 0) {
                RemoveRows(row,filledSquares);
                score+=1;
                
            }
            
        }
    }
    public void RemoveRows(int row, LabSquare[][] squares){           
        for(int i=0; i<rows; i++){
            squares[row][i].removeFromDrawing();
            squares[row][i] = null;
            
          }
        
        
        for(int j=row; j>0;j--){
            
            for(int k=0; k<squares[j-1].length; k++){
                if (squares[j][k] == null && squares[j-1][k] != null) {
                
            
            squares[j-1][k].moveToLabLocation(k, j);
            squares[j][k]=squares[j-1][k];
            squares[j-1][k]=null;
                }
        }
        }

       
       

    }
    public int getScore(){
    	return score;
    }
}