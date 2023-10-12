package chess.pieces;

import chess.*;

import java.util.Collection;
import java.util.Vector;

public class Pawn  extends ChessPieceImp {
    
    private ChessGameImp testGame = new ChessGameImp();
    
    private boolean hasMoved = false;
    
    public Pawn(String color) {
        this.myType = PieceType.PAWN;
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
        if (this.myColor == ChessGame.TeamColor.BLACK) {
            var king = protectingKing(board, myPosition);
            if (king == 0) {
                downHelper(board, mP, myVector, myPosition);
                mP.setPosition(row, col);
                downLeftHelper(board, mP, myVector, myPosition);
                mP.setPosition(row, col);
                downRightHelper(board, mP, myVector, myPosition);
                mP.setPosition(row, col);
            } else if (king == 1 || king == 5) {
                downHelper(board, mP, myVector, myPosition);
                mP.setPosition(row, col);
            } else if (king == 2 || king == 6) {
                downRightHelper(board, mP, myVector, myPosition);
                mP.setPosition(row, col);
            } else if (king == 4 || king == 8) {
                downLeftHelper(board, mP, myVector, myPosition);
                mP.setPosition(row, col);
            }
        } else {
            var king = protectingKing(board, myPosition);
            if (king == 0) {
                upHelper(board, mP, myVector, myPosition);
                mP.setPosition(row, col);
                upRightHelper(board, mP, myVector, myPosition);
                mP.setPosition(row, col);
                upLeftHelper(board, mP, myVector, myPosition);
                mP.setPosition(row, col);
            } else if (king == 1 || king == 5) {
                upHelper(board, mP, myVector, myPosition);
                mP.setPosition(row, col);
            } else if (king == 2 || king == 6) {
                upLeftHelper(board, mP, myVector, myPosition);
                mP.setPosition(row, col);
            } else if (king == 4 || king == 8) {
                upRightHelper(board, mP, myVector, myPosition);
                mP.setPosition(row, col);
            }
        }
        
        return myVector;
    }
    
    void upHelper(ChessBoard board, ChessPositionImp testPosition, Collection<ChessMove> myVector, ChessPosition myPosition) {
        testPosition.setPosition(testPosition.getRow()+1, testPosition.getColumn());
        if (isInBounds(testPosition)) {
            ChessPiece piece = board.getPiece(testPosition);
            if (piece == null) {
                if (testPosition.getRow() == 7) {
                    upPromotion(board, testPosition, myVector, myPosition);
                } else {
                    myVector.add(new ChessMoveImp(myPosition, new ChessPositionImp(testPosition.getRow(), testPosition.getColumn()), null));
                }
                if (testPosition.getRow() == 2) {
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
                if (testPosition.getRow() == 0) {
                    downPromotion(board, testPosition, myVector, myPosition);
                } else {
                    myVector.add(new ChessMoveImp(myPosition, new ChessPositionImp(testPosition.getRow(), testPosition.getColumn()), null));
                }
                if (testPosition.getRow() == 5) {
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
                    if (testPosition.getRow() == 7) {
                        upPromotion(board, testPosition, myVector, myPosition);
                    } else {
                        myVector.add(new ChessMoveImp(myPosition, new ChessPositionImp(testPosition.getRow(), testPosition.getColumn()), null));
                    }
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
                    if (testPosition.getRow() == 7) {
                        upPromotion(board, testPosition, myVector, myPosition);
                    } else {
                        myVector.add(new ChessMoveImp(myPosition, new ChessPositionImp(testPosition.getRow(), testPosition.getColumn()), null));
                    }
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
                    if (testPosition.getRow() == 0) {
                        downPromotion(board, testPosition, myVector, myPosition);
                    } else {
                        myVector.add(new ChessMoveImp(myPosition, new ChessPositionImp(testPosition.getRow(), testPosition.getColumn()), null));
                    }
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
                    if (testPosition.getRow() == 0) {
                        downPromotion(board, testPosition, myVector, myPosition);
                    } else {
                        myVector.add(new ChessMoveImp(myPosition, new ChessPositionImp(testPosition.getRow(), testPosition.getColumn()), null));
                    }
                }
            }
        }
    }
    
    void upPromotion(ChessBoard board, ChessPositionImp testPosition, Collection<ChessMove> myVector, ChessPosition myPosition) {
        myVector.add(new ChessMoveImp(myPosition, new ChessPositionImp(testPosition.getRow(), testPosition.getColumn()), PieceType.ROOK));
        myVector.add(new ChessMoveImp(myPosition, new ChessPositionImp(testPosition.getRow(), testPosition.getColumn()), PieceType.KNIGHT));
        myVector.add(new ChessMoveImp(myPosition, new ChessPositionImp(testPosition.getRow(), testPosition.getColumn()), PieceType.QUEEN));
        myVector.add(new ChessMoveImp(myPosition, new ChessPositionImp(testPosition.getRow(), testPosition.getColumn()), PieceType.BISHOP));
    }
    
    void downPromotion(ChessBoard board, ChessPositionImp testPosition, Collection<ChessMove> myVector, ChessPosition myPosition) {
        myVector.add(new ChessMoveImp(myPosition, new ChessPositionImp(testPosition.getRow(), testPosition.getColumn()), PieceType.BISHOP));
        myVector.add(new ChessMoveImp(myPosition, new ChessPositionImp(testPosition.getRow(), testPosition.getColumn()), PieceType.QUEEN));
        myVector.add(new ChessMoveImp(myPosition, new ChessPositionImp(testPosition.getRow(), testPosition.getColumn()), PieceType.ROOK));
        myVector.add(new ChessMoveImp(myPosition, new ChessPositionImp(testPosition.getRow(), testPosition.getColumn()), PieceType.KNIGHT));
    }
    
    boolean isInBounds(ChessPositionImp testPosition) {
        return testPosition.getRow() >= 0 && testPosition.getRow() <= 7 && testPosition.getColumn() >= 0 && testPosition.getColumn() <= 7;
    }
    
    @Override
    public String toString() {
        if (this.myColor == ChessGame.TeamColor.WHITE) {
            return "P";
        } else {
            return "p";
        }
    }
}
