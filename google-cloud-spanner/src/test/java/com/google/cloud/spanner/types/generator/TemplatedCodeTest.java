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

package com.google.cloud.spanner.types.generator;

import java.text.ParseException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;


public class TemplatedCodeTest {

  private static final String SAMPLE_SECTION_1_START_STRING =
      "#AutogenSectionStart, TargetFile=com/google/cloud/spanner/AbstractResultSet.java\n\n";

  private static final String SAMPLE_SECTION_2_START_STRING =
      "#AutogenSectionStart, TargetFile=com/google/cloud/spanner/AbstractStructReader.java\n\n";

  private static final String SAMPLE_SECTION_END_STRING = "#AutogenSectionEnd\n";

  private static final String SAMPLE_CHUNK_1 = "#AutogenChunkId=imports\n\n"
          + "import java.math.BigDecimal;\n";

  private static final String SAMPLE_CHUNK_2 = "#AutogenChunkId=body\n\n"
      + "  @Override\n"
      + "  protected BigDecimal getBigDecimalInternal(int columnIndex) {\n"
      + "    return currRow().getBigDecimalInternal(columnIndex);\n"
      + "  }\n\n"
      + "  @Override\n"
      + "  protected List<BigDecimal> getBigDecimalListInternal(int columnIndex) {\n"
      + "    return currRow().getBigDecimalListInternal(columnIndex);\n"
      + "  }\n";

  private static final String SAMPLE_TEMPLATE_STRING_SHORT =
      SAMPLE_SECTION_1_START_STRING + SAMPLE_CHUNK_1 + SAMPLE_CHUNK_2 + SAMPLE_SECTION_END_STRING;

  private static final String SAMPLE_TEMPLATE_STRING_LONG =
      SAMPLE_SECTION_1_START_STRING + SAMPLE_CHUNK_1 + SAMPLE_CHUNK_2 + SAMPLE_SECTION_END_STRING
      + SAMPLE_SECTION_2_START_STRING + SAMPLE_CHUNK_1 + SAMPLE_SECTION_END_STRING;

  private static final String SAMPLE_SECTION_BODY_WITHOUT_FILENAME =
      "#AutogenSectionStart\n\n" + SAMPLE_CHUNK_1;

  private TemplatedCode templatedCode;

  @Before
  public void setUp() {
    templatedCode = new TemplatedCode();
  }

  @Test
  public void nextTagOffsetTest() {
    System.out.println(SAMPLE_TEMPLATE_STRING_SHORT);
    assertEquals(0, templatedCode.nextTagOffset(SAMPLE_TEMPLATE_STRING_SHORT, 0,
        "#AutogenSectionStart"));
    assertEquals(197, templatedCode.nextTagOffset(SAMPLE_TEMPLATE_STRING_SHORT, 1,
        "#AutogenSectionStart"));
    assertEquals(82, templatedCode.nextTagOffset(SAMPLE_TEMPLATE_STRING_SHORT, 0,
        "#AutogenChunkId"));
  }

  @Test
  public void extractFileNameFromSectionStartTagTest() throws ParseException {
    assertEquals("com/google/cloud/spanner/AbstractResultSet.java", templatedCode.extractFileNameFromSectionStartTag(SAMPLE_SECTION_1_START_STRING));
  }

  @Test
  public void extractFileNameFromInvalidSectionStartTagTest() throws ParseException {
    assertThrows(ParseException.class, () -> templatedCode.extractFileNameFromSectionStartTag(SAMPLE_SECTION_BODY_WITHOUT_FILENAME));
  }

  @Test
  public void extractChuknIdFromChunkStartTest() throws ParseException {
    System.out.println(SAMPLE_TEMPLATE_STRING_SHORT);
    System.out.println(SAMPLE_TEMPLATE_STRING_LONG);
    assertEquals("imports", templatedCode.extractChunkIdFromChunkStart(SAMPLE_TEMPLATE_STRING_SHORT, 82));
    assertEquals("body", templatedCode.extractChunkIdFromChunkStart(SAMPLE_TEMPLATE_STRING_SHORT, 136));
  }
}
