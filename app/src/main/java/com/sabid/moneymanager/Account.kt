package com.sabid.moneymanager

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "accounts",
    foreignKeys = [ForeignKey(
        entity = AccountGroup::class,
        childColumns = ["account_group_id"],
        parentColumns = ["id"],
        onDelete = ForeignKey.RESTRICT,
        onUpdate = ForeignKey.RESTRICT
    )]
)
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val openingBalance: Double,
    @ColumnInfo(name = "account_group_id") val accountGroupId: Int
) {
    override fun toString(): String {
        return name
    }
}
