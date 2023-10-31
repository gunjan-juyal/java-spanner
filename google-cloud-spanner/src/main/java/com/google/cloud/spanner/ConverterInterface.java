package com.google.cloud.spanner;

import com.google.common.collect.ImmutableList;
import com.google.protobuf.Value.KindCase;

// TODO(gunjj@) Check null-behaviour consistency with existing implementation.
//  E.g. Int64Impl throws an exception if value.getInt64() is invoked on an Int64Impl where isNull was set as true
public interface ConverterInterface <T> {

  // TODO(gunjj@) Is protoKindCase needed, since this should be available in the proto for a well-formed object?
  //   Note that this logic is currently in com.google.cloud.spanner.AbstractResultSet::decodeValue method
  public T toObject(com.google.protobuf.Value proto, KindCase protoKindCase);

  public com.google.protobuf.Value toProto(T value); // TODO(gunjj@) Should this take Value parameter instead? Do we need Type.Code, or this implicit in the ConverterInterface implementation?

  public String toString(T value);

  /**
   * Returns a string representation of the value. This will work even if for unrecognized types.
   * @param proto Protobuf value
   * @param protoKindCase Wire-serialization type for the proto value
   * @return String representation of the proto value
   */
  public String getAsString(com.google.protobuf.Value proto, KindCase protoKindCase); // TODO(gunjj@) Should we accept a StringBuilder param?

  // TODO(gunjj@) Should we implement for lists too?
  // public ImmutableList<String> getAsStringlist(Object value);

  int valueHash(T value);

  boolean equals(T value, Value other);

  // TODO(gunjj@) Do we need to implement ValueBinder helper methods, to bind the appropriate values - setInt64, setJson etc?
}
