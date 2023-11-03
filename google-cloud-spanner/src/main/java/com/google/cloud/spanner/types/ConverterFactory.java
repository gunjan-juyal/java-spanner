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

public class ConverterFactory {

  private ConverterFactory() {
  }

  private static ConverterFactory factory = new ConverterFactory();
  public static ConverterFactory getInstance() {
    return factory;
  }

  public ConverterInterface getConverter(Class<? extends ConverterInterface> javaTypeConverter) {
    if (javaTypeConverter.equals(LongConverter.class)) {
      return LongConverter.getInstance();
    } else if (javaTypeConverter.equals(DoubleConverter.class)) {
      return DoubleConverter.getInstance();
    } else if (javaTypeConverter.equals(StringConverter.class)) {
      return StringConverter.getInstance();
    } else {
      throw new RuntimeException("Not implemented");
    }
  }
}
