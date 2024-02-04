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
import com.google.cloud.spanner.Value;
import com.google.protobuf.Value.KindCase;

/**
 * Stateless interface for converting between various encodings used for a data type
 * @param <T>
 */
// TODO(gunjj@) Check null-behaviour consistency with existing implementation.
//  E.g. Int64Impl throws an exception if value.getInt64() is invoked on an Int64Impl where isNull was set as true
public interface ConverterInterface <T> {

  // TODO(gunjj@) Is protoKindCase needed, since this should be available in the proto for a well-formed object?
  //   Note that this logic is currently in com.google.cloud.spanner.AbstractResultSet::decodeValue method
  public T toObject(com.google.protobuf.Value proto, KindCase protoKindCase);

  public com.google.protobuf.Value toProto(T value); // TODO(gunjj@) Should this take Value parameter instead? Do we need Type.Code, or this implicit in the ConverterInterface implementation?

  /**
   * Returns a string representation of the value. This will work even if for unrecognized types.
   *
   * @param value
   * @param isNull
   * @return String representation of the proto value
   */
  public String getAsString(T value, boolean isNull); // TODO(gunjj@) Should we accept a StringBuilder param?

  public String toString(T value);

  // TODO(gunjj@) Should we implement for lists too?
  // public ImmutableList<String> getAsStringlist(Object value);

  int valueHash(T value);

  boolean equals(Type fieldType, T value, Value other);

  // TODO(gunjj@) Do we need to implement ValueBinder helper methods, to bind the appropriate values - setInt64, setJson etc?
}
