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
import com.google.cloud.spanner.SpannerExceptionFactory;
import com.google.cloud.spanner.Type;
import com.google.common.base.Preconditions;
import com.google.protobuf.Value;
import com.google.protobuf.Value.KindCase;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TypeConversionDelegate {

  private TypeConversionDelegate() {
  }

  private static final TypeConversionDelegate TYPE_CONVERSION_DELEGATE = new TypeConversionDelegate();

  public static TypeConversionDelegate getInstance() {
    return TYPE_CONVERSION_DELEGATE;
  }

  // TODO(gunjj@) Type safety concerns?
  public Object parseType(Type fieldType, Value proto) {
    // Step-1: Get mapper for this type
    List<TypeMapper> mappers =  Arrays.stream(TypeMapper.values()).filter(m -> m.getCode() == fieldType.getCode()).collect(
        Collectors.toList());
    Preconditions.checkState(mappers.size() == 1,
        "No mapper available for type code: " + fieldType.getCode());
    TypeMapper mapper = mappers.get(0);
    checkType(fieldType, proto, mapper.getProtoKindCase());
    // mapper.getJavaTypeConverter().getInstance().toObject(mapper.getProtoKindCase(), proto);
    ConverterInterface converter = ConverterFactory.getInstance()
        .getConverter(mapper.getJavaTypeConverter());

    // Step-2: Extract value
    return converter.toObject(proto, mapper.getProtoKindCase());
  }

  private static void checkType(
      Type fieldType, com.google.protobuf.Value proto, KindCase expected) {
    if (proto.getKindCase() != expected) {
      throw SpannerExceptionFactory.newSpannerException(
          ErrorCode.INTERNAL,
          "Invalid value for column type "
              + fieldType
              + " expected "
              + expected
              + " but was "
              + proto.getKindCase());
    }
  }

  public <R> Value toProto(Type fieldType, R value) {
    ConverterInterface converter = getConverterInterface(fieldType);
    return converter.toProto(value);
  }

  private static ConverterInterface getConverterInterface(Type fieldType) {
    List<TypeMapper> mappers =  Arrays.stream(TypeMapper.values()).filter(m -> m.getCode() == fieldType.getCode()).collect(
        Collectors.toList());
    Preconditions.checkState(mappers.size() == 1,
        "No mapper available for type code: " + fieldType.getCode());
    TypeMapper mapper = mappers.get(0);
    // checkType(fieldType, proto, mapper.getProtoKindCase()); // TODO(gunjj@) Is this needed?
    // mapper.getJavaTypeConverter().getInstance().toObject(mapper.getProtoKindCase(), proto);
    ConverterInterface converter = ConverterFactory.getInstance()
        .getConverter(mapper.getJavaTypeConverter());
    return converter;
  }

  public <R> int valueHash(Type fieldType, R value) {
    ConverterInterface converter = getConverterInterface(fieldType);
    return converter.valueHash(value);
  }

  public <R> boolean equals(Type fieldType, R value, com.google.cloud.spanner.Value other) {
    return getConverterInterface(fieldType).equals(fieldType, value, other);
  }

  public <R> String toString(Type fieldType, R value) {
    return getConverterInterface(fieldType).toString(value);
  }

  public <R> String getAsString(Type fieldtype, R value, boolean isNull) {
    return getConverterInterface(fieldtype).getAsString(value, isNull);
  }
}
