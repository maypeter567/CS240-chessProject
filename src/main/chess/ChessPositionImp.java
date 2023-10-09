package chess;

public class ChessPositionImp implements ChessPosition {
    
    private int row;
    private int column;
    
    public ChessPositionImp(int row, int column) {
        this.row = row;
        this.column = column;
    }
    
    @Override
    public int getRow() {
        return this.row;
    }
    
    @Override
    public int getColumn() {
        return this.column;
    }
    
    public void setPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }
}
