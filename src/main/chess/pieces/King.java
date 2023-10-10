package chess.pieces;

import chess.ChessGame;
import chess.ChessPieceImp;
import chess.ChessPositionImp;

public class King  extends ChessPieceImp {
    public King(String color) {
        this.myType = PieceType.KING;
        if (color == "w") {
            this.myColor = ChessGame.TeamColor.WHITE;
        } else if (color == "b") {
            this.myColor = ChessGame.TeamColor.BLACK;
        }
    }
}
