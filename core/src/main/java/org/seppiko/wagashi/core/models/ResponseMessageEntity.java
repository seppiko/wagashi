package org.seppiko.wagashi.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

/**
 * @author Leonard Woo
 */
public record ResponseMessageEntity<T>(@JsonProperty("code") Integer code,
                                       @JsonProperty("message") String message,
                                       @JsonProperty("payload") T payload,
                                       @JsonProperty("errors") ArrayList<ResponseMessageEntity<?>> errors) {

  public ResponseMessageEntity(Integer code, String message) {
    this(code, message, null);
  }

  public ResponseMessageEntity(Integer code, String message, T payload) {
    this(code, message, payload, null);
  }

  public ResponseMessageEntity(ArrayList<ResponseMessageEntity<?>> errors) {
    this(null, null, null, errors);
  }

}
