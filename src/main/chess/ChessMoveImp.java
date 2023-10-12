package chess;

public class ChessMoveImp implements ChessMove {
    
    private ChessPosition startPosition, endPosition;
    private ChessPiece.PieceType pieceType;
    
    public ChessMoveImp(ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType piece) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.pieceType = piece;
    }
    @Override
    public ChessPosition getStartPosition() {
        return startPosition;
    }
    
    @Override
    public ChessPosition getEndPosition() {
        return endPosition;
    }
    
    @Override
    public ChessPiece.PieceType getPromotionPiece() {
        return pieceType;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        ChessMoveImp that = (ChessMoveImp) o;
        
        if (!startPosition.equals(that.startPosition)) return false;
        if (!endPosition.equals(that.endPosition)) return false;
        return pieceType == that.pieceType;
    }
    
    @Override
    public int hashCode() {
        int result = startPosition.hashCode();
        result = 31 * result + endPosition.hashCode();
        result = 31 * result + (pieceType != null ? pieceType.hashCode() : 0);
        return result;
    }
}
