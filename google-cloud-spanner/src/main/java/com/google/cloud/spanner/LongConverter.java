package com.google.cloud.spanner;

import com.google.cloud.spanner.Type.Code;
import com.google.common.base.Preconditions;
import com.google.protobuf.Value.KindCase;

/**
 * Converts language-specific Long values from and to different encodings - wire-format, hash, string etc
 */
public class LongConverter implements ConverterInterface<Long> {

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
  public String getAsString(com.google.protobuf.Value proto, KindCase protoKindCase) {
    // TODO(gunjj@) This calls valueToString() of the type-specific classes in most cases, or
    //  special cases in other (e.g. JSON/STRING where a null string results in value "NULL")
    //  WIP - Review the usage from GenericValue class - it has a `<T> value` instance (e.g. Long),
    //  hence, we should usually take that as a param, since interaction is like:
    //  ConverterInterface <-> TypeConversionDelegate <-> Value / AbstractValue
    return proto.getStringValue();
  }

  @Override
  public int valueHash(Long value) {
    return value.hashCode();
  }

  // TODO(gunjj@) Much of this method may be generic - consider reusing for various ConverterInterfaces
  @Override
  public boolean equals(Long value, Value other) {
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
