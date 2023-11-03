/*
 * Copyright 2017 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.spanner.types;

import com.google.cloud.spanner.Type;
import com.google.cloud.spanner.Type.Code;
import com.google.cloud.spanner.Value;
import com.google.common.base.Preconditions;
import com.google.protobuf.Value.KindCase;

/**
 * Converts language-specific FLOAT64 (Long) values from and to different encodings - wire-format, hash, string etc
 */
public class LongConverter implements ConverterInterface<Long> {
  private static final String NULL_STRING = "NULL";
  private static ConverterInterface instance = new LongConverter();

  private LongConverter() {
  }

  public static ConverterInterface getInstance() {
    return instance;
  }

  @Override
  public Long toObject(com.google.protobuf.Value proto, KindCase protoKindCase) {
    Preconditions.checkState(protoKindCase == KindCase.STRING_VALUE);
    return Long.parseLong(proto.getStringValue());
  }

  @Override
  public com.google.protobuf.Value toProto(Long value) {
    return com.google.protobuf.Value.newBuilder().setStringValue(value.toString()).build();
  }

  @Override
  public String toString(Long value) {
    return value.toString();
  }

  @Override
  public String getAsString(Long value, boolean isNull) {
    return isNull ? NULL_STRING : toString(value);
  }

  @Override
  public int valueHash(Long value) {
    return value.hashCode();
  }

  // TODO(gunjj@) Much of this method may be generic - consider reusing for various ConverterInterfaces
  @Override
  public boolean equals(Type fieldType, Long value, Value other) {
    // null check
    if (value == null || other == null) {
      return false;
    }
    // self check
    if (value.equals(other)) {
      return true;
    }
    // type check and cast
    if (other.getType().getCode() != Code.INT64) {
      return false;
    }
    // field comparison
    return value.longValue() == other.getInt64();
  }
}
