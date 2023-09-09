package com.molecoding.nobs.server.handler;

import com.molecoding.nobs.server.Message;
import com.molecoding.nobs.server.handler.MessageDecoder;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageDecoderTest {
  @Test
  @DisplayName("decode message should ok")
  void testMessageDecoder() {
    // given
    EmbeddedChannel embeddedChannel = new EmbeddedChannel(new MessageDecoder());

    String imei = "864333047098827000";
    // when
    embeddedChannel.writeInbound("{" + "0F" +
      imei +
      "01" +
      "00" +
      "251241BE4F9C8A26" +
      "72A4" +
      "}");

    // then
    Object decoded = embeddedChannel.readInbound();
    assertThat(decoded).isNotNull();
    assertThat(decoded).isInstanceOf(Message.class);
    Message message = (Message) decoded;
    assertThat(message.getImei()).isEqualTo(imei);
  }
}
