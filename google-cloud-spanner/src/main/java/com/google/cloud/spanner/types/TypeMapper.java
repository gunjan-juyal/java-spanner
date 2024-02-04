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

import com.google.cloud.spanner.Type.Code;
import com.google.protobuf.Value.KindCase;

public enum TypeMapper {
  INT64(Code.INT64, KindCase.STRING_VALUE, LongConverter.class),
  // TODO(gunjj@): FLOAT64 is
  //  complicated: KindCase can be STRING_VALUE if value is [-Infinity,Infinity,NaN], otherwise
  //  NUMBER_VALUE. Also, we're not using this KindCase in specific converters right now..
  FLOAT64(Code.FLOAT64, KindCase.NUMBER_VALUE, DoubleConverter.class),
  BOOL(Code.BOOL, KindCase.BOOL_VALUE, BooleanConverter.class),
  STRING(Code.STRING, KindCase.STRING_VALUE, StringConverter.class),
  JSON(Code.JSON, KindCase.STRING_VALUE, StringConverter.class),
  PG_JSONB(Code.PG_JSONB, KindCase.STRING_VALUE, StringConverter.class); // TODO(gunjj@) Add other mappers

  /** KindCase of serialization in gRPC wire encoding **/
  public KindCase getProtoKindCase() {
    return protoKindCase;
  }

  /** Type code **/
  public Code getCode() {
    return code;
  }

  /** Converter instance used to generate language-specific values **/
  public Class<? extends ConverterInterface> getJavaTypeConverter() {
    return javaTypeConverter;
  }

  private final KindCase protoKindCase;
  private Code code;
  private Class<? extends ConverterInterface> javaTypeConverter;

  TypeMapper(Code code, KindCase protoKindCase, Class<? extends ConverterInterface> javaType) {
    this.code = code;
    this.javaTypeConverter = javaType;
    this.protoKindCase = protoKindCase;
  }
}
