package chess.pieces;

import chess.*;

import java.util.Collection;
import java.util.Objects;
import java.util.Vector;

public class Bishop extends ChessPieceImp {
    public Bishop(String color) {
        this.myType = PieceType.BISHOP;
        if (Objects.equals(color, "w")) {
            this.myColor = ChessGame.TeamColor.WHITE;
        } else if (Objects.equals(color, "b")) {
            this.myColor = ChessGame.TeamColor.BLACK;
        }
    }
    
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Vector<ChessMove> myVector = new Vector<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPositionImp mP = new ChessPositionImp(row, col);
        
        int king = protectingKing(board, myPosition);
        if (king == 0) {
            upRightHelper(board, mP, myVector, myPosition);
            mP.setPosition(row, col);
            upLeftHelper(board, mP, myVector, myPosition);
            mP.setPosition(row, col);
            downLeftHelper(board, mP, myVector, myPosition);
            mP.setPosition(row, col);
            downRightHelper(board, mP, myVector, myPosition);
            mP.setPosition(row, col);
        } else if (king == 2 || king == 6) {
            downRightHelper(board, mP, myVector, myPosition);
            mP.setPosition(row, col);
            upLeftHelper(board, mP, myVector, myPosition);
            mP.setPosition(row, col);
        } else if (king == 4 || king == 8) {
            downLeftHelper(board, mP, myVector, myPosition);
            mP.setPosition(row, col);
            upRightHelper(board, mP, myVector, myPosition);
            mP.setPosition(row, col);
        }
        return myVector;
    }
    
    void upLeftHelper(ChessBoard board, ChessPositionImp testPosition, Collection<ChessMove> myVector, ChessPosition myPosition) {
        testPosition.setPosition(testPosition.getRow()+1, testPosition.getColumn()-1);
        if (isInBounds(testPosition)) {
            ChessPiece piece = board.getPiece(testPosition);
            if (piece != null) {
                if (piece.getTeamColor() != this.myColor) {
                    myVector.add(new ChessMoveImp(myPosition,  new ChessPositionImp (testPosition.getRow(), testPosition.getColumn()), null));
                }
            } else {
                myVector.add(new ChessMoveImp(myPosition,  new ChessPositionImp (testPosition.getRow(), testPosition.getColumn()), null));
                upLeftHelper(board, testPosition, myVector, myPosition);
            }
        }
    }
    
    void upRightHelper(ChessBoard board, ChessPositionImp testPosition, Collection<ChessMove> myVector, ChessPosition myPosition) {
        testPosition.setPosition(testPosition.getRow() + 1, testPosition.getColumn() + 1);
        if (isInBounds(testPosition)) {
            ChessPiece piece = board.getPiece(testPosition);
            if (piece != null) {
                if (piece.getTeamColor() != this.myColor) {
                    myVector.add(new ChessMoveImp(myPosition,  new ChessPositionImp (testPosition.getRow(), testPosition.getColumn()), null));
                }
            } else {
                myVector.add(new ChessMoveImp(myPosition,  new ChessPositionImp (testPosition.getRow(), testPosition.getColumn()), null));
                upRightHelper(board, testPosition, myVector, myPosition);
            }
        }
    }
    
    void downLeftHelper(ChessBoard board, ChessPositionImp testPosition, Collection<ChessMove> myVector, ChessPosition myPosition) {
        testPosition.setPosition(testPosition.getRow()-1, testPosition.getColumn()-1);
        if (isInBounds(testPosition)) {
            ChessPiece piece = board.getPiece(testPosition);
            if (piece != null) {
                if (piece.getTeamColor() != this.myColor) {
                    myVector.add(new ChessMoveImp(myPosition,  new ChessPositionImp (testPosition.getRow(), testPosition.getColumn()), null));
                }
            } else {
                myVector.add(new ChessMoveImp(myPosition,  new ChessPositionImp (testPosition.getRow(), testPosition.getColumn()), null));
                downLeftHelper(board, testPosition, myVector, myPosition);
            }
        }
    }
    
    void downRightHelper(ChessBoard board, ChessPositionImp testPosition, Collection<ChessMove> myVector, ChessPosition myPosition) {
        testPosition.setPosition(testPosition.getRow()-1, testPosition.getColumn()+1);
        if (isInBounds(testPosition)) {
            ChessPiece piece = board.getPiece(testPosition);
            if (piece != null) {
                if (piece.getTeamColor() != this.myColor) {
                    myVector.add(new ChessMoveImp(myPosition,  new ChessPositionImp (testPosition.getRow(), testPosition.getColumn()), null));
                }
            } else {
                myVector.add(new ChessMoveImp(myPosition,  new ChessPositionImp (testPosition.getRow(), testPosition.getColumn()), null));
                downRightHelper(board, testPosition, myVector, myPosition);
            }
        }
    }
    
    boolean isInBounds(ChessPositionImp testPosition) {
        return testPosition.getRow() >= 0 && testPosition.getRow() <= 7 && testPosition.getColumn() >= 0 && testPosition.getColumn() <= 7;
    }
    
    @Override
    public String toString() {
        if (this.myColor == ChessGame.TeamColor.WHITE) {
            return "B";
        } else {
            return "b";
        }
    }
}
