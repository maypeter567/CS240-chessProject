package chess.pieces;

import chess.ChessPieceImp;

public class Bishop extends ChessPieceImp {
    public Bishop(int row, int column, int number) {
        this.myNumber = number;
        this.myPosition.setPosition(row, column);
    }
    
}
