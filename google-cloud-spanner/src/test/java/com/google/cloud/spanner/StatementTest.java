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

import static com.google.common.testing.SerializableTester.reserializeAndAssert;
import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import com.google.cloud.ByteArray;
import com.google.cloud.spanner.Type.Code;
import com.google.common.collect.ImmutableMap;
import com.google.common.testing.EqualsTester;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** Unit tests for {@link com.google.cloud.spanner.Statement}. */
@RunWith(JUnit4.class)
public class StatementTest {

  @Test
  public void basic() {
    String sql = "SELECT 1";
    Statement stmt = Statement.of(sql);
    assertThat(stmt.getSql()).isEqualTo(sql);
    assertThat(stmt.getParameters()).isEmpty();
    assertThat(stmt.toString()).isEqualTo(sql);
    reserializeAndAssert(stmt);
  }

  @Test
  public void serialization() {
    Statement stmt =
        Statement.newBuilder("SELECT * FROM table WHERE ")
            .append("bool_field = @bool_field ")
            .bind("bool_field")
            .to(true)
            .append("long_field = @long_field ")
            .bind("long_field")
            .to(1L)
            .append("float_field = @float_field ")
            .bind("float_field")
            .to(1.)
            .append("string_field = @string_field ")
            .bind("string_field")
            .to("abc")
            .append("bytes_field = @bytes_field ")
            .bind("bytes_field")
            .to(ByteArray.fromBase64("abcd"))
            .bind("untyped_null_field")
            .to((Value) null)
            .build();
    reserializeAndAssert(stmt);
  }

  /**
   * Tests serialization for Bool, Float64 and JSON
   */
  @Test
  public void serializationUsingGenericMethods() {
    Statement stmt =
        Statement.newBuilder("SELECT * FROM table WHERE ")
            .append("bool_field = @bool_field ")
            .bind("bool_field")
            .to(Code.BOOL, Boolean.TRUE)
            .append("long_field = @long_field ")
            .bind("long_field")
            .to(Code.INT64, Long.valueOf(1L))
            .append("another_long_field = @another_long_field ")
            .bind("another_long_field")
            .to(Code.INT64, 1L)
            .append("float_field = @float_field ")
            .bind("float_field")
            .to(Code.FLOAT64, Double.valueOf(40.1F))
            .append("another_float_field = @another_float_field ")
            .bind("another_float_field")
            .to(Code.FLOAT64, 40.1D)
            .append("json_field = @json_field ")
            .bind("json_field")
            .to(Code.JSON, "{\"color\":\"red\",\"value\":\"#f00\"}")
            .bind("untyped_null_field")
            .to((Value) null)
            .build();
    reserializeAndAssert(stmt);

    // Negative testing of invalid call of generic method
    // TODO(gunjj@): Get this reviewed - is this a risky API, especially in Java's strongly-typed world?
    assertThrows(ClassCastException.class,
        () -> Statement.newBuilder("SELECT * FROM table WHERE ")
            .append("float_field = @float_field ")
            .bind("float_field")
            .to(Code.FLOAT64, Float.valueOf(40.1F))
            .build());

    assertThrows(ClassCastException.class,
        () -> Statement.newBuilder("SELECT * FROM table WHERE ")
            .append("float_field = @float_field ")
            .bind("float_field")
            .to(Code.FLOAT64, 40.1F)
            .build());
  }

  @Test
  public void append() {
    Statement stmt =
        Statement.newBuilder("SELECT Name FROM Users")
            .append(" WHERE Id = @id")
            .bind("id")
            .to(1234)
            .append(" AND Status = @status")
            .bind("status")
            .to("ACTIVE")
            .build();
    String expectedSql = "SELECT Name FROM Users WHERE Id = @id AND Status = @status";
    assertThat(stmt.getSql()).isEqualTo(expectedSql);
    assertThat(stmt.hasBinding("id")).isTrue();
    assertThat(stmt.hasBinding("status")).isTrue();
    assertThat(stmt.getParameters())
        .containsExactlyEntriesIn(
            ImmutableMap.of("id", Value.int64(1234), "status", Value.string("ACTIVE")));
    assertThat(stmt.toString()).startsWith(expectedSql);
    assertThat(stmt.toString()).contains("id: 1234");
    assertThat(stmt.toString()).contains("status: ACTIVE");
  }

