package chess;

import chess.pieces.Knight;
import chess.pieces.Pawn;

import java.util.Collection;

public class ChessPieceImp implements ChessPiece {
    
    protected PieceType myType;
    protected ChessGame.TeamColor myColor;
    
    public ChessPieceImp() {
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
