package chess.pieces;

import chess.*;

import java.util.Collection;
import java.util.Vector;

public class Rook  extends ChessPieceImp {
    public Rook(String color) {
        this.myType = PieceType.ROOK;
        if (color == "w") {
            this.myColor = ChessGame.TeamColor.WHITE;
        } else if (color == "b") {
            this.myColor = ChessGame.TeamColor.BLACK;
        }
    }
    
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Vector<ChessMove> myVector = new Vector<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPositionImp mP = new ChessPositionImp(row, col);
        
        var king = protectingKing(board, myPosition);
        if (king == 0) {
            rightHelper(board, mP, myVector, myPosition);
            mP.setPosition(row, col);
            leftHelper(board, mP, myVector, myPosition);
            mP.setPosition(row, col);
            upHelper(board, mP, myVector, myPosition);
            mP.setPosition(row, col);
            downHelper(board, mP, myVector, myPosition);
            mP.setPosition(row, col);
        } else if (king == 1 || king == 5) {
            upHelper(board, mP, myVector, myPosition);
            mP.setPosition(row, col);
            downHelper(board, mP, myVector, myPosition);
            mP.setPosition(row, col);
        } else if (king == 3 || king == 7) {
            rightHelper(board, mP, myVector, myPosition);
            mP.setPosition(row, col);
            leftHelper(board, mP, myVector, myPosition);
            mP.setPosition(row, col);
        }
        return myVector;
    }
    
    void rightHelper(ChessBoard board, ChessPositionImp testPosition, Collection<ChessMove> myVector, ChessPosition myPosition) {
        testPosition.setPosition(testPosition.getRow(), testPosition.getColumn()+1);
        if (isInBounds(testPosition)) {
            ChessPiece piece = board.getPiece(testPosition);
            if (piece != null) {
                if (piece.getTeamColor() != this.myColor) {
                    myVector.add(new ChessMoveImp(myPosition,  new ChessPositionImp (testPosition.getRow(), testPosition.getColumn()), null));
                }
            } else {
                myVector.add(new ChessMoveImp(myPosition,  new ChessPositionImp (testPosition.getRow(), testPosition.getColumn()), null));
                rightHelper(board, testPosition, myVector, myPosition);
            }
        }
    }
    
    void leftHelper(ChessBoard board, ChessPositionImp testPosition, Collection<ChessMove> myVector, ChessPosition myPosition) {
        testPosition.setPosition(testPosition.getRow(), testPosition.getColumn()-1);
        if (isInBounds(testPosition)) {
            ChessPiece piece = board.getPiece(testPosition);
            if (piece != null) {
                if (piece.getTeamColor() != this.myColor) {
                    myVector.add(new ChessMoveImp(myPosition,  new ChessPositionImp (testPosition.getRow(), testPosition.getColumn()), null));
                }
            } else {
                myVector.add(new ChessMoveImp(myPosition,  new ChessPositionImp (testPosition.getRow(), testPosition.getColumn()), null));
                leftHelper(board, testPosition, myVector, myPosition);
            }
        }
    }
    
    void downHelper(ChessBoard board, ChessPositionImp testPosition, Collection<ChessMove> myVector, ChessPosition myPosition) {
        testPosition.setPosition(testPosition.getRow()-1, testPosition.getColumn());
        if (isInBounds(testPosition)) {
            ChessPiece piece = board.getPiece(testPosition);
            if (piece != null) {
                if (piece.getTeamColor() != this.myColor) {
                    myVector.add(new ChessMoveImp(myPosition,  new ChessPositionImp (testPosition.getRow(), testPosition.getColumn()), null));
                }
            } else {
                myVector.add(new ChessMoveImp(myPosition,  new ChessPositionImp (testPosition.getRow(), testPosition.getColumn()), null));
                downHelper(board, testPosition, myVector, myPosition);
            }
        }
    }
    
    void upHelper(ChessBoard board, ChessPositionImp testPosition, Collection<ChessMove> myVector, ChessPosition myPosition) {
        testPosition.setPosition(testPosition.getRow()+1, testPosition.getColumn());
        if (isInBounds(testPosition)) {
            ChessPiece piece = board.getPiece(testPosition);
            if (piece != null) {
                if (piece.getTeamColor() != this.myColor) {
                    myVector.add(new ChessMoveImp(myPosition,  new ChessPositionImp (testPosition.getRow(), testPosition.getColumn()), null));
                }
            } else {
                myVector.add(new ChessMoveImp(myPosition,  new ChessPositionImp (testPosition.getRow(), testPosition.getColumn()), null));
                upHelper(board, testPosition, myVector, myPosition);
            }
        }
    }
    
    boolean isInBounds(ChessPositionImp testPosition) {
        return testPosition.getRow() >= 0 && testPosition.getRow() <= 7 && testPosition.getColumn() >= 0 && testPosition.getColumn() <= 7;
    }
    
    @Override
    public String toString() {
        if (this.myColor == ChessGame.TeamColor.WHITE) {
            return "R";
        } else {
            return "r";
        }
    }
}
