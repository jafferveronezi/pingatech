package br.com.veronezitecnologia.pingatech.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import br.com.veronezitecnologia.pingatech.model.PingaData
import br.com.veronezitecnologia.pingatech.repository.DataBasePinga

class ListPingaViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var pingas: LiveData<MutableList<PingaData>>

    private val bd: DataBasePinga = DataBasePinga.getDatabase(application.applicationContext)!!

    init {
        loadData()
    }

    private fun loadData() {
        pingas = bd.pingaDAO().lerPingas()
    }

    private fun deleteData(pinga: PingaData) {
        bd.pingaDAO().apagar(pinga)
    }
}
