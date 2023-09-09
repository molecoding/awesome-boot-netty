package com.molecoding.nobs.server.handler;

import com.molecoding.nobs.server.Message;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@ChannelHandler.Sharable
public class MessageDecoder extends MessageToMessageDecoder<String> {
  @Override
  protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
    try {
      out.add(Message.builder()
        .imei(msg.substring(3, 21))
        .cmd(msg.substring(21, 23))
        .ord(msg.substring(23, 25))
        .data(msg.substring(25, msg.length() - 5))
        .sum(msg.substring(msg.length() - 5, msg.length() - 1))
        .build());
      log.debug("message decoded: {}", out.get(0));
    } catch (Exception e) {
      log.error("decode error", e);
      ReferenceCountUtil.release(msg);
    }
  }
}