  @Test
  public void appendUsingGenericMethods() {
    Statement stmt =
        Statement.newBuilder("SELECT Name FROM Users")
            .append(" WHERE Id = @double_id")
            .bind("double_id")
            .to(Code.FLOAT64, Double.valueOf(1.1D))
            .append(" AND Status = @bool_status")
            .bind("bool_status")
            .to(Code.BOOL, Boolean.TRUE)
            .build();
    String expectedSql = "SELECT Name FROM Users WHERE Id = @double_id AND Status = @bool_status";
    assertThat(stmt.getSql()).isEqualTo(expectedSql);
    assertThat(stmt.hasBinding("double_id")).isTrue();
    assertThat(stmt.hasBinding("bool_status")).isTrue();
    assertThat(stmt.getParameters())
        .containsExactlyEntriesIn(
            ImmutableMap.of("double_id", Value.primitiveOfType(Code.FLOAT64, 1.1D),
                "bool_status", Value.primitiveOfType(Code.BOOL, true)));
    assertThat(stmt.toString()).startsWith(expectedSql);
    assertThat(stmt.toString()).contains("double_id: 1.1");
    assertThat(stmt.toString()).contains("bool_status: true");
  }

  @Test
  public void bindReplacement() {
    String sql = "SELECT Name FROM Users WHERE Id = @id";
    Statement stmt = Statement.newBuilder(sql).bind("id").to(1).bind("id").to(2).build();
    assertThat(stmt.hasBinding("id")).isTrue();
    assertThat(stmt.getSql()).isEqualTo(sql);
    assertThat(stmt.getParameters()).isEqualTo(ImmutableMap.of("id", Value.int64(2)));
    assertThat(stmt.toString()).isEqualTo(sql + " {id: 2}");
  }

  @Test
  public void incompleteBinding() {
    Statement.Builder builder = Statement.newBuilder("SELECT @v");
    builder.bind("v");
    IllegalStateException e = assertThrows(IllegalStateException.class, () -> builder.build());
    assertNotNull(e.getMessage());
  }

  @Test
  public void bindingInProgress() {
    Statement.Builder builder = Statement.newBuilder("SELECT @v");
    builder.bind("v");
    IllegalStateException e = assertThrows(IllegalStateException.class, () -> builder.bind("y"));
    assertNotNull(e.getMessage());
  }

  @Test
  public void alreadyBound() {
    ValueBinder<Statement.Builder> binder = Statement.newBuilder("SELECT @v").bind("v");
    binder.to("abc");
    IllegalStateException e = assertThrows(IllegalStateException.class, () -> binder.to("xyz"));
    assertNotNull(e.getMessage());
  }

  @Test
  public void bindCommitTimestampFails() {
    ValueBinder<Statement.Builder> binder = Statement.newBuilder("SELECT @v").bind("v");
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> binder.to(Value.COMMIT_TIMESTAMP));
    assertNotNull(e.getMessage());
  }

  @Test
  public void gettersAreSnapshot() {
    Statement stmt =
        Statement.newBuilder("SELECT Name FROM Users WHERE Id = @id")
            .append(" AND Status = @status")
            .bind("status")
            .to("ACTIVE")
            .bind("id")
            .to(1234)
            .bind("status")
            .to("ACTIVE")
            .build();
    assertThat(stmt.getSql())
        .isEqualTo("SELECT Name FROM Users WHERE Id = @id AND Status = @status");
    assertThat(stmt.getParameters())
        .isEqualTo(ImmutableMap.of("id", Value.int64(1234), "status", Value.string("ACTIVE")));
  }

  @Test
  public void equalsAndHashCode() {
    EqualsTester tester = new EqualsTester();
    tester.addEqualityGroup(
        Statement.of("SELECT 1"),
        Statement.of("SELECT 1"),
        Statement.newBuilder("SELECT ").append("1").build());
    tester.addEqualityGroup(Statement.of("SELECT 2"));
    // Note that some of the following are incomplete bindings: they would fail if executed.
    tester.addEqualityGroup(
        Statement.newBuilder("SELECT @x, @y").bind("x").to(1).build(),
        Statement.newBuilder("SELECT @x, @y").bind("x").to(1).build());
    tester.addEqualityGroup(Statement.newBuilder("SELECT @x, @y").bind("x").to("1").build());
    tester.addEqualityGroup(Statement.newBuilder("SELECT @x, @y").bind("x").to(2).build());
    tester.addEqualityGroup(Statement.newBuilder("SELECT @x, @y").bind("y").to(2).build());
    tester.addEqualityGroup(
        Statement.newBuilder("SELECT @x, @y").bind("x").to(1).bind("y").to(2).build());
    tester.addEqualityGroup(
        Statement.newBuilder("SELECT @x, @y").bind("x").to((Value) null).build(),
        Statement.newBuilder("SELECT @x, @y").bind("x").to((Value) null).build());
    tester.testEquals();
  }
}
