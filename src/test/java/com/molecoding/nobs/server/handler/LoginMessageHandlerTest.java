package com.molecoding.nobs.server.handler;

import com.molecoding.nobs.server.ChannelRepository;
import com.molecoding.nobs.server.Message;
import com.molecoding.nobs.server.handler.LoginMessageHandler;
import com.molecoding.nobs.server.handler.MessageDecoder;
import com.molecoding.nobs.server.handler.MessageEncoder;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class LoginMessageHandlerTest {
  ChannelRepository channelRepository;

  @BeforeEach
  void setup() {
    channelRepository = mock(ChannelRepository.class);
  }

  @Test
  @DisplayName("login handler test")
  void testLogin() {
    // given
    EmbeddedChannel embeddedChannel = new EmbeddedChannel(
      new StringDecoder(CharsetUtil.UTF_8),
      new MessageDecoder(),
      new LoginMessageHandler(this.channelRepository),
      new MessageEncoder(),
      new StringEncoder(CharsetUtil.UTF_8)
    );

    // when
    String imei = "864333047098827000";
    embeddedChannel.writeInbound("{" + "0F" +
      imei +
      "01" +
      "00" +
      "251241BE4F9C8A26" +
      "72A4" +
      "}");

    // then
    Queue<Object> outboundMessages = embeddedChannel.outboundMessages();
    Object decoded = outboundMessages.poll();

    assertThat(decoded).isNotNull();
    assertThat(decoded).isInstanceOf(Message.class);
    Message message = (Message) decoded;
    assertThat(message.getImei()).isEqualTo(imei);
    assertThat(message.getOrd()).isEqualTo("01");
  }
}
