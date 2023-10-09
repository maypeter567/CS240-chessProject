package chess.pieces;

import chess.ChessPieceImp;

public class Rook  extends ChessPieceImp {
    public Rook(int row, int column, int number) {
        this.myNumber = number;
        this.myPosition.setPosition(row, column);
    }
}
