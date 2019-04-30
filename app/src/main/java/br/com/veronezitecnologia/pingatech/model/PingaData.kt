package br.com.veronezitecnologia.pingatech.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class PingaData {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var resourceId: ByteArray = byteArrayOf()
    var name: String? = null
    var city: String? = null
    var manufacturingYear: String? = null
    var type: String? = null
    var telephone: String? = null
    var description: String? = null

    constructor() {}

    constructor(
        resourceId: ByteArray, name: String, city: String, manufacturingYear: String, type: String, telephone: String,
        description: String
    ) {
        this.resourceId = resourceId
        this.name = name
        this.city = city
        this.manufacturingYear = manufacturingYear
        this.type = type
        this.telephone = telephone
        this.description = description
    }

    constructor(
        id: Int,
        resourceId: ByteArray,
        name: String,
        city: String,
        manufacturingYear: String,
        type: String,
        telephone: String,
        description: String
    ) {
        this.resourceId = resourceId
        this.name = name
        this.city = city
        this.manufacturingYear = manufacturingYear
        this.type = type
        this.telephone = telephone
        this.description = description
    }
}