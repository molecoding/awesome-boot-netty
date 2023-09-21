package com.molecoding.nobs.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
  private final MessageDecoder messageDecoder;
  private final CommandMessageHandler commandMessageHandler;
  private final LoginMessageHandler loginMessageHandler;

  private final MessageEncoder messageEncoder;

  @Override
  protected void initChannel(SocketChannel socketChannel) {
    ChannelPipeline pipeline = socketChannel.pipeline();

    pipeline.addLast(new DelimiterBasedFrameDecoder(1024 * 1024,
      new ByteBuf[]{Unpooled.wrappedBuffer("}".getBytes())}));
    pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
    pipeline.addLast(messageDecoder);
    pipeline.addLast(loginMessageHandler);
    pipeline.addLast(commandMessageHandler);

    // Encoder
    pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
    pipeline.addLast(messageEncoder);
  }
}
