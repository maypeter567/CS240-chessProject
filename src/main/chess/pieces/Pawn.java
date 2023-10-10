package chess.pieces;

import chess.*;

import java.util.Collection;
import java.util.Vector;

public class Pawn  extends ChessPieceImp {
    
    private boolean hasMoved;
    
    public Pawn(String color) {
        this.myType = PieceType.PAWN;
        if (color == "w") {
            this.myColor = ChessGame.TeamColor.WHITE;
        } else if (color == "b") {
            this.myColor = ChessGame.TeamColor.BLACK;
        }
        this.hasMoved = false;
    }
    
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Vector<ChessMove> myVector = new Vector<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPositionImp mP = new ChessPositionImp(row, col);
        if (this.myColor == ChessGame.TeamColor.WHITE) {
            downHelper(board, mP, myVector, myPosition);
            mP.setPosition(row, col);
            downLeftHelper(board, mP, myVector, myPosition);
            mP.setPosition(row, col);
            downRightHelper(board, mP, myVector, myPosition);
            mP.setPosition(row, col);
        } else {
            upHelper(board, mP, myVector, myPosition);
            mP.setPosition(row, col);
            upRightHelper(board, mP, myVector, myPosition);
            mP.setPosition(row, col);
            upLeftHelper(board, mP, myVector, myPosition);
            mP.setPosition(row, col);
        }
        return myVector;
    }
    
    void upHelper(ChessBoard board, ChessPositionImp testPosition, Collection<ChessMove> myVector, ChessPosition myPosition) {
        testPosition.setPosition(testPosition.getRow()+1, testPosition.getColumn());
        if (isInBounds(testPosition)) {
            ChessPiece piece = board.getPiece(testPosition);
            if (piece == null) {
                myVector.add(new ChessMoveImp(myPosition, testPosition, null));
                if (!this.hasMoved) {
                    this.hasMoved = true;
                    upHelper(board, testPosition, myVector, myPosition);
                }
            }
        }
    }
    
    void downHelper(ChessBoard board, ChessPositionImp testPosition, Collection<ChessMove> myVector, ChessPosition myPosition) {
        testPosition.setPosition(testPosition.getRow()-1, testPosition.getColumn());
        if (isInBounds(testPosition)) {
            ChessPiece piece = board.getPiece(testPosition);
            if (piece == null) {
                myVector.add(new ChessMoveImp(myPosition, testPosition, null));
                if (!this.hasMoved) {
                    this.hasMoved = true;
                    downHelper(board, testPosition, myVector, myPosition);
                }
            }
        }
    }
    
    void upLeftHelper(ChessBoard board, ChessPositionImp testPosition, Collection<ChessMove> myVector, ChessPosition myPosition) {
        testPosition.setPosition(testPosition.getRow()+1, testPosition.getColumn()-1);
        if (isInBounds(testPosition)) {
            ChessPiece piece = board.getPiece(testPosition);
            if (piece != null) {
                if (piece.getTeamColor() != this.myColor) {
                    myVector.add(new ChessMoveImp(myPosition, testPosition, piece.getPieceType()));
                }
            }
        }
    }
    
    void upRightHelper(ChessBoard board, ChessPositionImp testPosition, Collection<ChessMove> myVector, ChessPosition myPosition) {
        testPosition.setPosition(testPosition.getRow()+1, testPosition.getColumn()+1);
        if (isInBounds(testPosition)) {
            ChessPiece piece = board.getPiece(testPosition);
            if (piece != null) {
                if (piece.getTeamColor() != this.myColor) {
                    myVector.add(new ChessMoveImp(myPosition, testPosition, piece.getPieceType()));
                }
            }
        }
    }
    
    void downLeftHelper(ChessBoard board, ChessPositionImp testPosition, Collection<ChessMove> myVector, ChessPosition myPosition) {
        testPosition.setPosition(testPosition.getRow()-1, testPosition.getColumn()-1);
        if (isInBounds(testPosition)) {
            ChessPiece piece = board.getPiece(testPosition);
            if (piece != null) {
                if (piece.getTeamColor() != this.myColor) {
                    myVector.add(new ChessMoveImp(myPosition, testPosition, piece.getPieceType()));
                }
            }
        }
    }
    
    void downRightHelper(ChessBoard board, ChessPositionImp testPosition, Collection<ChessMove> myVector, ChessPosition myPosition) {
        testPosition.setPosition(testPosition.getRow()-1, testPosition.getColumn()+1);
        if (isInBounds(testPosition)) {
            ChessPiece piece = board.getPiece(testPosition);
            if (piece != null) {
                if (piece.getTeamColor() != this.myColor) {
                    myVector.add(new ChessMoveImp(myPosition, testPosition, piece.getPieceType()));
                }
            }
        }
    }
    
    boolean isInBounds(ChessPositionImp testPosition) {
        return testPosition.getRow() >= 0 && testPosition.getRow() <= 7 && testPosition.getColumn() >= 0 && testPosition.getColumn() <= 7;
    }
}
