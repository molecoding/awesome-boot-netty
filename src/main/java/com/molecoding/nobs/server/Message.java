package com.molecoding.nobs.server;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {
  String imei;
  String cmd;
  String ord;
  String data;
  String sum;
}
