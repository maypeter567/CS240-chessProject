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
}
