package chess.pieces;

import chess.ChessPieceImp;

public class Pawn  extends ChessPieceImp {
    public Pawn(int row, int column, int number) {
        this.myNumber = number;
        this.myPosition.setPosition(row, column);
    }
}
