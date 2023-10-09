package chess;

import chess.pieces.*;

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
        int pieceNum = 1;
        for (int i = 0; i < 8; i++) {
            addPiece(new ChessPositionImp(1, i), new Pawn(1, i, pieceNum));
            pieceNum++;
            addPiece(new ChessPositionImp(6, i), new Pawn(6, i,  pieceNum));
            pieceNum++;
        }
        addPiece(new ChessPositionImp(0, 0), new Rook(0, 0, pieceNum));
        pieceNum++;
        addPiece(new ChessPositionImp(0, 7), new Rook(0, 7, pieceNum));
        pieceNum++;
        addPiece(new ChessPositionImp(7, 0), new Rook(7, 0, pieceNum));
        pieceNum++;
        addPiece(new ChessPositionImp(7, 7), new Rook(7, 7, pieceNum));
        pieceNum++;
        
        addPiece(new ChessPositionImp(0, 1), new Bishop(0, 1, pieceNum));
        pieceNum++;
        addPiece(new ChessPositionImp(0, 6), new Bishop(0, 6, pieceNum));
        pieceNum++;
        addPiece(new ChessPositionImp(7, 1), new Bishop(7, 1, pieceNum));
        pieceNum++;
        addPiece(new ChessPositionImp(7, 6), new Bishop(7, 6, pieceNum));
        pieceNum++;
        
        addPiece(new ChessPositionImp(0, 2), new Knight(0, 2, pieceNum));
        pieceNum++;
        addPiece(new ChessPositionImp(0, 5), new Knight(0, 5, pieceNum));
        pieceNum++;
        addPiece(new ChessPositionImp(7, 2), new Knight(7, 2, pieceNum));
        pieceNum++;
        addPiece(new ChessPositionImp(7, 5), new Knight(7, 5, pieceNum));
        pieceNum++;
        
        addPiece(new ChessPositionImp(0, 3), new Queen(0, 3, pieceNum));
        pieceNum++;
        addPiece(new ChessPositionImp(7, 3), new Queen(7, 3, pieceNum));
        pieceNum++;
        addPiece(new ChessPositionImp(0, 4), new King(0, 4, pieceNum));
        pieceNum++;
        addPiece(new ChessPositionImp(7, 4), new King(7, 4, pieceNum));
    }
}
