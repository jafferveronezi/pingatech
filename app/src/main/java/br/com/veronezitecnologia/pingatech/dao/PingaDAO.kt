package br.com.veronezitecnologia.pingatech.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import br.com.veronezitecnologia.pingatech.model.PingaData

@Dao
interface PingaDAO {

    @Insert
    fun inserir(pinga: PingaData)

    @Query("SELECT * FROM PingaData")
    fun lerPingas(): LiveData<MutableList<PingaData>>

    @Query("SELECT * FROM PingaData WHERE id = :id")
    fun buscarPor(id: Int): PingaData

    @Update
    fun atualizar(pinga: PingaData)

    @Delete
    fun apagar(pinga: PingaData)
}
