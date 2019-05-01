package br.com.veronezitecnologia.pingatech.repository

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import br.com.veronezitecnologia.pingatech.dao.PingaDAO
import br.com.veronezitecnologia.pingatech.model.PingaData

@Database(entities = arrayOf(PingaData::class), version = 1)
abstract class DataBasePinga : RoomDatabase() {

    abstract fun pingaDAO(): PingaDAO

    companion object {
        var INSTANCE: DataBasePinga? = null
        fun getDatabase(context: Context): DataBasePinga? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    DataBasePinga::class.java,
                    "pingasdbs"
                )
                    .build()
            }
            return INSTANCE
        }
    }
}