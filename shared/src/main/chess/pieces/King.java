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
        
        if (!myVector.isEmpty()) {
            myVector = moveDoesNotEndInCheck(board, myVector, myPosition);
        }
        
        return myVector;
    }
    
    public Collection<ChessMove> uniqueKingMoves(ChessBoard board, ChessPosition myPosition) {
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
    
    @Override
    public Vector<ChessMove> moveDoesNotEndInCheck(ChessBoard myBoard, Collection<ChessMove> kingMoves, ChessPosition myPosition) {
        Vector<ChessMove> vector = new Vector<>();
        Vector<ChessMove> returnVector = new Vector<>();
        ChessPositionImp position = new ChessPositionImp(0, 0);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                position.setPosition(i, j);
                if (myBoard.getPiece(position) != null && notMe(position, myPosition) && myBoard.getPiece(position).getPieceType() != PieceType.KING
                        && myBoard.getPiece(position).getTeamColor() != myBoard.getPiece(myPosition).getTeamColor()) {
                    var tempVector = myBoard.getPiece(new ChessPositionImp(position.getRow(),
                            position.getColumn())).pieceMoves(myBoard, new ChessPositionImp(position.getRow(), position.getColumn()));
                    vector.addAll(tempVector);
                } else if (myBoard.getPiece(position) != null && notMe(position, myPosition) && myBoard.getPiece(position).getPieceType() == PieceType.KING
                        && myBoard.getPiece(position).getTeamColor() != myBoard.getPiece(myPosition).getTeamColor()) {
                    var tempVector = myBoard.getPiece(new ChessPositionImp(position.getRow(),
                            position.getColumn())).uniqueKingMoves(myBoard, new ChessPositionImp(position.getRow(), position.getColumn()));
                    vector.addAll(tempVector);
                }
            }
        }
        ChessMove canMove = null;
        if (vector.isEmpty()) {
            return (Vector<ChessMove>) kingMoves;
        }
        for (var kingSpot : kingMoves) {
            canMove = kingSpot;
            for (var spot : vector) {
                if (spot.getEndPosition().getRow() == kingSpot.getEndPosition().getRow() &&
                        spot.getEndPosition().getColumn() == kingSpot.getEndPosition().getColumn()) {
                    var test = myBoard.getPiece(spot.getStartPosition());
                    if (test != null
                            && myBoard.getPiece(spot.getStartPosition()).getPieceType() == PieceType.PAWN
                            && spot.getEndPosition().getColumn() == spot.getStartPosition().getColumn()) {
                        canMove = canMove;
                    } else {
                        canMove = null;
                    }
                }
            }
            if (canMove != null) {
                returnVector.add(canMove);
            }
        }
        return returnVector;
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
                myVector.add(new ChessMoveImp(myPosition,  new ChessPositionImp (testPosition.getRow(), testPosition.getColumn()), null));
            }
        } else {
            myVector.add(new ChessMoveImp(myPosition,  new ChessPositionImp (testPosition.getRow(), testPosition.getColumn()), null));
        }
    }
    
    boolean isInBounds(ChessPositionImp testPosition) {
        return testPosition.getRow() >= 0 && testPosition.getRow() <= 7 && testPosition.getColumn() >= 0 && testPosition.getColumn() <= 7;
    }
    
    boolean notMe(ChessPosition position1, ChessPosition position2) {
        return position1.getColumn() != position2.getColumn() || position1.getRow() != position2.getRow();
    }
    
    @Override
    public String toString() {
        if (this.myColor == ChessGame.TeamColor.WHITE) {
            return "K";
        } else {
            return "k";
        }
    }
}
