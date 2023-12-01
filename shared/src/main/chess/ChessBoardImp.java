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
    
    public ChessBoardImp(ChessBoardImp oldBoard) {
        var position = new ChessPositionImp(0,0);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                position.setPosition(i, j);
                var oldPiece = oldBoard.getPiece(position);
                ChessPiece item;
                if (oldPiece == null) {
                    item = null;
                } else {
                    item = getNewPiece(oldPiece.getTeamColor(), oldPiece.getPieceType());
                }
                boardSpaces.add(item);
            }
        }
    }
    
    public ChessPiece getNewPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type){
        String colour;
        if (pieceColor == ChessGame.TeamColor.WHITE) {
            colour = "w";
        } else {
            colour = "b";
        }
        if (type == ChessPiece.PieceType.KNIGHT) {
            return new Knight(colour);
        } else if (type == ChessPiece.PieceType.PAWN) {
            return new Pawn(colour);
        } else if (type == ChessPiece.PieceType.KING) {
            return new King(colour);
        } else if (type == ChessPiece.PieceType.ROOK) {
            return new Rook(colour);
        } else if (type == ChessPiece.PieceType.BISHOP) {
            return new Bishop(colour);
        } else if (type == ChessPiece.PieceType.QUEEN) {
            return new Queen(colour);
        }
        return null;
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
    
    public void setPiece(ChessPosition toBeReplaced, ChessPosition toReplace) {
        boardSpaces.set(toBeReplaced.getRow() * 8 + toBeReplaced.getColumn(), boardSpaces.get(toReplace.getRow() * 8 + toReplace.getColumn()));
    }
    
    public void setPiece(ChessPosition toBeReplaced, ChessPiece toReplace) {
        boardSpaces.set(toBeReplaced.getRow() * 8 + toBeReplaced.getColumn(), toReplace);
    }
    
    public void clearPiece(ChessPosition position) {
        boardSpaces.set(position.getRow() * 8 + position.getColumn(), null);
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
    
    @Override
    public String toString() {
        int i = 0;
        StringBuilder str = new StringBuilder();
        for (var spot: this.boardSpaces) {
            if (i%8 == 0) {
                str.append("|\n");
            }
            if (spot == null) {
                str.append("| ");
            } else {
                str.append("|");
                str.append(spot);
            }
            i++;
        }
        return str.toString();
    }
    
    public String toStringUnique(boolean white) {
        if (white) {
            int i = 0;
            StringBuilder str = new StringBuilder();
            for (var spot : this.boardSpaces) {
                if (i % 8 == 0) {
                    str.append("|\n");
                }
                if (spot == null) {
                    str.append("| ");
                } else {
                    str.append("|");
                    str.append(spot);
                }
                i++;
            }
            return str.toString();
        } else {
            StringBuilder str = new StringBuilder();
            for (int i = 63; i >= 0; i--) {
                if (i % 8 == 0) {
                    str.append("|\n");
                }
                if (boardSpaces.get(i) == null) {
                    str.append("| ");
                } else {
                    str.append("|");
                    str.append(boardSpaces.get(i));
                }
            }
            return str.toString();
        }
    }
}
