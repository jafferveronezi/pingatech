package br.com.veronezitecnologia.pingatech.model

import android.os.Parcel
import android.os.Parcelable

data class PingaModel(val resourceId: Int,
                      val name: String,
                      val city: String,
                      val manufacturingYear: String,
                      val type: String,
                      val telephone: String,
                      val description: String): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(resourceId)
        parcel.writeString(name)
        parcel.writeString(city)
        parcel.writeString(manufacturingYear)
        parcel.writeString(type)
        parcel.writeString(telephone)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PingaModel> {
        override fun createFromParcel(parcel: Parcel): PingaModel {
            return PingaModel(parcel)
        }

        override fun newArray(size: Int): Array<PingaModel?> {
            return arrayOfNulls(size)
        }
    }
}