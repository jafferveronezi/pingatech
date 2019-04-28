package br.com.veronezitecnologia.pingatech.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import br.com.veronezitecnologia.pingatech.R
import br.com.veronezitecnologia.pingatech.model.PingaModel
import br.com.veronezitecnologia.pingatech.view.activity.DetailActivity
import br.com.veronezitecnologia.pingatech.view.adapter.PingaAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class FragmentHome : Fragment() {

    private var adapter: PingaAdapter? = null
    private var pingas: MutableList<PingaModel> = mutableListOf()

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

//        showData();

        listPinga.adapter = PingaAdapter(pingas, view.context, { pinga ->
            val detailIntent = Intent(view.context, DetailActivity::class.java)
            detailIntent.putExtra(pingaObj, pinga)
            startActivity(detailIntent)
        })

        val layoutManager = LinearLayoutManager(view.context)
        listPinga.layoutManager = layoutManager
    }

//    private fun showData() {
////of() — indica a activity ou Fragment em que o ViewModel será utilizado
////get() — indica o ViewModel que será utilizado.
//
//        ViewModelProviders.of(this)
//            .get(ListPingaViewModel::class.java)
//            .pingas
//            .observe(this, Observer<MutableList<Pinga>> { pingas ->
//                adapter?.setList(pingas!! as MutableList<PingaModel>)
//                listPinga.adapter?.notifyDataSetChanged()
//            })
//    }

//    private fun pingas(): MutableList<PingaModel> {
//        return listOf(
//            PingaModel(
//                R.drawable.barril,
//                "Santa Mônica",
//                "Campo do Meio - MG",
//                "Desde 1938",
//                "Tipo Ouro",
//                "55353857894"
//            ),
//            PingaModel(
//                R.drawable.barril,
//                "Santa Mônica",
//                "Campo do Meio - MG",
//                "Desde 1938",
//                "Tipo Ouro",
//                "55353857894"
//            ),
//            PingaModel(
//                R.drawable.barril,
//                "Santa Mônica",
//                "Campo do Meio - MG",
//                "Desde 1938",
//                "Tipo Ouro",
//                "55353857894"
//            ),
//            PingaModel(
//                R.drawable.barril,
//                "Santa Mônica",
//                "Campo do Meio - MG",
//                "Desde 1938",
//                "Tipo Ouro",
//                "55353857894"
//            ),
//            PingaModel(
//                R.drawable.barril,
//                "Santa Mônica",
//                "Campo do Meio - MG",
//                "Desde 1938",
//                "Tipo Ouro",
//                "55353857894"
//            ),
//            PingaModel(
//                R.drawable.barril,
//                "Santa Mônica",
//                "Campo do Meio - MG",
//                "Desde 1938",
//                "Tipo Ouro",
//                "55353857894"
//            ),
//            PingaModel(
//                R.drawable.barril,
//                "Santa Mônica",
//                "Campo do Meio - MG",
//                "Desde 1938",
//                "Tipo Ouro",
//                "55353857894"
//            ),
//            PingaModel(
//                R.drawable.barril,
//                "Santa Mônica",
//                "Campo do Meio - MG",
//                "Desde 1938",
//                "Tipo Ouro",
//                "55353857894"
//            ),
//            PingaModel(
//                R.drawable.barril,
//                "Santa Mônica",
//                "Campo do Meio - MG",
//                "Desde 1938",
//                "Tipo Ouro",
//                "55353857894"
//            ),
//            PingaModel(
//                R.drawable.barril,
//                "Santa Mônica",
//                "Campo do Meio - MG",
//                "Desde 1938",
//                "Tipo Ouro",
//                "55353857894"
//            )
//
//        ).toMutableList()
//    }

    companion object {
        fun newInstance(): FragmentHome {
            var fragmentHome = FragmentHome()
            var args = Bundle()
            fragmentHome.arguments = args
            return fragmentHome
        }
        val pingaObj = "PINGA"
    }
}
