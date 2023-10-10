package chess.pieces;

import chess.*;

import java.util.Collection;
import java.util.Vector;

public class King  extends ChessPieceImp {
    public King(String color) {
        this.myType = PieceType.KING;
        if (color == "w") {
            this.myColor = ChessGame.TeamColor.WHITE;
        } else if (color == "b") {
            this.myColor = ChessGame.TeamColor.BLACK;
        }
    }
    
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Vector<ChessMove> myVector = new Vector<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPositionImp mP = new ChessPositionImp(row, col);
        upRightHelper(board, mP, myVector, myPosition);
        mP.setPosition(row, col);
        upHelper(board, mP, myVector, myPosition);
        mP.setPosition(row, col);
        upLeftHelper(board, mP, myVector, myPosition);
        mP.setPosition(row, col);
        leftHelper(board, mP, myVector, myPosition);
        mP.setPosition(row, col);
        downLeftHelper(board, mP, myVector, myPosition);
        mP.setPosition(row, col);
        downHelper(board, mP, myVector, myPosition);
        mP.setPosition(row, col);
        downRightHelper(board, mP, myVector, myPosition);
        mP.setPosition(row, col);
        rightHelper(board, mP, myVector, myPosition);
        mP.setPosition(row, col);
        return myVector;
    }
    
    void upLeftHelper(ChessBoard board, ChessPositionImp testPosition, Collection<ChessMove> myVector, ChessPosition myPosition) {
        testPosition.setPosition(testPosition.getRow() + 1, testPosition.getColumn() - 1);
        if (isInBounds(testPosition)) {
            helperHelper(board, testPosition, myVector, myPosition);
        }
    }
    
    void upRightHelper(ChessBoard board, ChessPositionImp testPosition, Collection<ChessMove> myVector, ChessPosition myPosition) {
        testPosition.setPosition(testPosition.getRow() + 1, testPosition.getColumn() + 1);
        if (isInBounds(testPosition)) {
            helperHelper(board, testPosition, myVector, myPosition);
        }
    }
    
    void downLeftHelper(ChessBoard board, ChessPositionImp testPosition, Collection<ChessMove> myVector, ChessPosition myPosition) {
        testPosition.setPosition(testPosition.getRow() - 1, testPosition.getColumn() - 1);
        if (isInBounds(testPosition)) {
            helperHelper(board, testPosition, myVector, myPosition);
        }
    }
    
    void downRightHelper(ChessBoard board, ChessPositionImp testPosition, Collection<ChessMove> myVector, ChessPosition myPosition) {
        testPosition.setPosition(testPosition.getRow() - 1, testPosition.getColumn() + 1);
        if (isInBounds(testPosition)) {
            helperHelper(board, testPosition, myVector, myPosition);
        }
    }
    
    void downHelper(ChessBoard board, ChessPositionImp testPosition, Collection<ChessMove> myVector, ChessPosition myPosition) {
        testPosition.setPosition(testPosition.getRow() - 1, testPosition.getColumn());
        if (isInBounds(testPosition)) {
            helperHelper(board, testPosition, myVector, myPosition);
        }
    }
    
    void leftHelper(ChessBoard board, ChessPositionImp testPosition, Collection<ChessMove> myVector, ChessPosition myPosition) {
        testPosition.setPosition(testPosition.getRow(), testPosition.getColumn() - 1);
        if (isInBounds(testPosition)) {
            helperHelper(board, testPosition, myVector, myPosition);
        }
    }
    
    void rightHelper(ChessBoard board, ChessPositionImp testPosition, Collection<ChessMove> myVector, ChessPosition myPosition) {
        testPosition.setPosition(testPosition.getRow(), testPosition.getColumn() + 1);
        if (isInBounds(testPosition)) {
            helperHelper(board, testPosition, myVector, myPosition);
        }
    }
    
    void upHelper(ChessBoard board, ChessPositionImp testPosition, Collection<ChessMove> myVector, ChessPosition myPosition) {
        testPosition.setPosition(testPosition.getRow() + 1, testPosition.getColumn());
        if (isInBounds(testPosition)) {
            helperHelper(board, testPosition, myVector, myPosition);
        }
    }
    
    private void helperHelper(ChessBoard board, ChessPositionImp testPosition, Collection<ChessMove> myVector, ChessPosition myPosition) {
        ChessPiece piece = board.getPiece(testPosition);
        if (piece != null) {
            if (piece.getTeamColor() != this.myColor) {
                myVector.add(new ChessMoveImp(myPosition, testPosition, piece.getPieceType()));
            }
        } else {
            myVector.add(new ChessMoveImp(myPosition, testPosition, null));
        }
    }
    
    boolean isInBounds(ChessPositionImp testPosition) {
        return testPosition.getRow() >= 0 && testPosition.getRow() <= 7 && testPosition.getColumn() >= 0 && testPosition.getColumn() <= 7;
    }
}
