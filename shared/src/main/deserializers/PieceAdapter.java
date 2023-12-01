package deserializers;

import chess.ChessPiece;
import chess.pieces.*;
import com.google.gson.*;

import java.lang.reflect.Type;

    public class PieceAdapter implements JsonDeserializer<ChessPiece> {
        @Override
        public ChessPiece deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            GsonBuilder gson = new GsonBuilder();
            String pieceType = jsonElement.getAsJsonObject().get("myType").getAsString();
            return switch (pieceType) {
                case "QUEEN" -> gson.create().fromJson(jsonElement, Queen.class);
                case "KING" -> gson.create().fromJson(jsonElement, King.class);
                case "KNIGHT" -> gson.create().fromJson(jsonElement, Knight.class);
                case "BISHOP" -> gson.create().fromJson(jsonElement, Bishop.class);
                case "ROOK" -> gson.create().fromJson(jsonElement, Rook.class);
                case "PAWN" -> gson.create().fromJson(jsonElement, Pawn.class);
                default -> throw new UnsupportedOperationException("the class is wrong for chess piece gson");
            };
        }
    }
