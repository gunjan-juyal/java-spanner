<#ftl output_format="plainText">

<#-- TODO(gunjj@) Consider extending this to add boilerplate in if-else and switch blocks -->

#AutogenSectionStart, TargetFile=main/java/com/google/cloud/spanner/AbstractResultSet.java

#AutogenChunkId=imports

<#if javaTypeImport??>
import ${javaTypeImport};
</#if>

#AutogenChunkId=GrpcStruct

    @Override
    protected ${javaType} get${javaType}Internal(int columnIndex) {
      return (${javaType}) rowData.get(columnIndex);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected List<${javaType}> get${javaType}ListInternal(int columnIndex) {
      return (List<${javaType}>) rowData.get(columnIndex);
    }

#AutogenChunkId=body

  @Override
  protected ${javaType} get${javaType}Internal(int columnIndex) {
    return currRow().get${javaType}Internal(columnIndex);
  }

  @Override
  protected List<${javaType}> get${javaType}ListInternal(int columnIndex) {
    return currRow().get${javaType}ListInternal(columnIndex);
  }

#AutogenSectionEnd
<#-- ---- -->

#AutogenSectionStart, TargetFile=main/java/com/google/cloud/spanner/AbstractStructReader.java

#AutogenChunkId=imports

<#if javaTypeImport??>
import ${javaTypeImport};
</#if>

#AutogenChunkId=body

  protected abstract ${javaType} get${javaType}Internal(int columnIndex);

  protected abstract List<${javaType}> get${javaType}ListInternal(int columnIndex);

  @Override
  public ${javaType} get${javaType}(int columnIndex) {
    checkNonNullOfType(columnIndex, Type.${spannerType}(), columnIndex);
    return get${javaType}Internal(columnIndex);
  }

  @Override
  public ${javaType} get${javaType}(String columnName) {
    int columnIndex = getColumnIndex(columnName);
    checkNonNullOfType(columnIndex, Type.${spannerType}(), columnName);
    return get${javaType}Internal(columnIndex);
  }

  @Override
  public List<${javaType}> get${javaType}List(int columnIndex) {
    checkNonNullOfType(columnIndex, Type.array(Type.${spannerType}()), columnIndex);
    return get${javaType}ListInternal(columnIndex);
  }

  @Override
  public List<${javaType}> get${javaType}List(String columnName) {
    int columnIndex = getColumnIndex(columnName);
    checkNonNullOfType(columnIndex, Type.array(Type.${spannerType}()), columnName);
    return get${javaType}ListInternal(columnIndex);
  }

#AutogenSectionEnd
<#-- ---- -->

#AutogenSectionStart, TargetFile=main/java/com/google/cloud/spanner/ForwardingStructReader.java

#AutogenChunkId=imports

<#if javaTypeImport??>
import ${javaTypeImport};
</#if>

#AutogenChunkId=body

  @Override
  public ${javaType} get${javaType}(int columnIndex) {
    return delegate.get().get${javaType}(columnIndex);
  }

  @Override
  public ${javaType} get${javaType}(String columnName) {
    return delegate.get().get${javaType}(columnName);
  }

  @Override
  public List<${javaType}> get${javaType}List(int columnIndex) {
    return delegate.get().get${javaType}List(columnIndex);
  }

  @Override
  public List<${javaType}> get${javaType}List(String columnName) {
    return delegate.get().get${javaType}List(columnName);
  }

#AutogenSectionEnd
<#-- ---- -->

#AutogenSectionStart, TargetFile=main/java/com/google/cloud/spanner/Key.java

#AutogenChunkId=imports

<#if javaTypeImport??>
import ${javaTypeImport};
</#if>

#AutogenChunkId=body

    /** Appends a {@code ${spannerType?upper_case}} value to the key. */
    public Builder append(@Nullable ${javaType} value) {
      buffer.add(value);
      return this;
    }

#AutogenSectionEnd
<#-- ---- -->

#AutogenSectionStart, TargetFile=main/java/com/google/cloud/spanner/ResultSets.java

#AutogenChunkId=imports

<#if javaTypeImport??>
import ${javaTypeImport};
</#if>

#AutogenChunkId=body

    @Override
    public ${javaType} get${javaType}(int columnIndex) {
      return getCurrentRowAsStruct().get${javaType}(columnIndex);
    }

    @Override
    public ${javaType} get${javaType}(String columnName) {
      return getCurrentRowAsStruct().get${javaType}(columnName);
    }

    @Override
    public List<${javaType}> get${javaType}List(int columnIndex) {
      return getCurrentRowAsStruct().get${javaType}List(columnIndex);
    }

  @Override
  public List<${javaType}> get${javaType}List(String columnName) {
    return getCurrentRowAsStruct().get${javaType}List(columnName);
  }

#AutogenSectionEnd
<#-- ---- -->

#AutogenSectionStart, TargetFile=main/java/com/google/cloud/spanner/Struct.java

#AutogenChunkId=imports

<#if javaTypeImport??>
import ${javaTypeImport};
</#if>

#AutogenChunkId=body

    @Override
    protected ${javaType} get${javaType}Internal(int columnIndex) {
      return values.get(columnIndex).get${spannerType?cap_first}();
    }

    @Override
    protected List<${javaType}> get${javaType}ListInternal(int columnIndex) {
      return values.get(columnIndex).get${spannerType?cap_first}Array();
    }

#AutogenSectionEnd
<#-- ---- -->

#AutogenSectionStart, TargetFile=main/java/com/google/cloud/spanner/StructReader.java

#AutogenChunkId=imports

<#if javaTypeImport??>
import ${javaTypeImport};
</#if>

#AutogenChunkId=body

  /** Returns the value of a non-{@code NULL} column with type {@link Type#${spannerType}()}. */
  ${javaType} get${javaType}(int columnIndex);

  /** Returns the value of a non-{@code NULL} column with type {@link Type#${spannerType}()}. */
  ${javaType} get${javaType}(String columnName);

  /**
   * Returns the value of a non-{@code NULL} column with type {@code Type.array(Type.${spannerType}())}.
   */
  List<${javaType}> get${javaType}List(int columnIndex);

  /**
   * Returns the value of a non-{@code NULL} column with type {@code Type.array(Type.${spannerType}())}.
   */
  List<${javaType}> get${javaType}List(String columnName);

#AutogenSectionEnd
<#-- ---- -->

#AutogenSectionStart, TargetFile=main/java/com/google/cloud/spanner/Type.java

#AutogenChunkId=private_statics

private static final Type TYPE_${spannerType?upper_case} = new Type(Code.${spannerType?upper_case}, null, null);
private static final Type TYPE_ARRAY_${spannerType?upper_case} = new Type(Code.ARRAY, TYPE_${spannerType?upper_case}, null);

#AutogenChunkId=public_statics

  /** Returns the descriptor for the {@code ${spannerType?upper_case}} type. */
  public static Type ${spannerType}() {
    return TYPE_${spannerType?upper_case};
  }

#AutogenChunkId=enum
    ${spannerType?upper_case}(TypeCode.${spannerType?upper_case}),

#AutogenSectionEnd
<#-- ---- -->

#AutogenSectionStart, TargetFile=main/java/com/google/cloud/spanner/Value.java

#AutogenChunkId=imports

<#if javaTypeImport??>
import ${javaTypeImport};
</#if>

#AutogenChunkId=public_statics

// TODO(Auto-generation): Consider if we also need to manually generate a method taking a
//  primitive Java type as input. E.g. float64(Double) -> float64(double);
//  latter forms are not auto-generated in this template.

  /**
   * Returns a {@code ${spannerType?upper_case}} value.
   *
   * @param v the value, which may be null
   */
  public static Value ${spannerType}(@Nullable ${javaType} v) {
    return new GenericValue<${javaType}>(v == null, Type.${spannerType}(), v);
  }

  /**
   * Returns an {@code ARRAY<${spannerType?upper_case}>} value.
   *
   * @param v the source of element values. This may be {@code null} to produce a value for which
   *     {@code isNull()} is {@code true}. Individual elements may also be {@code null}.
   */
  public static Value ${spannerType}Array(@Nullable Iterable<${javaType}> v) {
    return new ${spannerType?cap_first}ArrayImpl(v == null, v == null ? null : immutableCopyOf(v));
  }

#AutogenChunkId=public

  /**
   * Returns the value of a {@code ${spannerType?upper_case}}-typed instance.
   *
   * @throws IllegalStateException if {@code isNull()} or the value is not of the expected type
   */
  public abstract ${javaType} get${spannerType?cap_first}();

  /**
   * Returns the value of an {@code ARRAY<${spannerType?upper_case}>}-typed instance. While the returned list itself
   * will never be {@code null}, elements of that list may be null.
   *
   * @throws IllegalStateException if {@code isNull()} or the value is not of the expected type
   */
  public abstract List<${javaType}> get${spannerType?cap_first}Array();

#AutogenChunkId=AbstractValue

    @Override
    public ${javaType} get${spannerType?cap_first}() {
      throw defaultGetter(Type.${spannerType}());
    }

    @Override
    public List<${javaType}> get${spannerType?cap_first}Array() {
      throw defaultGetter(Type.array(Type.${spannerType}()));
    }

#AutogenChunkId=GenericValue

    @Override
    public ${javaType} get${spannerType?cap_first}() {
      checkType(Type.${spannerType}());
      checkNotNull();
      return (${javaType}) value;
    }

#AutogenChunkId=typeArrayImpl

// TODO(Auto-generation): This template provides a default implementation of
//  backing an array of a new type. This may need to be customized based on the
//  type-specific requirements. A manual review is needed to verify if the
//  implementation fits the requirements.

  private static class ${spannerType?cap_first}ArrayImpl extends AbstractArrayValue<${javaType}> {

    private ${spannerType?cap_first}ArrayImpl(boolean isNull, @Nullable List<${javaType}> values) {
      super(isNull, Type.${spannerType}(), values);
    }

    @Override
    public List<${javaType}> get${spannerType?cap_first}Array() {
      checkNotNull();
      return value;
    }

    @Override
    void appendElement(StringBuilder b, ${javaType} element) {
      b.append(element);
    }
  }

#AutogenSectionEnd
<#-- ---- -->

#AutogenSectionStart, TargetFile=main/java/com/google/cloud/spanner/ValueBinder.java

#AutogenChunkId=imports

<#if javaTypeImport??>
import ${javaTypeImport};
</#if>

#AutogenChunkId=body

  /** Binds to {@code Value.${spannerType}(value)} */
  public R to(${javaType} value) {
    return handle(Value.${spannerType}(value));
  }

  /** Binds to {@code Value.${spannerType}Array(values)} */
  public R to${spannerType?cap_first}Array(@Nullable Iterable<${javaType}> values) {
    return handle(Value.${spannerType}Array(values));
  }

#AutogenSectionEnd
<#-- ---- -->

#AutogenSectionStart, TargetFile=main/java/com/google/cloud/spanner/connection/ChecksumResultSet.java

#AutogenChunkId=imports

<#if javaTypeImport??>
import ${javaTypeImport};
</#if>

#AutogenSectionEnd
<#-- ---- -->

#AutogenSectionStart, TargetFile=main/java/com/google/cloud/spanner/connection/DirectExecuteResultSet.java

#AutogenChunkId=imports

<#if javaTypeImport??>
import ${javaTypeImport};
</#if>

#AutogenChunkId=body

  @Override
  public ${javaType} get${javaType}(String columnName) {
    Preconditions.checkState(nextCalledByClient, MISSING_NEXT_CALL);
    return delegate.get${javaType}(columnName);
  }

  @Override
  public ${javaType} get${javaType}(int columnIndex) {
    Preconditions.checkState(nextCalledByClient, MISSING_NEXT_CALL);
    return delegate.get${javaType}(columnIndex);
  }

  @Override
  public List<${javaType}> get${javaType}List(int columnIndex) {
    Preconditions.checkState(nextCalledByClient, MISSING_NEXT_CALL);
    return delegate.get${javaType}List(columnIndex);
  }

  @Override
  public List<${javaType}> get${javaType}List(String columnName) {
    Preconditions.checkState(nextCalledByClient, MISSING_NEXT_CALL);
    return delegate.get${javaType}List(columnName);
  }

#AutogenSectionEnd
<#-- ---- -->

#AutogenSectionStart, TargetFile=main/java/com/google/cloud/spanner/connection/ReplaceableForwardingResultSet.java

#AutogenChunkId=imports

<#if javaTypeImport??>
import ${javaTypeImport};
</#if>

#AutogenChunkId=body

  @Override
  public ${javaType} get${javaType}(int columnIndex) {
    checkClosed();
    return delegate.get${javaType}(columnIndex);
  }

  @Override
  public ${javaType} get${javaType}(String columnName) {
    checkClosed();
    return delegate.get${javaType}(columnName);
  }

  @Override
  public List<${javaType}> get${javaType}List(int columnIndex) {
    checkClosed();
    return delegate.get${javaType}List(columnIndex);
  }

  @Override
  public List<${javaType}> get${javaType}List(String columnName) {
    checkClosed();
    return delegate.get${javaType}List(columnName);
  }

#AutogenSectionEnd
<#-- ---- -->

#AutogenSectionStart, TargetFile=test/java/com/google/cloud/spanner/AbstractStructReaderTypesTest.java

#AutogenChunkId=imports

<#if javaTypeImport??>
import ${javaTypeImport};
</#if>

#AutogenChunkId=body

    @Override
    protected ${javaType} get${javaType}Internal(int columnIndex) {
      return null;
    }

    @Override
    protected List<${javaType}> get${javaType}ListInternal(int columnIndex) {
      return null;
    }

#AutogenSectionEnd
<#-- ---- -->
