package chess;

import java.util.Collection;

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
        return null;
    }
    
    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
    
    }
    
    @Override
    public boolean isInCheck(TeamColor teamColor) {
        return false;
    }
    
    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        return false;
    }
    
    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        return false;
    }
    
    @Override
    public void setBoard(ChessBoard board) {
        myBoard.resetBoard();
    }
    
    @Override
    public ChessBoard getBoard() {
        return myBoard;
    }
}
