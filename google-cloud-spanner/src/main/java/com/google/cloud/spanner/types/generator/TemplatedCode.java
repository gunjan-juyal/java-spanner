/*
 * Copyright 2019 Google LLC
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

import com.google.cloud.spanner.types.generator.TemplatedCode.ParsedFileChunks.Chunk;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class TemplatedCode {

  static final int SECTION_LENGTH_IN_SECTION_PARSE_EXCEPTION = 100;
  private static final String SECTION_START_TAG = "#AutogenSectionStart";
  private static final String SECTION_END_TAG = "#AutogenSectionEnd";
  private static final String SECTION_FILENAME_TAG = "TargetFile";
  private static final String CHUNK_START_TAG = "#AutogenChunkId";
  private static final String CHUNK_ASSIGNMENT_OPERATOR = "=";

  public List<ParsedFileChunks> parseTemplatedCode(String templatedCode) throws ParseException {
    // Start from beginning of file, then subsequent block till there are no more start tags
    // Get next start tag and following end tag. Within each block:
    // Get next chunk start tag, till no more chunks remain
    // Parse the text within the chunk. Add to data structure: sections:{targetFile: chunks{chunkId: chunkText}}
    int currentIdx = 0;
    List<ParsedFileChunks> parsedFiles = new ArrayList<>();
    int nextSectionStartOffset = nextTagOffset(templatedCode, currentIdx, SECTION_START_TAG);
    while (nextSectionStartOffset >= 0) {
      System.out.format("Found section start tag at offset %d [%s...]\n", nextSectionStartOffset,
          getCroppedDebugString(templatedCode, nextSectionStartOffset));
      int nextSectionEndTagOffset = nextTagOffset(templatedCode, nextSectionStartOffset,
          SECTION_END_TAG);
      if (nextSectionEndTagOffset <= 0) {
        throw new ParseException(
            "Could not find closing tag for section starting at offset: " + getCroppedDebugString(
                templatedCode,
                nextSectionStartOffset), nextSectionStartOffset);
      }
      String sectionBody = templatedCode.substring(nextSectionStartOffset,
          nextSectionEndTagOffset + SECTION_END_TAG.length());
      parsedFiles.add(extractChunksFromSection(sectionBody));
      nextSectionStartOffset = nextTagOffset(templatedCode, nextSectionEndTagOffset, SECTION_START_TAG);
    }
    return parsedFiles;
  }

  private static String getCroppedDebugString(String string, int startOffset) {
    return startOffset + SECTION_LENGTH_IN_SECTION_PARSE_EXCEPTION > string.length()
        ? string.substring(startOffset) + "..."
        : string.substring(startOffset,
            startOffset + SECTION_LENGTH_IN_SECTION_PARSE_EXCEPTION) + "...";
  }

  int nextTagOffset(String text, int startIdx, String tag) {
    return text.indexOf(tag, startIdx);
  }

  private ParsedFileChunks extractChunksFromSection(String sectionBody) throws ParseException {
    String targetFile = extractFileNameFromSectionStartTag(sectionBody);
    ParsedFileChunks chunks = new ParsedFileChunks(targetFile);
    int currentIdx = 0;
    int chunkStartOffset = nextTagOffset(sectionBody, currentIdx, CHUNK_START_TAG);
    while (chunkStartOffset >= 0) {
      System.out.format("Found chunk start tag at offset %d [%s...]\n", chunkStartOffset,
          getCroppedDebugString(sectionBody, chunkStartOffset));
      String chunkId = extractChunkIdFromChunkStart(sectionBody, chunkStartOffset);
      int offset1 = nextTagOffset(sectionBody,
          chunkStartOffset + CHUNK_START_TAG.length(), CHUNK_START_TAG);
      int offset2 = nextTagOffset(sectionBody,
          chunkStartOffset + CHUNK_START_TAG.length(), SECTION_END_TAG);
      // System.out.format("Offset1: %d, Offset2: %d\n", offset1, offset2);
      int chunkEndOffset = (offset1 >=0 && offset1 < offset2) ? offset1 : offset2;
      chunks.add(
          new Chunk(chunkId, sectionBody.substring(chunkStartOffset, chunkEndOffset).trim()));
      chunkStartOffset = nextTagOffset(sectionBody, chunkEndOffset, CHUNK_START_TAG);
    }
    return chunks;
  }

  String extractChunkIdFromChunkStart(String sectionBody, int chunkStartOffset)
      throws ParseException {
    int lineEndIdx = sectionBody.indexOf("\n", chunkStartOffset);
    int chunkIdStartIdx = nextTagOffset(sectionBody, chunkStartOffset, CHUNK_ASSIGNMENT_OPERATOR);
    if (chunkIdStartIdx < 0 || chunkIdStartIdx > lineEndIdx) {
      throw new ParseException(
          "Could not find chunkId tag \"" + CHUNK_START_TAG + "\" in chunk-start line: "
              + getCroppedDebugString(sectionBody, chunkStartOffset), chunkIdStartIdx);
    }
    return sectionBody.substring(chunkIdStartIdx + 1, lineEndIdx).trim();
  }

  String extractFileNameFromSectionStartTag(String sectionBody) throws ParseException {
    int lineEndIdx = sectionBody.indexOf("\n");
    int filenameStartIdx = sectionBody.indexOf(SECTION_FILENAME_TAG);
    if (filenameStartIdx < 0 || filenameStartIdx > lineEndIdx) {
      throw new ParseException(
          "Could not find filename tag \"" + SECTION_FILENAME_TAG + "\" in section-start line: "
              + getCroppedDebugString(sectionBody, 0), -1);
    }
    return sectionBody.substring(filenameStartIdx + SECTION_FILENAME_TAG.length() + 1, lineEndIdx)
        .trim();
  }

  public static class ParsedFileChunks {

    private String targetFile;
    private List<Chunk> chunks;

    public ParsedFileChunks(String targetFile) {
      this.targetFile = targetFile;
      this.chunks = new ArrayList<>();
    }

    public String getTargetFile() {
      return targetFile;
    }

    public List<Chunk> getChunks() {
      return chunks;
    }

    public void add(Chunk chunk) {
      chunks.add(chunk);
    }

    public static class Chunk {

      private String chunkId;
      private String chunkText;

      public Chunk(String chunkId, String chunkText) {
        this.chunkId = chunkId;
        this.chunkText = chunkText;
      }

      public String getChunkId() {
        return chunkId;
      }

      public String getChunkText() {
        return chunkText;
      }
    }
  }
}
