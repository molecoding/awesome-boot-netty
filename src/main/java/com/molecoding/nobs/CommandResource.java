package com.molecoding.nobs;

import com.molecoding.nobs.server.ClientUnavailableException;
import com.molecoding.nobs.server.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController("/")
public class CommandResource {

  private final CommandSenderService commandService;

  @GetMapping("/clients")
  public Set<String> fetchOnlineClients() {
    return commandService.onlineClients();
  }

  @PostMapping("/clients/{client}/cmd/{command}")
  public ResponseEntity<Void> sendCommand(@PathVariable String client, @PathVariable String command) {
    try {
      commandService.send(Message.builder()
        .imei(client)
        .cmd(command)
        .ord("00")
        .data("command")
        .sum("0000")
        .build());
      return ResponseEntity.ok().build();
    } catch (ClientUnavailableException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
