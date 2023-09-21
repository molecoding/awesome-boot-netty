package com.molecoding.nobs.server;

import com.google.common.io.ByteStreams;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.io.OutputStream;
import java.net.Socket;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CommandResourceTest {
  @Autowired
  MockMvc mvc;

  @Test
  void sendCommandByApi_shouldOk() throws Exception {

    try (Socket sock = new Socket("localhost", 9494)) {
      sock.setSoTimeout(1000);
      OutputStream os = sock.getOutputStream();
      os.write("{0F8643330470988270000100251241BE4F9C8A2672A4}".getBytes());
      os.flush();

      String expectedLoginRsp = "{8643330470988270000101yes!0000}";
      byte[] bytesLoginRsp = new byte[expectedLoginRsp.length()];
      ByteStreams.read(sock.getInputStream(), bytesLoginRsp, 0, bytesLoginRsp.length);
      assertThat(new String(bytesLoginRsp)).isEqualTo(expectedLoginRsp);

      mvc.perform(post("/clients/864333047098827000/cmd/123"))
        .andDo(print())
        .andExpect(status().isOk())
      ;

      String expected = "{86433304709882700012300command0000}";
      byte[] bytes = new byte[expected.length()];
      ByteStreams.read(sock.getInputStream(), bytes, 0, bytes.length);
      assertThat(new String(bytes)).isEqualTo(expected);
    }
  }
}
