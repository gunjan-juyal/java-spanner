package com.google.cloud.spanner;


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
    } else if (javaTypeConverter.equals(StringConverter.class)) {
      return StringConverter.getInstance();
    } else {
      throw new RuntimeException("Not implemented");
    }
  }
}
