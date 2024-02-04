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
// source: google/spanner/admin/database/v1/spanner_database_admin.proto

package com.google.spanner.admin.database.v1;

public interface RestoreDatabaseRequestOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.spanner.admin.database.v1.RestoreDatabaseRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * Required. The name of the instance in which to create the
   * restored database. This instance must be in the same project and
   * have the same instance configuration as the instance containing
   * the source backup. Values are of the form
   * `projects/&lt;project&gt;/instances/&lt;instance&gt;`.
   * </pre>
   *
   * <code>
   * string parent = 1 [(.google.api.field_behavior) = REQUIRED, (.google.api.resource_reference) = { ... }
   * </code>
   *
   * @return The parent.
   */
  java.lang.String getParent();
  /**
   *
   *
   * <pre>
   * Required. The name of the instance in which to create the
   * restored database. This instance must be in the same project and
   * have the same instance configuration as the instance containing
   * the source backup. Values are of the form
   * `projects/&lt;project&gt;/instances/&lt;instance&gt;`.
   * </pre>
   *
   * <code>
   * string parent = 1 [(.google.api.field_behavior) = REQUIRED, (.google.api.resource_reference) = { ... }
   * </code>
   *
   * @return The bytes for parent.
   */
  com.google.protobuf.ByteString getParentBytes();

  /**
   *
   *
   * <pre>
   * Required. The id of the database to create and restore to. This
   * database must not already exist. The `database_id` appended to
   * `parent` forms the full database name of the form
   * `projects/&lt;project&gt;/instances/&lt;instance&gt;/databases/&lt;database_id&gt;`.
   * </pre>
   *
   * <code>string database_id = 2 [(.google.api.field_behavior) = REQUIRED];</code>
   *
   * @return The databaseId.
   */
  java.lang.String getDatabaseId();
  /**
   *
   *
   * <pre>
   * Required. The id of the database to create and restore to. This
   * database must not already exist. The `database_id` appended to
   * `parent` forms the full database name of the form
   * `projects/&lt;project&gt;/instances/&lt;instance&gt;/databases/&lt;database_id&gt;`.
   * </pre>
   *
   * <code>string database_id = 2 [(.google.api.field_behavior) = REQUIRED];</code>
   *
   * @return The bytes for databaseId.
   */
  com.google.protobuf.ByteString getDatabaseIdBytes();

  /**
   *
   *
   * <pre>
   * Name of the backup from which to restore.  Values are of the form
   * `projects/&lt;project&gt;/instances/&lt;instance&gt;/backups/&lt;backup&gt;`.
   * </pre>
   *
   * <code>string backup = 3 [(.google.api.resource_reference) = { ... }</code>
   *
   * @return Whether the backup field is set.
   */
  boolean hasBackup();
  /**
   *
   *
   * <pre>
   * Name of the backup from which to restore.  Values are of the form
   * `projects/&lt;project&gt;/instances/&lt;instance&gt;/backups/&lt;backup&gt;`.
   * </pre>
   *
   * <code>string backup = 3 [(.google.api.resource_reference) = { ... }</code>
   *
   * @return The backup.
   */
  java.lang.String getBackup();
  /**
   *
   *
   * <pre>
   * Name of the backup from which to restore.  Values are of the form
   * `projects/&lt;project&gt;/instances/&lt;instance&gt;/backups/&lt;backup&gt;`.
   * </pre>
   *
   * <code>string backup = 3 [(.google.api.resource_reference) = { ... }</code>
   *
   * @return The bytes for backup.
   */
  com.google.protobuf.ByteString getBackupBytes();

  /**
   *
   *
   * <pre>
   * Optional. An encryption configuration describing the encryption type and key
   * resources in Cloud KMS used to encrypt/decrypt the database to restore to.
   * If this field is not specified, the restored database will use
   * the same encryption configuration as the backup by default, namely
   * [encryption_type][google.spanner.admin.database.v1.RestoreDatabaseEncryptionConfig.encryption_type] =
   * `USE_CONFIG_DEFAULT_OR_BACKUP_ENCRYPTION`.
   * </pre>
   *
   * <code>
   * .google.spanner.admin.database.v1.RestoreDatabaseEncryptionConfig encryption_config = 4 [(.google.api.field_behavior) = OPTIONAL];
   * </code>
   *
   * @return Whether the encryptionConfig field is set.
   */
  boolean hasEncryptionConfig();
  /**
   *
   *
   * <pre>
   * Optional. An encryption configuration describing the encryption type and key
   * resources in Cloud KMS used to encrypt/decrypt the database to restore to.
   * If this field is not specified, the restored database will use
   * the same encryption configuration as the backup by default, namely
   * [encryption_type][google.spanner.admin.database.v1.RestoreDatabaseEncryptionConfig.encryption_type] =
   * `USE_CONFIG_DEFAULT_OR_BACKUP_ENCRYPTION`.
   * </pre>
   *
   * <code>
   * .google.spanner.admin.database.v1.RestoreDatabaseEncryptionConfig encryption_config = 4 [(.google.api.field_behavior) = OPTIONAL];
   * </code>
   *
   * @return The encryptionConfig.
   */
  com.google.spanner.admin.database.v1.RestoreDatabaseEncryptionConfig getEncryptionConfig();
  /**
   *
   *
   * <pre>
   * Optional. An encryption configuration describing the encryption type and key
   * resources in Cloud KMS used to encrypt/decrypt the database to restore to.
   * If this field is not specified, the restored database will use
   * the same encryption configuration as the backup by default, namely
   * [encryption_type][google.spanner.admin.database.v1.RestoreDatabaseEncryptionConfig.encryption_type] =
   * `USE_CONFIG_DEFAULT_OR_BACKUP_ENCRYPTION`.
   * </pre>
   *
   * <code>
   * .google.spanner.admin.database.v1.RestoreDatabaseEncryptionConfig encryption_config = 4 [(.google.api.field_behavior) = OPTIONAL];
   * </code>
   */
  com.google.spanner.admin.database.v1.RestoreDatabaseEncryptionConfigOrBuilder
      getEncryptionConfigOrBuilder();

  com.google.spanner.admin.database.v1.RestoreDatabaseRequest.SourceCase getSourceCase();
}
