package br.com.veronezitecnologia.pingatech.view.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import br.com.veronezitecnologia.pingatech.model.PingaModel
import kotlinx.android.synthetic.main.pinga_item.view.*

class PingaViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    fun bindView(pinga: PingaModel,
                 listerner: (PingaModel) -> Unit) = with(itemView) {
        namePinga.text = pinga.name
        typePinga.text = pinga.type
        manufacturingYearPinga.text = pinga.manufacturingYear
        cityPinga.text = pinga.city
        imagePinga.setImageDrawable(ContextCompat.getDrawable(context, pinga.resourceId))

        setOnClickListener { listerner(pinga) }
    }
}