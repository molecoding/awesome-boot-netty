package com.molecoding.nobs;

import com.molecoding.nobs.server.TCPServer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
@SpringBootApplication
public class AwesomeBootNettyApplication {
  private final TCPServer tcpServer;

  public static void main(String[] args) {
    SpringApplication.run(AwesomeBootNettyApplication.class, args);
  }

  @Bean
  public ApplicationListener<ApplicationReadyEvent> readyEventApplicationListener() {
    return applicationReadyEvent -> tcpServer.start();
  }

}
