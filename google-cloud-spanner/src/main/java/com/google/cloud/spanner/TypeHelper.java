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

package com.google.cloud.spanner;

import com.google.cloud.spanner.Type.Code;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

// TODO(gunjj) Consider moving these methods to Value class since they are addressing value conversions
public final class TypeHelper {

  // TODO(gunjj@) Challenge with this approach with given Value interfaces - v.get<Type>() are instance
  //  methods and hence I cannot create a static map of transformer functions. Creating a
  //  per-instance map would create extra data structures hence greater overheads
  //  E.g. Supplier<Boolean> getBool = value::getBool; Then: getBool.get();
  private static Map<Code, Supplier<?>> valueToPrimitiveTransformers(Value value) {
    return ImmutableMap.<Code, Supplier<?>>builder()
        .put(Code.BOOL, value::getBool)
        .put(Code.BYTES, value::getBytes)
        .put(Code.DATE, value::getDate)
        .put(Code.FLOAT64, value::getFloat64)
        .put(Code.PG_NUMERIC, value::getString)
        .put(Code.INT64, value::getInt64)
        .put(Code.STRING, value::getString)
        .put(Code.JSON, value::getJson)
        .put(Code.PG_JSONB, value::getPgJsonb)
        .put(Code.TIMESTAMP, value::getTimestamp)
        .put(Code.NUMERIC, value::getNumeric)
        .build();
  }

  /**
   * Helper method to transform a Value object to a primitive language-specific type
   *
   * @param value {@code Value} object to extract the type from
   * @param code Type code
   * @return Language-specific type object
   */
  public static Object toPrimitiveType(Value value, Code code) {
    // TODO(gunjj@): Note on cyclomatic complexity: Reduced from 14 to 4, but the delegate method complexity is 13 now.
    Map<Code, Supplier<?>> transformer = valueToPrimitiveTransformers(value);
    if (!transformer.containsKey(code)) {
      throw new IllegalArgumentException(String.format("unsupported row type: %s", code));
    }
    return transformer.get(code).get();
    // switch (code) {
    //   // TODO(gunjj) Verify null-checking - it is there in older row.getType methods, and is most
    //   //  likely there in the value.getType() methods also - need to validate.
    //   case BOOL:
    //     // TODO(gunjj@) Can we use Supplier<T> instead?
    //     return value.getBool();
    //   case BYTES:
    //     return value.getBytes();
    //   case DATE:
    //     return value.getDate();
    //   case FLOAT64:
    //     return value.getFloat64();
    //   case NUMERIC:
    //     return value.getNumeric();
    //   case PG_NUMERIC:
    //     return value.getString();
    //   case INT64:
    //     return value.getInt64();
    //   case STRING:
    //     return value.getString();
    //   case JSON:
    //     return value.getJson();
    //   case PG_JSONB:
    //     return value.getPgJsonb();
    //   case TIMESTAMP:
    //     return value.getTimestamp();
    //   default:
    //     throw new IllegalArgumentException(String.format("unsupported row type: %s", code));
    // }
  }

    /**
     * Helper method to transform a {@code Struct} representing an array column to a list of
     * primitive language-specific type
     *
     * @param row Row in a {@code ResultSet}
     * @param columnIndex Index of array column to be extracted
     * @param arrayElementType {@code Type.Code} of each element in the array
     * @return List of primitive object types extracted from the array column
     */
  public static List<?> toPrimitiveTypeList(final Struct row, int columnIndex,
      Code arrayElementType) {
    switch (arrayElementType) {
      case BOOL:
        return row.getBooleanList(columnIndex);
      case BYTES:
        return row.getBytesList(columnIndex);
      case DATE:
        return row.getDateList(columnIndex);
      case FLOAT64:
        return row.getDoubleList(columnIndex);
      case PG_NUMERIC:
      case STRING:
        return row.getStringList(columnIndex);
      case INT64:
        return row.getLongList(columnIndex);
      case JSON:
        return row.getJsonList(columnIndex);
      case PG_JSONB:
        return row.getPgJsonbList(columnIndex);
      case TIMESTAMP:
        return row.getTimestampList(columnIndex);
      case NUMERIC:
        return row.getBigDecimalList(columnIndex);
      default:
        throw new IllegalArgumentException(String.format("unsupported array element type: %s", arrayElementType));
    }
  }
}
