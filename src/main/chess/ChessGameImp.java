package chess;

import chess.pieces.*;

import java.util.Collection;
import java.util.Vector;

public class ChessGameImp implements ChessGame {
    
    protected ChessGame.TeamColor currentTurn;
    protected ChessBoardImp myBoard;
    
    public ChessGameImp() {
        currentTurn = TeamColor.WHITE;
        myBoard = new ChessBoardImp();
    }
    
    @Override
    public TeamColor getTeamTurn() {
        return currentTurn;
    }
    
    @Override
    public void setTeamTurn(TeamColor team) {
        currentTurn = team;
    }
    
    @Override
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        Vector<ChessMove> moves = (Vector<ChessMove>) myBoard.getPiece(startPosition).pieceMoves(myBoard, startPosition);
        Vector<ChessMove> returnMoves = new Vector<>();
        if (isInCheck(myBoard.getPiece(startPosition).getTeamColor())) {
            for (var move : moves) {
                try {
                    var tempTurn = currentTurn;
                    currentTurn = myBoard.getPiece(move.getStartPosition()).getTeamColor();
                    if (!moveCheckPredictor(move)) {
                        currentTurn = tempTurn;
                        returnMoves.add(move);
                    }
                    currentTurn = tempTurn;
                } catch (InvalidMoveException e) {
                    throw new RuntimeException(e);
                }
            }
            return (Collection<ChessMove>) returnMoves;
        }
        return (Collection<ChessMove>) moves;
    }
    
    private boolean moveCheckPredictor(ChessMove move) throws InvalidMoveException {
        ChessBoardImp newBoard = new ChessBoardImp(myBoard);
        ChessBoardImp oldBoard = myBoard;
        myBoard = newBoard;
        makeMoveChecked(move);
        if (isInCheck(currentTurn)) {
            myBoard = oldBoard;
            return true;
        } else {
            myBoard = oldBoard;
            return false;
        }
    }
    
    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
        var tempVect = myBoard.getPiece(move.getStartPosition()).pieceMoves(myBoard, move.getStartPosition());
        if (!tempVect.contains(move)) {
            throw new InvalidMoveException("this is not a legal move");
        }
        if (myBoard.getPiece(move.getStartPosition()).getTeamColor() != this.currentTurn) {
            throw new InvalidMoveException("this is not your turn");
        }
        if (isInCheck(this.currentTurn)) {
            if (!isInCheckmate(this.currentTurn)) {
                if (moveCheckPredictor(move)) {
                    throw new InvalidMoveException("you are in check before and after this move");
                }
            }
        }
        if (moveCheckPredictor(move)) {
            throw new InvalidMoveException("you are in check after this move");
        }
        
        if (move.getPromotionPiece() != null) {
            var piece = myBoard.getPiece(move.getStartPosition());
            myBoard.setPiece(move.getEndPosition(), getNewPiece(piece.getTeamColor(), move.getPromotionPiece()));
            myBoard.clearPiece(move.getStartPosition());
        } else {
            myBoard.setPiece(move.getEndPosition(), move.getStartPosition());
            myBoard.clearPiece(move.getStartPosition());
        }
        if (this.currentTurn == TeamColor.WHITE) {
            this.currentTurn = TeamColor.BLACK;
        } else {
            this.currentTurn = TeamColor.WHITE;
        }
        
    }
    
    public void makeMoveChecked(ChessMove move){
        if (move.getPromotionPiece() != null) {
            var piece = myBoard.getPiece(move.getStartPosition());
            myBoard.setPiece(move.getEndPosition(), getNewPiece(piece.getTeamColor(), move.getPromotionPiece()));
            myBoard.clearPiece(move.getStartPosition());
        } else {
            myBoard.setPiece(move.getEndPosition(), move.getStartPosition());
            myBoard.clearPiece(move.getStartPosition());
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
    public boolean isInCheck(TeamColor teamColor) {
        Vector<ChessMove> vector = new Vector<>();
        ChessPositionImp position = new ChessPositionImp(0, 0);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                position.setPosition(i, j);
                if (myBoard.getPiece(position) != null) {
                    var tempVector = myBoard.getPiece(position).pieceMoves(myBoard, position);
                    vector.addAll(tempVector);
                }
            }
        }
        for (var item : vector) {
            if (myBoard.getPiece(item.getEndPosition()) != null) {
                var temp = myBoard.getPiece(item.getEndPosition());
                if (temp.getPieceType() == ChessPiece.PieceType.KING && temp.getTeamColor() == teamColor) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        Vector<ChessMove> vector = new Vector<>();
        Vector<ChessMove> kingVector = new Vector<>();
        ChessPositionImp position = new ChessPositionImp(0, 0);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                position.setPosition(i, j);
                if (myBoard.getPiece(position) != null) {
                    var tempVector = myBoard.getPiece(position).pieceMoves(myBoard, position);
                    vector.addAll(tempVector);
                }
            }
        }
        for (var item : vector) {
            if (myBoard.getPiece(item.getEndPosition()) != null) {
                var temp = myBoard.getPiece(item.getEndPosition());
                if (temp.getPieceType() == ChessPiece.PieceType.KING && temp.getTeamColor() == teamColor) {
                    var tempVector = temp.pieceMoves(myBoard, item.getEndPosition());
                    kingVector.addAll(tempVector);
                    boolean inCheck = false;
                    for (var item2 : kingVector) {
                        for (var item3 : vector) {
                            if (item2.getEndPosition().getColumn() == item3.getEndPosition().getColumn() &&
                                    item2.getEndPosition().getRow() == item3.getEndPosition().getRow()) {
                                inCheck = true;
                            }
                        }
                        if (!inCheck) {
                            break;
                        }
                    }
                    return inCheck;
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        Vector<ChessMove> vector = new Vector<>();
        ChessPositionImp position = new ChessPositionImp(0, 0);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                position.setPosition(i, j);
                if (myBoard.getPiece(position) != null && myBoard.getPiece(position).getTeamColor() == teamColor) {
                    var tempVector = myBoard.getPiece(position).pieceMoves(myBoard, new ChessPositionImp(position.getRow(), position.getColumn()));
                    vector.addAll(tempVector);
                }
            }
        }
        for (var test : vector) {
            try {
                if (!moveCheckPredictor(test)) {
                    return false;
                }
            } catch (InvalidMoveException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }
    
    @Override
    public void setBoard(ChessBoard board) {
        myBoard = (ChessBoardImp) board;
    }
    
    @Override
    public ChessBoard getBoard() {
        return myBoard;
    }
}
