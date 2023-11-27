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

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Standalone utility to auto-generate boilerplate necessary to add a data type to the library.
 * Usage: Modify the input variables as per the new data type, run generateAndReplaceCode() and the
 *   utility would replace the source code in the local workspace. It needs to be reviewed, cleaned-
 *   up, completed with non-auto-generatable code and submitted for review by a human reviewer.
 *
 *   The code replacement happens based on special tags in comments in the source files, so the
 *   placeholder comments must not be removed during review of auto-generated code.
 *
 *   It is recommended to commit the auto-generatable code separately from human-generated code for
 *   easier identification during the review cycle.
 */
public class TypesCodeGenerator {

  public static void main(String[] args) throws IOException, TemplateException {
    new TypesCodeGenerator().generateAndReplaceCode();
  }

  public void generateAndReplaceCode() {
    // 1. Generate code based from the template and runtime variables
    String templatedCode = null;
    try {
      // Create data model. TODO(gunjj@) Move these to a config file. Current code runs only for a
      //  single type; consider looping over all existing generatble-types to add them together, in
      //  case there are changes in the template (but do we want to auto-generate already-committed
      //  code?)
      Map<String, Object> data = new HashMap<>();
      data.put("javaType", "BigDecimal");
      data.put("javaTypeImport", "java.math.BigDecimal");
      data.put("spannerType", "numeric");
      templatedCode = generateTemplatedCode("template.ftl", data);
    } catch (IOException e) {
      System.err.println("Error in I/O during template processing: " + e.getMessage());
      throw new RuntimeException(e);
    } catch (TemplateException e) {
      System.err.println("Error during auto-generation template processing: " + e.getMessage());
      throw new RuntimeException(e);
    }

    // 2.1. Tokenize the generated code by start/end autogen section tokens. For each section:
    new TemplatedCode().parseTemplatedCode(templatedCode);

    // 2.2. Lookup the file name, try open file in the source path. Fail with error if not available
    // 2.3. Tokenize chunks within the section. For each chunk, search for comment-boundaries within the
    //  source file, and replace the contents with autogen content

    System.out.println(templatedCode);
  }

  private String generateTemplatedCode(String templateName, Map<String, Object> data) throws IOException, TemplateException {
    // Create a configuration object
    Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);

    // Load template from file
    cfg.setClassForTemplateLoading(TypesCodeGenerator.class, ".");
    Template template = cfg.getTemplate(templateName);

    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    OutputStreamWriter out = new OutputStreamWriter(bos);
    template.process(data, out);
    out.flush();
    return bos.toString("UTF-8");
  }
}