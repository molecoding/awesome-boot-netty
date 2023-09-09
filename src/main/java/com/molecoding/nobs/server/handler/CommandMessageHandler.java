package com.molecoding.nobs.server.handler;

import com.molecoding.nobs.server.ChannelRepository;
import com.molecoding.nobs.server.Client;
import com.molecoding.nobs.server.Message;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
@ChannelHandler.Sharable
public class CommandMessageHandler extends ChannelInboundHandlerAdapter {

  private final ChannelRepository channelRepository;

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object obj) {
    if (!(obj instanceof Message)) {
      ctx.fireChannelRead(obj);
      return;
    }

    Message msg = (Message) obj;
    log.debug("command message: {}", msg);

    ctx.channel().writeAndFlush(process(msg));
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    log.error(cause.getMessage(), cause);
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) {
    Client.current(ctx.channel()).ifPresent(
      client -> {
        client.logout(this.channelRepository, ctx.channel());
        log.info("kicked out client: {}", client.getId());
      }
    );
  }

  private Message process(Message request) {
    Message response = Message.builder()
      .imei(request.getImei())
      .cmd(request.getCmd())
      .ord(String.format("%02d", Integer.parseInt(request.getOrd()) + 1))
      .data("cmd")
      .sum("0000")
      .build();
    return response;
  }
}
