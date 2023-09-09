package com.molecoding.nobs.server.handler;

import com.molecoding.nobs.server.Message;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@ChannelHandler.Sharable
public class MessageEncoder extends MessageToMessageEncoder<Message> {
  @Override
  protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
    String encoded = String.format("{%s%s%s%s%s}", msg.getImei(), msg.getCmd(), msg.getOrd(), msg.getData(), msg.getSum());
    out.add(encoded);
    log.debug("messaged encoded: {}", encoded);
  }
}
