package br.com.veronezitecnologia.pingatech.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import br.com.veronezitecnologia.pingatech.R
import br.com.veronezitecnologia.pingatech.model.PingaData
import br.com.veronezitecnologia.pingatech.repository.DataBasePinga
import br.com.veronezitecnologia.pingatech.utils.ConvertBitmapUtils
import br.com.veronezitecnologia.pingatech.view.activity.DetailsItemActivity
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

        val itemTouchHelper = ItemTouchHelper(simpleCallbackItemTouchHelper)
        listPinga.layoutManager =  LinearLayoutManager(context)

        adapter = PingaAdapter(pingas, view.context, { pinga ->
            val detailIntent = Intent(view.context, DetailsItemActivity::class.java)
            detailIntent.putExtra(pingaObj, pinga.id)
            startActivity(detailIntent)
        })

        listPinga.adapter = adapter
        itemTouchHelper.attachToRecyclerView(listPinga)

        if((listPinga.adapter as PingaAdapter).pingas.isEmpty()){
            text_list_empty.visibility = View.VISIBLE
        } else {
            text_list_empty.visibility = View.GONE
        }
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

    private fun convertImageDefault() : ByteArray  {
        var convertDefault = BitmapFactory.decodeResource(context?.getResources(), R.drawable.barril)
        return  ConvertBitmapUtils().getBytes(convertDefault)
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

    var simpleCallbackItemTouchHelper: ItemTouchHelper.SimpleCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition

                val prev = pingas.removeAt(fromPosition)
                pingas.add(if (toPosition > fromPosition) toPosition - 1 else toPosition, prev)
                adapter?.notifyItemMoved(fromPosition, toPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val db = DataBasePinga.getDatabase(context?.applicationContext!!)
                InsertAsyncTask(db!!).execute((listPinga.adapter as PingaAdapter).pingas.get(position))

                (listPinga.adapter as PingaAdapter).pingas.removeAt(position)
                adapter?.notifyDataSetChanged()

                Toast.makeText(context?.applicationContext!!, context?.applicationContext!!.getString(R.string.delete_ok), Toast.LENGTH_LONG)
                    .show()
            }
        }

    private inner class InsertAsyncTask internal
    constructor(appDatabase: DataBasePinga) : AsyncTask<PingaData, Void, String>() {
        private val db: DataBasePinga = appDatabase

        override fun doInBackground(vararg params: PingaData): String {
            db.pingaDAO().apagar(params[0])
            return ""
        }
    }
}
