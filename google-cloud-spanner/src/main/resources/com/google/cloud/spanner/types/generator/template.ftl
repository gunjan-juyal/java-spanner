<#ftl output_format="plainText">

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

#AutogenChunkId=switchBlockDecodeValue

        case ${spannerType?upper_case}:
          return null; // TODO(Auto-generation): Add deserialization and parsing logic here

#AutogenChunkId=switchBlockDecodeArrayValue

        case ${spannerType?upper_case}: // TODO(Auto-generation): Review to see if more performant custom containers are needed instead of the default to avoid wrapper objects

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

#AutogenChunkId=ifBlockAppendObject
        else if (value instanceof ${javaType}) {
          buffer.add((${javaType})value); // TODO(Auto-generation): Review to see if any special handling is needed or if this block is made redundant by the default catch-all condition
        }

#AutogenChunkId=javadocGetParts
   *   <li>{@code ${spannerType?upper_case}} is represented by {@link ${javaType}}

#AutogenChunkId=ifBlockToProto
      else if (part instanceof ${javaType}) {
        builder.addValuesBuilder().setNullValue(NullValue.NULL_VALUE); // TODO(Auto-generation): Add serialization logic from language-specific object to gRPC wire-encoding-type here, replacing 'setNullValue'
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

#AutogenChunkId=switchBlockGetAsObject
      case ${spannerType?upper_case}:
        return get${javaType}Internal(columnIndex);

#AutogenChunkId=switchBlockInnerGetAsObject
          case ${spannerType?upper_case}:
            return get${javaType}ListInternal(columnIndex);

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

#AutogenChunkId=imports

<#if javaTypeImport??>
import ${javaTypeImport};
</#if>

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

#AutogenChunkId=switchBlockArray
      case ${spannerType?upper_case}:
        return TYPE_ARRAY_${spannerType?upper_case};

#AutogenChunkId=ifBlockFromProto
// TODO(Auto-generation): Review to verify that the new type ${spannerType?upper_case} is handled as a primitive type and no special handling is needed here

#AutogenChunkId=privateStaticsCodeToPrimitiveTypeMap
      .put(Code.${spannerType?upper_case}, TYPE_${spannerType?upper_case}) // TODO(Auto-generation): Review to verify that the new type can be handled as a primitive type

#AutogenChunkId=privateStaticsCodeToArrayTypeMap
      .put(Code.${spannerType?upper_case}, TYPE_ARRAY_${spannerType?upper_case})

#AutogenChunkId=privateStaticsSupportedPrimitiveTypeClasses
          , ${javaType}.class // TODO(Auto-generation): Review to verify that the new type can be handled as a primitive type

#AutogenChunkId=privateStaticsSupportedPrimitiveTypeCodes
          , Code.${spannerType?upper_case} // TODO(Auto-generation): Review to verify that the new type can be handled as a primitive type

#AutogenSectionEnd
<#-- ---- -->

#AutogenSectionStart, TargetFile=main/java/com/google/cloud/spanner/Value.java

#AutogenChunkId=imports

<#if javaTypeImport??>
import ${javaTypeImport};
</#if>

#AutogenChunkId=public_statics

// TODO(Auto-generation): Consider if we also need to manually generate a method
//  taking a primitive Java type as input. E.g. float64(Double) -> float64(double);
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

#AutogenChunkId=switchBlockPrimitivesToValue
        case ${spannerType?upper_case}:
          return ${spannerType}Array((Iterable<${javaType}>) values);

#AutogenChunkId=switchBlockGetValue
        case ${spannerType?upper_case}:
          return Value.${spannerType}(value.get${javaType}(fieldIndex));

#AutogenChunkId=switchBlockInnerGetValue
              case ${spannerType?upper_case}:
                return Value.${spannerType}Array(value.get${javaType}List(fieldIndex));

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

#AutogenChunkId=ifBlockFunnel

// TODO(Auto-generation): This template provides a default implementation of
//  computing checksum of a row. This may need to be customized for the new type
//  if it cannot be handled in the default way (i.e. as a primitive type). A
//  manual review is needed for this and similar methods (such as funnelArray).

#AutogenChunkId=switchBlockFunnelValue

// TODO(Auto-generation): This template provides a default implementation of
//  converting the type to checksum data. This may need to be customized for the
//  new type if it cannot be handled in the default way (i.e. converting to its
//  string representation). A manual review is needed.
          case ${spannerType?upper_case}:
            String stringRepresentation = ((${javaType}) value).toString();
            into.putInt(stringRepresentation.length());
            into.putUnencodedChars(stringRepresentation);
            break;

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
