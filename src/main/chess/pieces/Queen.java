package chess.pieces;

import chess.ChessPieceImp;

public class Queen  extends ChessPieceImp {
    public Queen(int row, int column, int number) {
        this.myNumber = number;
        this.myPosition.setPosition(row, column);
    }
}
