package org.collibra.challenge.protocol.event;

import java.util.UUID;

public class ConnectionStartedEvent {

    private final UUID sessionId;

    private final String connectionName;

    /**
     *
     * @param sessionId
     * @param connectionName
     */
    public ConnectionStartedEvent(UUID sessionId, String connectionName) {
        this.sessionId = sessionId;
        this.connectionName = connectionName;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public String getConnectionName() {
        return connectionName;
    }
}
