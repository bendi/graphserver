package org.collibra.challenge;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.collibra.challenge.graph.handler.NodeOperationHandler;
import org.collibra.challenge.graph.manager.JGraphTNodeManager;
import org.collibra.challenge.graph.manager.NodeOperationManager;
import org.collibra.challenge.protocol.handlers.SessionClosedHandler;
import org.collibra.challenge.protocol.handlers.SessionStartHandler;
import org.collibra.challenge.protocol.handlers.RequestDecoder;
import org.collibra.challenge.protocol.handlers.ResponseEncoder;

import java.time.Duration;
import java.util.UUID;

/**
 *
 */
public class Server {

    private static final Duration READ_TIMEOUT = Duration.ofSeconds(30);

    public void run(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        NodeOperationManager nodeOperationManager = new JGraphTNodeManager();

        LogManager.getLogger().info("Starting Collibra Challenge server on port: {}", port);

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                .handler(new LoggingHandler(LogLevel.INFO))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {

                        UUID sessionId = UUID.randomUUID();

                        Logger LOG = LogManager.getLogger();

                        LOG.info("Started session: {}", sessionId);

                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("readTimeoutHandler", new ReadTimeoutHandler((int) READ_TIMEOUT.getSeconds()));

                        pipeline.addLast(new ResponseEncoder());
                        pipeline.addLast(new RequestDecoder());

                        pipeline.addLast(new SessionStartHandler(sessionId));
                        pipeline.addLast(new SessionClosedHandler(sessionId));

                        pipeline.addLast(new NodeOperationHandler(nodeOperationManager));
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync();

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.run(50_000);
    }

}
