package org.seppiko.wagashi.core.models;

import java.util.ArrayList;

/**
 * @author Leonard Woo
 */
public record ResponseMessageEntity<T>(Integer code,
                                       String message,
                                       T payload,
                                       ArrayList<ResponseMessageEntity<?>> errors) {

  public ResponseMessageEntity(Integer code, String message) {
    this(code, message, null, null);
  }

  public ResponseMessageEntity(Integer code, String message, T payload) {
    this(code, message, payload, null);
  }

  public ResponseMessageEntity(ArrayList<ResponseMessageEntity<?>> errors) {
    this(null, null, null, errors);
  }

}
