package mage.server.managers;

import mage.game.Game;
import mage.server.ChatSession;
import mage.server.DisconnectReason;
import mage.view.ChatMessage;

import java.util.List;
import java.util.UUID;

public interface ChatManager {

    UUID createChatSession(String info);

    void joinChat(UUID chatId, UUID userId);

    void leaveChat(UUID chatId, UUID userId);

    void destroyChatSession(UUID chatId);

    void broadcast(UUID chatId, String userName, String message, ChatMessage.MessageColor color, boolean withTime, Game game, ChatMessage.MessageType messageType, ChatMessage.SoundToPlay soundToPlay);

    void sendReconnectMessage(UUID userId);

    void sendMessageToUserChats(UUID userId, String message);

    void removeUser(UUID userId, DisconnectReason reason);

    List<ChatSession> getChatSessions();

    void checkHealth();
}
