package com.molecoding.nobs.server;

import io.netty.channel.Channel;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ChannelRepository {
  private final ConcurrentMap<String, Channel> channelCache = new ConcurrentHashMap<>();

  public void put(String key, Channel value) {
    channelCache.put(key, value);
  }

  public Optional<Channel> get(String key) {
    return Optional.ofNullable(channelCache.get(key));
  }

  public void remove(String key) {
    this.channelCache.remove(key);
  }

  public int size() {
    return this.channelCache.size();
  }

  public Set<String> keys() {
    return this.channelCache.keySet();
  }
}
