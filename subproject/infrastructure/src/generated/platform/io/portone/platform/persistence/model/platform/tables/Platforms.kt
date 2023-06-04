/*
 * This file is generated by jOOQ.
 */
package io.portone.platform.persistence.model.platform.tables


import io.portone.platform.persistence.model.platform.Platform
import io.portone.platform.persistence.model.platform.indexes.PLATFORMS_ID_APPLIED_AT_IDX
import io.portone.platform.persistence.model.platform.keys.PLATFORMS_PKEY
import io.portone.platform.persistence.model.platform.tables.records.PlatformsRecord

import java.time.OffsetDateTime

import kotlin.collections.List

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Index
import org.jooq.Name
import org.jooq.Record
import org.jooq.Schema
import org.jooq.Table
import org.jooq.TableField
import org.jooq.TableOptions
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class Platforms(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, PlatformsRecord>?,
    aliased: Table<PlatformsRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<PlatformsRecord>(
    alias,
    Platform.PLATFORM,
    child,
    path,
    aliased,
    parameters,
    DSL.comment(""),
    TableOptions.table()
) {
    companion object {

        /**
         * The reference instance of <code>platform.platforms</code>
         */
        val PLATFORMS: Platforms = Platforms()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<PlatformsRecord> = PlatformsRecord::class.java

    /**
     * The column <code>platform.platforms.id</code>.
     */
    val ID: TableField<PlatformsRecord, String?> = createField(DSL.name("id"), io.portone.platform.persistence.model.platform.domains.ID.getDataType().nullable(false), this, "")

    /**
     * The column <code>platform.platforms.ref</code>.
     */
    val REF: TableField<PlatformsRecord, String?> = createField(DSL.name("ref"), io.portone.platform.persistence.model.platform.domains.VERSION_REF.getDataType().nullable(false), this, "")

    /**
     * The column <code>platform.platforms.is_hidden</code>.
     */
    val IS_HIDDEN: TableField<PlatformsRecord, Boolean?> = createField(DSL.name("is_hidden"), SQLDataType.BOOLEAN, this, "")

    /**
     * The column <code>platform.platforms.applied_at</code>.
     */
    val APPLIED_AT: TableField<PlatformsRecord, OffsetDateTime?> = createField(DSL.name("applied_at"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false), this, "")

    private constructor(alias: Name, aliased: Table<PlatformsRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<PlatformsRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>platform.platforms</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>platform.platforms</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>platform.platforms</code> table reference
     */
    constructor(): this(DSL.name("platforms"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, PlatformsRecord>): this(Internal.createPathAlias(child, key), child, key, PLATFORMS, null)
    override fun getSchema(): Schema? = if (aliased()) null else Platform.PLATFORM
    override fun getIndexes(): List<Index> = listOf(PLATFORMS_ID_APPLIED_AT_IDX)
    override fun getPrimaryKey(): UniqueKey<PlatformsRecord> = PLATFORMS_PKEY
    override fun `as`(alias: String): Platforms = Platforms(DSL.name(alias), this)
    override fun `as`(alias: Name): Platforms = Platforms(alias, this)
    override fun `as`(alias: Table<*>): Platforms = Platforms(alias.getQualifiedName(), this)

    /**
     * Rename this table
     */
    override fun rename(name: String): Platforms = Platforms(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): Platforms = Platforms(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): Platforms = Platforms(name.getQualifiedName(), null)
}