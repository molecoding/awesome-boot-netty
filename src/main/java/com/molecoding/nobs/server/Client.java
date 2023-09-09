package com.molecoding.nobs.server;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.Getter;
import lombok.NonNull;

import java.util.Optional;

public class Client {
  private static final AttributeKey<Client> KEY = AttributeKey.newInstance("CLIENT");

  @Getter
  private final String id;
  private final Channel channel;

  private Client(String id, Channel channel) {
    this.id = id;
    this.channel = channel;
  }

  public static Client of(@NonNull String id, @NonNull Channel channel) {
    return new Client(id, channel);
  }

  public static Optional<Client> current(@NonNull Channel channel) {
    return Optional.ofNullable(channel.attr(KEY).get());
  }

  public static Optional<Client> current(ChannelRepository channelRepository, @NonNull String id) {
    return channelRepository.get(id).map(opt -> opt.attr(KEY).get());
  }


  public void login(ChannelRepository channelRepository, Channel channel) {
    channel.attr(KEY).set(this);
    channelRepository.put(this.id, channel);
  }

  public void logout(ChannelRepository channelRepository, Channel channel) {
    channel.attr(KEY).getAndSet(null);
    channelRepository.remove(this.id);
  }

  public void send(@NonNull Message message) {
    this.channel.writeAndFlush(message);
  }
}
