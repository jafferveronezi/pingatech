package br.com.veronezitecnologia.pingatech.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import br.com.veronezitecnologia.pingatech.model.Pinga
import br.com.veronezitecnologia.pingatech.model.PingaModel
import br.com.veronezitecnologia.pingatech.repository.DataBasePinga

public class ListPingaViewModel constructor (application: Application) : AndroidViewModel(application) {

    lateinit var pingas: LiveData<MutableList<Pinga>>
    private val bd: DataBasePinga = DataBasePinga.getDatabase(application.applicationContext)!!

     init {
        loadData()
    }

    fun loadData() {
//Carregar os dados da nossa Base de dados e armazenar no LiveData
        pingas = bd.pingaDAO().readPingas()
    }
}
