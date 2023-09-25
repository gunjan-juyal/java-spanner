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
import java.util.List;

public final class TypeHelper {

  // TODO(gunjj) row.get<Type>() is used extensively in tests, so can this method simplify that?
  //  The internal implementation src/main/java/com/google/cloud/spanner/AbstractResultSet.java::getDoubleInternal
  //  Also, consider moving this to Value class since it is addressing similar concerns
  public static Object extractPrimitiveType(Value value, Code code) {
    switch (code) {
      // TODO(gunjj) Verify null-checking - it is there in older row.getType methods, and is most
      //  likely there in the value.getType() methods also - need to validate.
      case BOOL:
        return value.getBool();
      case BYTES:
        return value.getBytes();
      case DATE:
        return value.getDate();
      case FLOAT64:
        return value.getFloat64();
      case NUMERIC:
        return value.getNumeric();
      case PG_NUMERIC:
        return value.getString();
      case INT64:
        return value.getInt64();
      case STRING:
        return value.getString();
      case JSON:
        return value.getJson();
      case PG_JSONB:
        return value.getPgJsonb();
      case TIMESTAMP:
        return value.getTimestamp();
      default:
        throw new IllegalArgumentException(String.format("unsupported row type: %s", code));
    }
  }

  public static List<?> extractArrayOfPrimitiveType(final Struct row, int columnIndex,
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
      case NUMERIC:
        return row.getBigDecimalList(columnIndex);
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
      default:
        throw new IllegalArgumentException(String.format("unsupported array element type: %s", arrayElementType));
    }
  }
}
