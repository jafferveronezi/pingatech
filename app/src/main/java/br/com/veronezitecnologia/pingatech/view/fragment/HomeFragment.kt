package br.com.veronezitecnologia.pingatech.view.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast

import br.com.veronezitecnologia.pingatech.R
import br.com.veronezitecnologia.pingatech.model.PingaModel
import br.com.veronezitecnologia.pingatech.view.activity.DetailPingaActivity
import br.com.veronezitecnologia.pingatech.view.adapter.PingaAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class FragmentHome : Fragment() {

    companion object {
        fun newInstance(): FragmentHome {
            var fragmentHome = FragmentHome()
            var args = Bundle()
            fragmentHome.arguments = args
            return fragmentHome
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        var rootView = inflater!!.inflate(R.layout.fragment_home, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listPinga.adapter = PingaAdapter(pingas(), view.context, { pinga ->
            val detailIntent = Intent(view.context, DetailPingaActivity::class.java)
            detailIntent.putExtra("PINGA", pinga)
            startActivity(detailIntent)
        })

        val layoutManager = LinearLayoutManager(view.context)
        listPinga.layoutManager = layoutManager
    }

    private fun pingas(): MutableList<PingaModel> {
        return listOf(
            PingaModel(
                R.drawable.barril,
                "Santa Mônica",
                "Campo do Meio - MG",
                "Desde 1938",
                "Tipo Ouro"
            ),
            PingaModel(
                R.drawable.barril,
                "Santa Mônica",
                "Campo do Meio - MG",
                "Desde 1938",
                "Tipo Ouro"
            ),
            PingaModel(
                R.drawable.barril,
                "Santa Mônica",
                "Campo do Meio - MG",
                "Desde 1938",
                "Tipo Ouro"
            ),
            PingaModel(
                R.drawable.barril,
                "Santa Mônica",
                "Campo do Meio - MG",
                "Desde 1938",
                "Tipo Ouro"
            ),
            PingaModel(
                R.drawable.barril,
                "Santa Mônica",
                "Campo do Meio - MG",
                "Desde 1938",
                "Tipo Ouro"
            ),
            PingaModel(
                R.drawable.barril,
                "Santa Mônica",
                "Campo do Meio - MG",
                "Desde 1938",
                "Tipo Ouro"
            ),
            PingaModel(
                R.drawable.barril,
                "Santa Mônica",
                "Campo do Meio - MG",
                "Desde 1938",
                "Tipo Ouro"
            ),
            PingaModel(
                R.drawable.barril,
                "Santa Mônica",
                "Campo do Meio - MG",
                "Desde 1938",
                "Tipo Ouro"
            ),
            PingaModel(
                R.drawable.barril,
                "Santa Mônica",
                "Campo do Meio - MG",
                "Desde 1938",
                "Tipo Ouro"
            ),
            PingaModel(
                R.drawable.barril,
                "Santa Mônica",
                "Campo do Meio - MG",
                "Desde 1938",
                "Tipo Ouro"
            )

        ).toMutableList()
    }

}
