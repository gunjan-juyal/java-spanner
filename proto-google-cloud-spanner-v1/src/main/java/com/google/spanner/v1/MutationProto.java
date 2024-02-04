/*
 * Copyright 2024 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/spanner/v1/mutation.proto

package com.google.spanner.v1;

public final class MutationProto {
  private MutationProto() {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
  }

  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_spanner_v1_Mutation_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_spanner_v1_Mutation_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_spanner_v1_Mutation_Write_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_spanner_v1_Mutation_Write_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_spanner_v1_Mutation_Delete_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_spanner_v1_Mutation_Delete_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = {
      "\n google/spanner/v1/mutation.proto\022\021goog"
          + "le.spanner.v1\032\037google/api/field_behavior"
          + ".proto\032\034google/protobuf/struct.proto\032\034go"
          + "ogle/spanner/v1/keys.proto\"\330\003\n\010Mutation\022"
          + "3\n\006insert\030\001 \001(\0132!.google.spanner.v1.Muta"
          + "tion.WriteH\000\0223\n\006update\030\002 \001(\0132!.google.sp"
          + "anner.v1.Mutation.WriteH\000\022=\n\020insert_or_u"
          + "pdate\030\003 \001(\0132!.google.spanner.v1.Mutation"
          + ".WriteH\000\0224\n\007replace\030\004 \001(\0132!.google.spann"
          + "er.v1.Mutation.WriteH\000\0224\n\006delete\030\005 \001(\0132\""
          + ".google.spanner.v1.Mutation.DeleteH\000\032Y\n\005"
          + "Write\022\023\n\005table\030\001 \001(\tB\004\342A\001\002\022\017\n\007columns\030\002 "
          + "\003(\t\022*\n\006values\030\003 \003(\0132\032.google.protobuf.Li"
          + "stValue\032O\n\006Delete\022\023\n\005table\030\001 \001(\tB\004\342A\001\002\0220"
          + "\n\007key_set\030\002 \001(\0132\031.google.spanner.v1.KeyS"
          + "etB\004\342A\001\002B\013\n\toperationB\260\001\n\025com.google.spa"
          + "nner.v1B\rMutationProtoP\001Z5cloud.google.c"
          + "om/go/spanner/apiv1/spannerpb;spannerpb\252"
          + "\002\027Google.Cloud.Spanner.V1\312\002\027Google\\Cloud"
          + "\\Spanner\\V1\352\002\032Google::Cloud::Spanner::V1"
          + "b\006proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.google.api.FieldBehaviorProto.getDescriptor(),
              com.google.protobuf.StructProto.getDescriptor(),
              com.google.spanner.v1.KeysProto.getDescriptor(),
            });
    internal_static_google_spanner_v1_Mutation_descriptor =
        getDescriptor().getMessageTypes().get(0);
    internal_static_google_spanner_v1_Mutation_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_spanner_v1_Mutation_descriptor,
            new java.lang.String[] {
              "Insert", "Update", "InsertOrUpdate", "Replace", "Delete", "Operation",
            });
    internal_static_google_spanner_v1_Mutation_Write_descriptor =
        internal_static_google_spanner_v1_Mutation_descriptor.getNestedTypes().get(0);
    internal_static_google_spanner_v1_Mutation_Write_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_spanner_v1_Mutation_Write_descriptor,
            new java.lang.String[] {
              "Table", "Columns", "Values",
            });
    internal_static_google_spanner_v1_Mutation_Delete_descriptor =
        internal_static_google_spanner_v1_Mutation_descriptor.getNestedTypes().get(1);
    internal_static_google_spanner_v1_Mutation_Delete_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_spanner_v1_Mutation_Delete_descriptor,
            new java.lang.String[] {
              "Table", "KeySet",
            });
    com.google.protobuf.ExtensionRegistry registry =
        com.google.protobuf.ExtensionRegistry.newInstance();
    registry.add(com.google.api.FieldBehaviorProto.fieldBehavior);
    com.google.protobuf.Descriptors.FileDescriptor.internalUpdateFileDescriptor(
        descriptor, registry);
    com.google.api.FieldBehaviorProto.getDescriptor();
    com.google.protobuf.StructProto.getDescriptor();
    com.google.spanner.v1.KeysProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
