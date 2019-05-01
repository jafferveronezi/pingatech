package br.com.veronezitecnologia.pingatech.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

@Entity
class PingaData : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var resourceId: ByteArray = byteArrayOf()
    var name: String? = null
    var city: String? = null
    var manufacturingYear: String? = null
    var type: String? = null
    var telephone: String? = null
    var description: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        resourceId = parcel.createByteArray()
        name = parcel.readString()
        city = parcel.readString()
        manufacturingYear = parcel.readString()
        type = parcel.readString()
        telephone = parcel.readString()
        description = parcel.readString()
    }

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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeByteArray(resourceId)
        parcel.writeString(name)
        parcel.writeString(city)
        parcel.writeString(manufacturingYear)
        parcel.writeString(type)
        parcel.writeString(telephone)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PingaData> {
        override fun createFromParcel(parcel: Parcel): PingaData {
            return PingaData(parcel)
        }

        override fun newArray(size: Int): Array<PingaData?> {
            return arrayOfNulls(size)
        }
    }
}