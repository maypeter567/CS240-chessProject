package chess.pieces;

import chess.ChessGame;
import chess.ChessPieceImp;
import chess.ChessPositionImp;

public class Bishop extends ChessPieceImp {
    public Bishop(String color) {
        this.myType = PieceType.BISHOP;
        if (color == "w") {
            this.myColor = ChessGame.TeamColor.WHITE;
        } else if (color == "b") {
            this.myColor = ChessGame.TeamColor.BLACK;
        }
    }
    
}
