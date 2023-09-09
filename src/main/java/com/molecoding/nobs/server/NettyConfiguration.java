package com.molecoding.nobs.server;

import com.molecoding.nobs.server.handler.ServerChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Configuration
@RequiredArgsConstructor
public class NettyConfiguration {
  @Value("${netty.port:9494}")
  private Integer port;

  @Value("${netty.logging:INFO}")
  private LogLevel logLevel;

  @Bean(name = "serverBootstrap")
  public ServerBootstrap bootstrap(ServerChannelInitializer channelInitializer) {
    ServerBootstrap b = new ServerBootstrap();
    b.group(bossGroup(), workerGroup())
      .channel(NioServerSocketChannel.class)
      .handler(new LoggingHandler(logLevel))
      .childHandler(channelInitializer);
    return b;
  }

  @Bean(destroyMethod = "shutdownGracefully")
  public NioEventLoopGroup bossGroup() {
    return new NioEventLoopGroup(1);
  }

  @Bean(destroyMethod = "shutdownGracefully")
  public NioEventLoopGroup workerGroup() {
    return new NioEventLoopGroup();
  }

  @Bean
  public InetSocketAddress tcpSocketAddress() {
    return new InetSocketAddress(port);
  }

  @Bean
  public ChannelRepository channelRepository() {
    return new ChannelRepository();
  }
}
