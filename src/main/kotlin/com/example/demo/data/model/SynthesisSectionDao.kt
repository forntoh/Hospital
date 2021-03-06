@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.example.demo.data.model

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import tornadofx.*

/**
 * @author forntoh
 * @version 1.0
 * @created 12-Jun-2020 11:17:28 AM
 */
object SynthesisSectionsTbl : IdTable<Int>() {
    override val id = integer("SynthesisSectionId").autoIncrement().entityId()
    val Name = varchar("Name", 64)
    override val primaryKey = PrimaryKey(columns = *arrayOf(id))
}

class SynthesisSection(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SynthesisSection>(SynthesisSectionsTbl)

    var name by SynthesisSectionsTbl.Name
}

fun ResultRow.toSynthesisSectionEntry() = SynthesisSectionEntry(
        this[SynthesisSectionsTbl.id].value,
        this[SynthesisSectionsTbl.Name]
)

fun SynthesisSectionModel.toRow(): SynthesisSectionsTbl.(UpdateBuilder<*>) -> Unit = {
    it[Name] = this@toRow.name.value
}

class SynthesisSectionEntry(id: Int, name: String) {
    val idProperty = SimpleIntegerProperty(id)
    val id by idProperty

    val nameProperty = SimpleStringProperty(name)
    val name by nameProperty
}

class SynthesisSectionModel : ItemViewModel<SynthesisSectionEntry>() {
    val id = bind { item?.idProperty }
    val name = bind { item?.nameProperty }
}