package hoversprite.notification.internal.config;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import hoversprite.auth.external.service.JwtService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Getter
@Component
public class SocketIOHandler {

    @Autowired
    private JwtService jwtService;

    private final SocketIOServer socketIOServer;
    public SocketIOHandler(SocketIOServer socketIOServer) {
        socketIOServer.addConnectListener(this::onConnect);
        socketIOServer.addDisconnectListener(this::onDisconnect);
        this.socketIOServer = socketIOServer;

        socketIOServer.start();
    }
    @OnConnect
    public void onConnect(SocketIOClient client) {
        System.out.println(client + "connected");
        String authHeader = client.getHandshakeData().getHttpHeaders().get("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            System.out.println(token);
            if (!jwtService.isTokenExpired(token)) {
                String userId = jwtService.getUserIdFromJwt(token);

                client.set("userId", userId);

                System.out.println("Client connected with userId: " + userId);
            } else {
                System.out.println("Invalid JWT token");
                client.disconnect();
            }
        } else {
            System.out.println("Authorization header missing or invalid");
            client.disconnect();
        }
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        String userId = client.get("userId");
        System.out.println("Client disconnected with userId: " + userId);
    }

}