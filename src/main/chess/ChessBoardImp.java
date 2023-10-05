package chess;

import java.util.Vector;

public class ChessBoardImp implements ChessBoard {
    private final Vector<ChessPiece> boardSpaces = new Vector<>();
    
    public ChessBoardImp() {
        boardSpaces.setSize(64);
    }
    
    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        if (boardSpaces.get(position.getRow() * 8 + position.getColumn()) == null) {
            boardSpaces.set(position.getRow() * 8 + position.getColumn(), piece);
        } else {
            try {
                throw new Exception("You cannot add a piece where a piece is already standing");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return boardSpaces.get(position.getRow() * 8 + position.getColumn());
    }
    
    @Override
    public void resetBoard() {
        boardSpaces.clear();
        setUpBoard();
    }
    
    private void setUpBoard() {
        // finish setting up board state
    }
}
