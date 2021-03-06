package br.com.veronezitecnologia.pingatech.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.veronezitecnologia.pingatech.R
import br.com.veronezitecnologia.pingatech.model.PingaData

class PingaAdapter(var pingas: MutableList<PingaData>,
                   private val context: Context,
                   val listener: (PingaData) -> Unit) : RecyclerView.Adapter<PingaViewHolder>() {

    fun setList(pingas: MutableList<PingaData>) {
        this.pingas = pingas
    }

    override fun onBindViewHolder(holder: PingaViewHolder, position: Int) {
        val pinga = pingas[position]
        holder?.let {
            it.bindView(pinga, listener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PingaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.pinga_item, parent, false)
        return PingaViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pingas.size
    }

    fun removeAt(position: Int) {
        pingas.removeAt(position)
        notifyItemRemoved(position)
    }

    interface ClickListener {
        fun onClick(view: View, position: Int)
    }
}