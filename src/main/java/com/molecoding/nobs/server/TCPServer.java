package com.molecoding.nobs.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

@Slf4j
@RequiredArgsConstructor
@Service
public class TCPServer {
  private final ServerBootstrap serverBootstrap;

  private final InetSocketAddress tcpPort;

  private Channel serverChannel;

  public void start() {
    try {
      ChannelFuture serverChannelFuture = serverBootstrap.bind(tcpPort).sync();
      log.info("server listening on: {}", tcpPort.getPort());
      serverChannel = serverChannelFuture.channel();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  @PreDestroy
  public void stop() throws InterruptedException {
    serverChannel.close().sync();
    if (serverChannel != null) {
      serverChannel.close();
      if (serverChannel.parent() != null) {
        serverChannel.parent().close();
      }
    }
  }
}
