package br.com.veronezitecnologia.pingatech.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import br.com.veronezitecnologia.pingatech.R
import br.com.veronezitecnologia.pingatech.model.PingaData
import br.com.veronezitecnologia.pingatech.model.PingaModel
import br.com.veronezitecnologia.pingatech.view.activity.DetailActivity
import br.com.veronezitecnologia.pingatech.view.adapter.PingaAdapter
import br.com.veronezitecnologia.pingatech.viewmodel.ListPingaViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class FragmentHome : Fragment() {

    private var adapter: PingaAdapter? = null
    private var pingas: MutableList<PingaData> = mutableListOf()

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

        showData()

        listPinga.layoutManager =  LinearLayoutManager(context)

        adapter = PingaAdapter(pingas, view.context, { pinga ->
            val detailIntent = Intent(view.context, DetailActivity::class.java)
            detailIntent.putExtra(pingaObj, "pinga")
            startActivity(detailIntent)
        })

        listPinga.adapter = adapter

    }

    private fun showData() {
        ViewModelProviders.of(this)
            .get(ListPingaViewModel::class.java)
            .pingas
            .observe(this, Observer<MutableList<PingaData>> { pingas ->
                adapter?.setList(pingas!!)
                listPinga.adapter?.notifyDataSetChanged()
            })
    }

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
