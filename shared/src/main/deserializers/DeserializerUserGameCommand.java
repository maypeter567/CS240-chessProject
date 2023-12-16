package deserializers;

import com.google.gson.*;
import webSocketMessages.userCommands.UserGameCommand;
import webSocketMessages.userCommands.allCommands.*;

import java.lang.reflect.Type;

public class DeserializerUserGameCommand implements JsonDeserializer<UserGameCommand> {
    @Override
    public UserGameCommand deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        GsonBuilder gson = new GsonBuilder();
        String commandType = jsonElement.getAsJsonObject().get("commandType").getAsString();
        return switch (commandType) {
            case "JOIN_PLAYER" -> gson.create().fromJson(jsonElement, JoinPlayerGameCommand.class);
            case "JOIN_OBSERVER" -> gson.create().fromJson(jsonElement, JoinObserverGameCommand.class);
            case "MAKE_MOVE" -> gson.create().fromJson(jsonElement, MakeMoveGameCommand.class);
            case "LEAVE" -> gson.create().fromJson(jsonElement, LeaveGameCommand.class);
            case "RESIGN" -> gson.create().fromJson(jsonElement, ResignGameCommand.class);
            default -> throw new UnsupportedOperationException("the class is wrong for UserGameCommand gson");
        };
    }
}
