package chess.pieces;

import chess.ChessGame;
import chess.ChessPieceImp;
import chess.ChessPositionImp;

public class Rook  extends ChessPieceImp {
    public Rook(String color) {
        this.myType = PieceType.ROOK;
        if (color == "w") {
            this.myColor = ChessGame.TeamColor.WHITE;
        } else if (color == "b") {
            this.myColor = ChessGame.TeamColor.BLACK;
        }
    }
}
