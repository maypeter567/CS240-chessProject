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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        ChessPositionImp that = (ChessPositionImp) o;
        
        if (row != that.row) return false;
        return column == that.column;
    }
    
    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        return result;
    }
}
