package chess;

import java.util.Collection;

public class ChessPieceImp implements ChessPiece {
    
    protected PieceType myType;
    protected ChessGame.TeamColor myColor;
    
    public ChessPieceImp() {
    }
    
    public ChessPieceImp(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.myType = type;
        this.myColor = pieceColor;
    }
    
    @Override
    public ChessGame.TeamColor getTeamColor() {
        return this.myColor;
    }
    
    @Override
    public PieceType getPieceType() {
        return this.myType;
    }
    
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }
}
