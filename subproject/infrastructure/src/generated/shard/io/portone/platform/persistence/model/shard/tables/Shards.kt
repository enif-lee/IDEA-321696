/*
 * This file is generated by jOOQ.
 */
package io.portone.platform.persistence.model.shard.tables


import io.portone.platform.persistence.model.shard.Shard
import io.portone.platform.persistence.model.shard.keys.SHARDS_PKEY
import io.portone.platform.persistence.model.shard.tables.records.ShardsRecord

import java.time.OffsetDateTime

import org.jooq.Field
import org.jooq.ForeignKey
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
open class Shards(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, ShardsRecord>?,
    aliased: Table<ShardsRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<ShardsRecord>(
    alias,
    Shard.SHARD,
    child,
    path,
    aliased,
    parameters,
    DSL.comment(""),
    TableOptions.table()
) {
    companion object {

        /**
         * The reference instance of <code>shard.shards</code>
         */
        val SHARDS: Shards = Shards()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<ShardsRecord> = ShardsRecord::class.java

    /**
     * The column <code>shard.shards.id</code>.
     */
    val ID: TableField<ShardsRecord, String?> = createField(DSL.name("id"), SQLDataType.VARCHAR(255).nullable(false), this, "")

    /**
     * The column <code>shard.shards.created_at</code>.
     */
    val CREATED_AT: TableField<ShardsRecord, OffsetDateTime?> = createField(DSL.name("created_at"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).defaultValue(DSL.field(DSL.raw("now()"), SQLDataType.TIMESTAMPWITHTIMEZONE)), this, "")

    private constructor(alias: Name, aliased: Table<ShardsRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<ShardsRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>shard.shards</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>shard.shards</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>shard.shards</code> table reference
     */
    constructor(): this(DSL.name("shards"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, ShardsRecord>): this(Internal.createPathAlias(child, key), child, key, SHARDS, null)
    override fun getSchema(): Schema? = if (aliased()) null else Shard.SHARD
    override fun getPrimaryKey(): UniqueKey<ShardsRecord> = SHARDS_PKEY
    override fun `as`(alias: String): Shards = Shards(DSL.name(alias), this)
    override fun `as`(alias: Name): Shards = Shards(alias, this)
    override fun `as`(alias: Table<*>): Shards = Shards(alias.getQualifiedName(), this)

    /**
     * Rename this table
     */
    override fun rename(name: String): Shards = Shards(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): Shards = Shards(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): Shards = Shards(name.getQualifiedName(), null)
}