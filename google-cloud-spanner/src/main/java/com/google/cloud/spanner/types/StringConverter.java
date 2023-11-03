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
import com.google.common.base.Preconditions;
import com.google.protobuf.Value.KindCase;

public class StringConverter implements ConverterInterface<String> {
  private static final int MAX_DEBUG_STRING_LENGTH = 36;
  private static final String ELLIPSIS = "...";
  private static final String NULL_STRING = "NULL";
  private StringConverter() {
  }

  private static ConverterInterface instance = new StringConverter();

  public static ConverterInterface getInstance() {
    return instance;
  }

  @Override
  public String toObject(com.google.protobuf.Value proto, KindCase protoKindCase) {
    Preconditions.checkState(protoKindCase == KindCase.STRING_VALUE); // TODO(gunjj@): Verify that the error message is not getting changed between this and older type-specific Impl classes.
    // .. See AbstractResultSet::checkType() for the logic used there, even though this path is different from Impl-specific classes initialization
    return proto.getStringValue();
  }

  @Override
  public com.google.protobuf.Value toProto(String value) {
    return com.google.protobuf.Value.newBuilder().setStringValue(value).build(); // TODO(gunjj@) cleanup: Ref - Current logic inherited from AbstractObjectValue class
  }

  @Override
  public String toString(String value) {
    if (value.length() > MAX_DEBUG_STRING_LENGTH) {
      StringBuilder b = new StringBuilder(MAX_DEBUG_STRING_LENGTH);
      b.append(value, 0, MAX_DEBUG_STRING_LENGTH - ELLIPSIS.length()).append(ELLIPSIS);
      return b.toString();
    } else {
      return value;
    }
  }

  @Override
  public String getAsString(String value, boolean isNull) {
    return isNull ? NULL_STRING : value;
  }

  @Override
  public int valueHash(String value) {
    return value.hashCode();
  }

  @Override
  public boolean equals(Type fieldType, String value, com.google.cloud.spanner.Value other) {
    // null check
    if (value == null || other == null) {
      return false;
    }
    // self check
    if (value.equals(other)) {
      return true;
    }
    // type check and cast
    if (other.getType().getCode() != fieldType.getCode()) {
      return false;
    }
    // field comparison for string-based types
    return value.equals(other.getString());
  }
}
