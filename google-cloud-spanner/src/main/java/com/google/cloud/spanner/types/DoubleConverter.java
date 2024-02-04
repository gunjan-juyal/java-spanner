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
 * Converts language-specific FLOAT64 (Double) values from and to different encodings - wire-format,
 * hash, string etc
 */
public class DoubleConverter implements ConverterInterface<Double> {

  private static final String NULL_STRING = "NULL";
  private static ConverterInterface instance = new DoubleConverter();

  private DoubleConverter() {
  }

  public static ConverterInterface getInstance() {
    return instance;
  }

  @Override
  public Double toObject(com.google.protobuf.Value proto, KindCase protoKindCase) {
    // TODO(gunjj@): FLOAT64 is complicated: KindCase can be STRING_VALUE if value is [-Infinity,Infinity,NaN], otherwise
    //  NUMBER_VALUE. Also, we're not using this KindCase in specific converters right now..
    //  This code is duplicated from AbstractResultSet::valueProtoToFloat64; can we remove this
    //  duplication?
    // Preconditions.checkState(protoKindCase == KindCase.NUMBER_VALUE);
    if (proto.getKindCase() == KindCase.STRING_VALUE) {
      switch (proto.getStringValue()) {
        case "-Infinity":
          return Double.NEGATIVE_INFINITY;
        case "Infinity":
          return Double.POSITIVE_INFINITY;
        case "NaN":
          return Double.NaN;
        default:
          // Fall-through to handling below to produce an error.
      }
    }
    if (proto.getKindCase() != KindCase.NUMBER_VALUE) {
      // Keeping failure message similar to existing behaviour defined in AbstractResultSet float64
      //  extraction logic
      throw newSpannerException(
          ErrorCode.INTERNAL,
          "Invalid value for column type "
              + Type.float64()
              + " expected NUMBER_VALUE or STRING_VALUE with value one of"
              + " \"Infinity\", \"-Infinity\", or \"NaN\" but was "
              + proto.getKindCase()
              + (proto.getKindCase() == KindCase.STRING_VALUE
              ? " with value \"" + proto.getStringValue() + "\""
              : ""));
    }
    return Double.valueOf(proto.getNumberValue());
  }

  @Override
  public com.google.protobuf.Value toProto(Double value) {
    return com.google.protobuf.Value.newBuilder().setNumberValue(value).build();
  }

  @Override
  public String toString(Double value) {
    return value.toString();
  }

  @Override
  public String getAsString(Double value, boolean isNull) {
    return isNull ? NULL_STRING : toString(value);
  }

  @Override
  public int valueHash(Double value) {
    return value.hashCode();
  }

  // TODO(gunjj@) Much of this method may be generic - consider reusing for various ConverterInterfaces
  @Override
  public boolean equals(Type fieldType, Double value, Value other) {
    // null check
    if (value == null || other == null) {
      return false;
    }
    // self check
    if (value.equals(other)) {
      return true;
    }
    // type check and cast
    if (other.getType().getCode() != Code.FLOAT64) {
      return false;
    }
    // field comparison
    return value.doubleValue() == other.getFloat64();
  }
}
