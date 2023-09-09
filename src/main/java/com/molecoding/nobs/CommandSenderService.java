package com.molecoding.nobs;

import com.molecoding.nobs.server.ChannelRepository;
import com.molecoding.nobs.server.Client;
import com.molecoding.nobs.server.ClientUnavailableException;
import com.molecoding.nobs.server.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommandSenderService {
  private final ChannelRepository channelRepository;

  public void send(Message message) throws ClientUnavailableException {
    Client.current(channelRepository, message.getImei())
      .orElseThrow(ClientUnavailableException::new).send(message);
  }

  public Set<String> onlineClients() {
    return channelRepository.keys();
  }
}
