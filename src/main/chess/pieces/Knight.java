package chess.pieces;

import chess.ChessPieceImp;

public class Knight  extends ChessPieceImp {
    public Knight(int row, int column, int number) {
        this.myNumber = number;
        this.myPosition.setPosition(row, column);
    }
}
