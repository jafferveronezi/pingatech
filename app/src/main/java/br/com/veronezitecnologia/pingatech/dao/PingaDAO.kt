package br.com.veronezitecnologia.pingatech.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import br.com.veronezitecnologia.pingatech.model.Pinga

@Dao
interface PingaDAO {
    @Insert
    fun inserir(pinga: Pinga)

    @Query("SELECT * FROM Pinga")
    fun readPingas(): LiveData<MutableList<Pinga>>

    @Query("SELECT * FROM Pinga WHERE id = :id")
    fun buscarPor(id: Int): Pinga

    @Update
    fun atualizar(pinga: Pinga)

    @Delete
    fun apagar(pinga: Pinga)
}
