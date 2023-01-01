package es.codeurjc.eoloplanner;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

@ServerEndpoint("/eoloplants/{name}")
@ApplicationScoped
public class WebSocketHandler {

    Logger LOG = Logger.getLogger(String.valueOf(WebSocketHandler.class));

    Map<String, Session> sessionMap = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("name") String name) {
        LOG.info("onOpen> " + name);

        sessionMap.put(name, session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("name") String name) {
        LOG.info("onClose> " + name);

        sessionMap.remove(name);
    }

    @OnError
    public void onError(Session session, @PathParam("name") String name, Throwable throwable) {
        LOG.info("onError> " + name + ": " + throwable);

        sessionMap.remove(name);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("name") String name) {
        LOG.info("onMessage> " + name + ": " + message);

        Session session = this.sessionMap.get(name);

        session.getAsyncRemote().sendObject(message, result -> {
            if(result.getException() != null) {
                LOG.info("Exception sending message: " + result.getException());
            }
        });
    }

    public Map<String, Session> getSessionMap() {
        return sessionMap;
    }
}
