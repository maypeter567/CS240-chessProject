package chess.pieces;

import chess.ChessPieceImp;

public class King  extends ChessPieceImp {
    public King(int row, int column, int number) {
        this.myNumber = number;
        this.myPosition.setPosition(row, column);
    }
}
