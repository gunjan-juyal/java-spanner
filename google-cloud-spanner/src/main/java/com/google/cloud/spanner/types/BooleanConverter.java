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

import static com.google.cloud.spanner.SpannerExceptionFactory.newSpannerException;

import com.google.cloud.spanner.ErrorCode;
import com.google.cloud.spanner.Type;
import com.google.cloud.spanner.Type.Code;
import com.google.cloud.spanner.Value;
import com.google.common.base.Preconditions;
import com.google.protobuf.Value.KindCase;

/**
 * Converts language-specific BOOL values from and to different encodings - wire-format,
 * hash, string etc
 */
public class BooleanConverter implements ConverterInterface<Boolean> {

  private static final String NULL_STRING = "NULL";
  private static ConverterInterface instance = new BooleanConverter();

  private BooleanConverter() {
  }

  public static ConverterInterface getInstance() {
    return instance;
  }

  @Override
  public Boolean toObject(com.google.protobuf.Value proto, KindCase protoKindCase) {
    Preconditions.checkState(protoKindCase == KindCase.BOOL_VALUE);
    return proto.getBoolValue();
  }

  @Override
  public com.google.protobuf.Value toProto(Boolean value) {
    return com.google.protobuf.Value.newBuilder().setBoolValue(value).build();
  }

  @Override
  public String toString(Boolean value) {
    return value.toString();
  }

  @Override
  public String getAsString(Boolean value, boolean isNull) {
    return isNull ? NULL_STRING : toString(value);
  }

  @Override
  public int valueHash(Boolean value) {
    return value.hashCode();
  }

  // TODO(gunjj@) Much of this method may be generic - consider reusing for various ConverterInterfaces
  @Override
  public boolean equals(Type fieldType, Boolean value, Value other) {
    // null check
    if (value == null || other == null) {
      return false;
    }
    // self check
    if (value.equals(other)) {
      return true;
    }
    // type check and cast
    if (other.getType().getCode() != Code.BOOL) {
      return false;
    }
    // field comparison
    return value.equals(other.getBool());
  }
}
