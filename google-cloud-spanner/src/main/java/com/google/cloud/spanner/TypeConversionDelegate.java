package com.google.cloud.spanner;


import static com.google.cloud.spanner.SpannerExceptionFactory.newSpannerException;

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
      throw newSpannerException(
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
    // Step-1: Get mapper for this type
    ConverterInterface converter = getConverterInterface(fieldType);

    // Step-2: Extract value
    return converter.toProto(value);
  }

  public <R> int valueHash(Type fieldType, R value) {
    // Step-1: Get mapper for this type
    ConverterInterface converter = getConverterInterface(fieldType);

    // Step-2: Extract value
    return converter.valueHash(value);
  }

  public <R> boolean equals(Type fieldType, R value, com.google.cloud.spanner.Value other) {
    return getConverterInterface(fieldType).equals(value, other);
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

  public <R> String toString(Type fieldType, R value) {
    return getConverterInterface(fieldType).toString(value);
  }
}
