package com.google.cloud.spanner;

import com.google.protobuf.Value;
import com.google.protobuf.Value.KindCase;

public class StringConverter implements ConverterInterface<String> {

  private StringConverter() {
  }

  private static ConverterInterface instance;

  public static ConverterInterface getInstance() {
    return instance;
  }

  @Override
  public String toObject(Value proto, KindCase protoKindCase) {
    return null;
  }

  // TODO(gunjj) WIP - Following code is incomplete
  @Override
  public String getAsString(Value proto, KindCase protoKindCase) {
    return null;
  }

  @Override
  public Value toProto(String value) {
    return null;
  }

  @Override
  public String toString(String value) {
    return null;
  }

  @Override
  public int valueHash(String value) {
    return 0;
  }

  @Override
  public boolean equals(String value, com.google.cloud.spanner.Value other) {
    return false;
  }
}
