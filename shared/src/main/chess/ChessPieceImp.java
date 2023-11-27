package chess;

import java.util.Collection;
import java.util.Vector;

public class ChessPieceImp implements ChessPiece {
    
    protected PieceType myType;
    protected ChessGame.TeamColor myColor;
    
    public ChessPieceImp() {
    }
    
    @Override
    public ChessGame.TeamColor getTeamColor() {
        return this.myColor;
    }
    
    @Override
    public PieceType getPieceType() {
        return this.myType;
    }
    
    @Override
    public Collection<ChessMove> uniqueKingMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }
    
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        return null;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        ChessPieceImp that = (ChessPieceImp) o;
        
        if (myType != that.myType) return false;
        return myColor == that.myColor;
    }
    
    public Vector<ChessMove> moveDoesNotEndInCheck(ChessBoard myBoard, Collection<ChessMove> kingMoves,
                                                   ChessPosition myPosition) { return null; }
    
    protected int protectingKing(ChessBoard board, ChessPosition position) {
        if (pKUpHelper(board, position, position)) {
            if (dangerDownHelper(board, position, position)) {
                return 1;
            }
        }
        if (pKUpLeftHelper(board, position, position)) {
            if (dangerDownRightHelper(board, position, position)) {
                return 2;
            }
        }
        if (pKLeftHelper(board, position, position)) {
            if (dangerRightHelper(board, position, position)) {
                return 3;
            }
        }
        if (pKDownLeftHelper(board, position, position)) {
            if (dangerUpRighttHelper(board, position, position)) {
                return 4;
            }
        }
        if (pKDownHelper(board, position, position)) {
            if (dangerUpHelper(board, position, position)) {
                return 5;
            }
        }
        if (pKDownRightHelper(board, position, position)) {
            if (dangerUpLeftHelper(board, position, position)) {
                return 6;
            }
        }
        if (pKRightHelper(board, position, position)) {
            if (dangerLeftHelper(board, position, position)) {
                return 7;
            }
        }
        if (pKUpRightHelper(board, position, position)) {
            if (dangerDownLeftHelper(board, position, position)) {
                return 8;
            }
        }
        return 0;
    }
    
    protected boolean dangerPieceDiagonal(ChessPiece piece) {
        return piece.getPieceType() == PieceType.QUEEN || piece.getPieceType() == PieceType.BISHOP;
    }
    
    protected boolean dangerPieceVirtical(ChessPiece piece) {
        return piece.getPieceType() == PieceType.QUEEN || piece.getPieceType() == PieceType.ROOK;
    }
    
    protected boolean dangerUpHelper(ChessBoard board, ChessPosition position, ChessPosition test) {
        var test1 = new ChessPositionImp(test.getRow() + 1, test.getColumn());
        if (isInBounds(test1)) {
            return false;
        }
        var thisColor = board.getPiece(position).getTeamColor(); // I AM WORKING HERE, I AM GETTING THE FUNCTION TO RECOGNIZE THE KING IS IN DANGER IF THIS PIECE MOVES.
        var checkPiece = board.getPiece(test1);
        if (checkPiece != null && dangerPieceVirtical(checkPiece) && checkPiece.getTeamColor() != thisColor) {
            return true;
        } else if (checkPiece == null) {
            return dangerUpHelper(board, position, test1);
        }
        return false;
    }
    
    protected boolean dangerDownHelper(ChessBoard board, ChessPosition position, ChessPosition test) {
        var test1 = new ChessPositionImp(test.getRow() - 1, test.getColumn());
        if (isInBounds(test1)) {
            return false;
        }
        var thisColor = board.getPiece(position).getTeamColor(); // I AM WORKING HERE, I AM GETTING THE FUNCTION TO RECOGNIZE THE KING IS IN DANGER IF THIS PIECE MOVES.
        var checkPiece = board.getPiece(test1);
        if (checkPiece != null && dangerPieceVirtical(checkPiece) && checkPiece.getTeamColor() != thisColor) {
            return true;
        } else if (checkPiece == null) {
            return dangerDownHelper(board, position, test1);
        }
        return false;
    }
    
    protected boolean dangerLeftHelper(ChessBoard board, ChessPosition position, ChessPosition test) {
        var test1 = new ChessPositionImp(test.getRow(), test.getColumn()-1);
        if (isInBounds(test1)) {
            return false;
        }
        var thisColor = board.getPiece(position).getTeamColor(); // I AM WORKING HERE, I AM GETTING THE FUNCTION TO RECOGNIZE THE KING IS IN DANGER IF THIS PIECE MOVES.
        var checkPiece = board.getPiece(test1);
        if (checkPiece != null && dangerPieceVirtical(checkPiece) && checkPiece.getTeamColor() != thisColor) {
            return true;
        } else if (checkPiece == null) {
            return dangerLeftHelper(board, position, test1);
        }
        return false;
    }
    
    protected boolean dangerRightHelper(ChessBoard board, ChessPosition position, ChessPosition test) {
        var test1 = new ChessPositionImp(test.getRow(), test.getColumn()+1);
        if (isInBounds(test1)) {
            return false;
        }
        var thisColor = board.getPiece(position).getTeamColor(); // I AM WORKING HERE, I AM GETTING THE FUNCTION TO RECOGNIZE THE KING IS IN DANGER IF THIS PIECE MOVES.
        var checkPiece = board.getPiece(test1);
        if (checkPiece != null && dangerPieceVirtical(checkPiece) && checkPiece.getTeamColor() != thisColor) {
            return true;
        } else if (checkPiece == null) {
            return dangerRightHelper(board, position, test1);
        }
        return false;
    }
    
    protected boolean dangerUpLeftHelper(ChessBoard board, ChessPosition position, ChessPosition test) {
        var test1 = new ChessPositionImp(test.getRow() + 1, test.getColumn()-1);
        if (isInBounds(test1)) {
            return false;
        }
        var thisColor = board.getPiece(position).getTeamColor(); // I AM WORKING HERE, I AM GETTING THE FUNCTION TO RECOGNIZE THE KING IS IN DANGER IF THIS PIECE MOVES.
        var checkPiece = board.getPiece(test1);
        if (checkPiece != null && dangerPieceDiagonal(checkPiece) && checkPiece.getTeamColor() != thisColor) {
            return true;
        } else if (checkPiece == null) {
            return dangerUpLeftHelper(board, position, test1);
        }
        return false;
    }
    
    protected boolean dangerUpRighttHelper(ChessBoard board, ChessPosition position, ChessPosition test) {
        var test1 = new ChessPositionImp(test.getRow() + 1, test.getColumn()+1);
        if (isInBounds(test1)) {
            return false;
        }
        var thisColor = board.getPiece(position).getTeamColor(); // I AM WORKING HERE, I AM GETTING THE FUNCTION TO RECOGNIZE THE KING IS IN DANGER IF THIS PIECE MOVES.
        var checkPiece = board.getPiece(test1);
        if (checkPiece != null && dangerPieceDiagonal(checkPiece) && checkPiece.getTeamColor() != thisColor) {
            return true;
        } else if (checkPiece == null) {
            return dangerUpRighttHelper(board, position, test1);
        }
        return false;
    }
    
    protected boolean dangerDownLeftHelper(ChessBoard board, ChessPosition position, ChessPosition test) {
        var test1 = new ChessPositionImp(test.getRow() - 1, test.getColumn()-1);
        if (isInBounds(test1)) {
            return false;
        }
        var thisColor = board.getPiece(position).getTeamColor(); // I AM WORKING HERE, I AM GETTING THE FUNCTION TO RECOGNIZE THE KING IS IN DANGER IF THIS PIECE MOVES.
        var checkPiece = board.getPiece(test1);
        if (checkPiece != null && dangerPieceDiagonal(checkPiece) && checkPiece.getTeamColor() != thisColor) {
            return true;
        } else if (checkPiece == null) {
            return dangerDownLeftHelper(board, position, test1);
        }
        return false;
    }
    
    protected boolean dangerDownRightHelper(ChessBoard board, ChessPosition position, ChessPosition test) {
        var test1 = new ChessPositionImp(test.getRow() - 1, test.getColumn()+1);
        if (isInBounds(test1)) {
            return false;
        }
        var thisColor = board.getPiece(position).getTeamColor(); // I AM WORKING HERE, I AM GETTING THE FUNCTION TO RECOGNIZE THE KING IS IN DANGER IF THIS PIECE MOVES.
        var checkPiece = board.getPiece(test1);
        if (checkPiece != null && dangerPieceDiagonal(checkPiece) && checkPiece.getTeamColor() != thisColor) {
            return true;
        } else if (checkPiece == null) {
            return dangerDownRightHelper(board, position, test1);
        }
        return false;
    }
    
    protected boolean pKUpHelper(ChessBoard board, ChessPosition position, ChessPosition test) {
        var test1 = new ChessPositionImp(test.getRow() + 1, test.getColumn());
        if (isInBounds(test1)) {
            return false;
        }
        var thisColor = board.getPiece(position).getTeamColor();
        var checkPiece = board.getPiece(test1);
        if (checkPiece != null && checkPiece.getPieceType() == PieceType.KING && checkPiece.getTeamColor() == thisColor) {
            return true;
        } else if (checkPiece == null) {
            return pKUpHelper(board, position, test1);
        }
        return false;
    }
    
    protected boolean pKUpLeftHelper(ChessBoard board, ChessPosition position, ChessPosition test) {
        var test1 = new ChessPositionImp(test.getRow() + 1, test.getColumn()-1);
        if (isInBounds(test1)) {
            return false;
        }
        var thisColor = board.getPiece(position).getTeamColor();
        var checkPiece = board.getPiece(test1);
        if (checkPiece != null && checkPiece.getPieceType() == PieceType.KING && checkPiece.getTeamColor() == thisColor) {
            return true;
        } else if (checkPiece == null) {
            return pKUpLeftHelper(board, position, test1);
        }
        return false;
    }
    
    protected boolean pKLeftHelper(ChessBoard board, ChessPosition position, ChessPosition test) {
        var test1 = new ChessPositionImp(test.getRow(), test.getColumn()-1);
        if (isInBounds(test1)) {
            return false;
        }
        var thisColor = board.getPiece(position).getTeamColor();
        var checkPiece = board.getPiece(test1);
        if (checkPiece != null && checkPiece.getPieceType() == PieceType.KING && checkPiece.getTeamColor() == thisColor) {
            return true;
        } else if (checkPiece == null) {
            return pKLeftHelper(board, position, test1);
        }
        return false;
    }
    
    protected boolean pKDownLeftHelper(ChessBoard board, ChessPosition position, ChessPosition test) {
        var test1 = new ChessPositionImp(test.getRow() - 1, test.getColumn()-1);
        if (isInBounds(test1)) {
            return false;
        }
        var thisColor = board.getPiece(position).getTeamColor();
        var checkPiece = board.getPiece(test1);
        if (checkPiece != null && checkPiece.getPieceType() == PieceType.KING && checkPiece.getTeamColor() == thisColor) {
            return true;
        } else if (checkPiece == null) {
            return pKDownLeftHelper(board, position, test1);
        }
        return false;
    }
    
    protected boolean pKDownHelper(ChessBoard board, ChessPosition position, ChessPosition test) {
        var test1 = new ChessPositionImp(test.getRow() - 1, test.getColumn());
        if (isInBounds(test1)) {
            return false;
        }
        var thisColor = board.getPiece(position).getTeamColor();
        var checkPiece = board.getPiece(test1);
        if (checkPiece != null && checkPiece.getPieceType() == PieceType.KING && checkPiece.getTeamColor() == thisColor) {
            return true;
        } else if (checkPiece == null) {
            return pKDownHelper(board, position, test1);
        }
        return false;
    }
    
    protected boolean pKDownRightHelper(ChessBoard board, ChessPosition position, ChessPosition test) {
        var test1 = new ChessPositionImp(test.getRow()-1, test.getColumn()+1);
        if (isInBounds(test1)) {
            return false;
        }
        var thisColor = board.getPiece(position).getTeamColor();
        var checkPiece = board.getPiece(test1);
        if (checkPiece != null && checkPiece.getPieceType() == PieceType.KING && checkPiece.getTeamColor() == thisColor) {
            return true;
        } else if (checkPiece == null) {
            return pKDownRightHelper(board, position, test1);
        }
        return false;
    }
    
    protected boolean pKRightHelper(ChessBoard board, ChessPosition position, ChessPosition test) {
        var test1 = new ChessPositionImp(test.getRow(), test.getColumn()+1);
        if (isInBounds(test1)) {
            return false;
        }
        var thisColor = board.getPiece(position).getTeamColor();
        var checkPiece = board.getPiece(test1);
        if (checkPiece != null && checkPiece.getPieceType() == PieceType.KING && checkPiece.getTeamColor() == thisColor) {
            return true;
        } else if (checkPiece == null) {
            return pKRightHelper(board, position, test1);
        }
        return false;
    }
    
    protected boolean pKUpRightHelper(ChessBoard board, ChessPosition position, ChessPosition test) {
        var test1 = new ChessPositionImp(test.getRow() + 1, test.getColumn()+1);
        if (isInBounds(test1)) {
            return false;
        }
        var thisColor = board.getPiece(position).getTeamColor();
        var checkPiece = board.getPiece(test1);
        if (checkPiece != null && checkPiece.getPieceType() == PieceType.KING && checkPiece.getTeamColor() == thisColor) {
            return true;
        } else if (checkPiece == null) {
            return pKUpRightHelper(board, position, test1);
        }
        return false;
    }
    
    private boolean isInBounds(ChessPositionImp testPosition) {
        return testPosition.getRow() < 0 || testPosition.getRow() > 7 || testPosition.getColumn() < 0 || testPosition.getColumn() > 7;
    }
    
    @Override
    public int hashCode() {
        int result = myType != null ? myType.hashCode() : 0;
        result = 31 * result + myColor.hashCode();
        return result;
    }
    
    @Override
    public String toString() {
        return "" + myType + myColor;
    }
}
