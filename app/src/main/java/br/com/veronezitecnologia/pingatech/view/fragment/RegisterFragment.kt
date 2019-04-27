package br.com.veronezitecnologia.pingatech.view.fragment

import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import br.com.veronezitecnologia.pingatech.R
import br.com.veronezitecnologia.pingatech.model.Pinga
import br.com.veronezitecnologia.pingatech.model.PingaModel
import br.com.veronezitecnologia.pingatech.repository.DataBasePinga
import kotlinx.android.synthetic.main.fragment_register.*

class FragmentDashboard: Fragment(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        var rootView = inflater!!.inflate(R.layout.fragment_register, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       register_button.setOnClickListener {
          if(valideInputsRegister()) {
              saveDatabase()
          }
       }

    }

    private fun valideInputsRegister() : Boolean {
        var valide = true

        if("".equals(ed_name_register.text.toString())) {
            ed_name_register.setError(context?.getString(R.string.error_name))
            valide = false
        }
        if("".equals(ed_city_register.text.toString())){
            ed_city_register.setError(context?.getString(R.string.error_city))
            valide = false
        }
        if("".equals(ed_manufacturingYear_register.text.toString())){
            ed_manufacturingYear_register.setError(context?.getString(R.string.error_manufacture_year))
            valide = false
        }
        if("".equals(ed_type_register.text.toString())){
            ed_type_register.setError(context?.getString(R.string.error_type))
            valide = false
        }
        if("".equals(ed_telephone_register.text.toString())){
            ed_telephone_register.setError(context?.getString(R.string.error_telephone))
            valide = false
        }
        if("".equals(ed_description_register.text.toString())){
            ed_description_register.setError(context?.getString(R.string.error_description))
            valide = false
        }
        return valide
    }

    private fun saveDatabase() {
        val db = DataBasePinga.getDatabase(context?.applicationContext!!)
        val pinga = Pinga(1, ed_name_register.text.toString(),  ed_city_register.text.toString(),
            ed_manufacturingYear_register.text.toString(), ed_type_register.text.toString(),
            ed_telephone_register.text.toString(), ed_description_register.text.toString())

        if (pinga.name != "" && pinga.city != "" && pinga.manufacturingYear != "" &&
            pinga.type != "" && pinga.telephone != "" && pinga.description != "" ) {
            InsertAsyncTask(db!!).execute(pinga)
            Toast.makeText(context?.applicationContext!!, this.getString(R.string.register_ok), Toast.LENGTH_LONG).show()
            clearRegister()
        }
    }

    fun clearRegister() {
        ed_name_register.text = null
        ed_city_register.text = null
        ed_manufacturingYear_register.text = null
        ed_type_register.text = null
        ed_telephone_register.text = null
        ed_description_register.text = null
    }

    private inner class InsertAsyncTask internal
    constructor(appDatabase: DataBasePinga) : AsyncTask<Pinga, Void, String>() {
        private val db: DataBasePinga = appDatabase

        override fun doInBackground(vararg params: Pinga): String {
            db.pingaDAO().inserir(params[0])
            return ""
        }
    }
}
