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
public class LoginMessageHandler extends ChannelInboundHandlerAdapter {
  private final ChannelRepository channelRepository;

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object obj) {
    if (!(obj instanceof Message)) {
      ctx.fireChannelRead(obj);
      return;
    }

    Message msg = (Message) obj;
    if (!"01".equals(msg.getCmd())) {
      ctx.fireChannelRead(msg);
      return;
    }

    log.debug("login message: {}", msg);

    Client client = Client.of(msg.getImei(), ctx.channel());
    client.login(channelRepository, ctx.channel());

    ctx.channel().writeAndFlush(Message.builder()
      .imei(msg.getImei())
      .cmd(msg.getCmd())
      .ord(String.format("%02d", Integer.parseInt(msg.getOrd()) + 1))
      .data("yes!")
      .sum("0000")
      .build());
  }
}
