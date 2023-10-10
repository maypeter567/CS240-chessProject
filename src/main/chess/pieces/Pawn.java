package chess.pieces;

import chess.ChessGame;
import chess.ChessPieceImp;
import chess.ChessPositionImp;

public class Pawn  extends ChessPieceImp {
    public Pawn(String color) {
        this.myType = PieceType.PAWN;
        if (color == "w") {
            this.myColor = ChessGame.TeamColor.WHITE;
        } else if (color == "b") {
            this.myColor = ChessGame.TeamColor.BLACK;
        }
    }
}
