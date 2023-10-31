package com.google.cloud.spanner;

import com.google.cloud.spanner.Type.Code;
import com.google.protobuf.Value.KindCase;

public enum TypeMapper {
  INT64(Code.INT64, KindCase.STRING_VALUE, LongConverter.class),
  JSON(Code.JSON, KindCase.STRING_VALUE, StringConverter.class); // TODO(gunjj@) Add other mappers

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
  private Type.Code code;
  private Class<? extends ConverterInterface> javaTypeConverter;

  TypeMapper(Code code, KindCase protoKindCase, Class<? extends ConverterInterface> javaType) {
    this.code = code;
    this.javaTypeConverter = javaType;
    this.protoKindCase = protoKindCase;
  }
}
