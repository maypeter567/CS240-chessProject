package chess;

import chess.pieces.*;

import java.util.Vector;

public class ChessBoardImp implements ChessBoard {
    private final Vector<ChessPiece> boardSpaces = new Vector<>();
    
    public ChessBoardImp() {
        for (int i = 0; i < 64; i++) {
            boardSpaces.add(null);
        }
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
        for (int i = 0; i < 64; i++) {
            boardSpaces.add(null);
        }
        setUpBoard();
    }
    
    private void setUpBoard() {
        ChessPositionImp chessPosition = new ChessPositionImp(0, 0);
        for (int i = 0; i < 8; i++) {
            chessPosition.setPosition(1, i);
            addPiece(chessPosition, new Pawn("w"));
            chessPosition.setPosition(6, i);
            addPiece(chessPosition, new Pawn("b"));
        }
        chessPosition.setPosition(0, 0);
        addPiece(chessPosition, new Rook("w"));
        chessPosition.setPosition(0, 7);
        addPiece(chessPosition, new Rook("w"));
        chessPosition.setPosition(7, 0);
        addPiece(chessPosition, new Rook("b"));
        chessPosition.setPosition(7, 7);
        addPiece(chessPosition, new Rook("b"));
        
        chessPosition.setPosition(0, 2);
        addPiece(chessPosition, new Bishop("w"));
        chessPosition.setPosition(0, 5);
        addPiece(chessPosition, new Bishop("w"));
        chessPosition.setPosition(7, 2);
        addPiece(chessPosition, new Bishop("b"));
        chessPosition.setPosition(7, 5);
        addPiece(chessPosition, new Bishop("b"));
        
        chessPosition.setPosition(0, 1);
        addPiece(chessPosition, new Knight("w"));
        chessPosition.setPosition(0, 6);
        addPiece(chessPosition, new Knight("w"));
        chessPosition.setPosition(7, 1);
        addPiece(chessPosition, new Knight("b"));
        chessPosition.setPosition(7, 6);
        addPiece(chessPosition, new Knight("b"));
        
        chessPosition.setPosition(0, 3);
        addPiece(chessPosition, new Queen("w"));
        chessPosition.setPosition(7, 3);
        addPiece(chessPosition, new Queen("b"));
        chessPosition.setPosition(0, 4);
        addPiece(chessPosition, new King("w"));
        chessPosition.setPosition(7, 4);
        addPiece(chessPosition, new King("b"));
    }
}
