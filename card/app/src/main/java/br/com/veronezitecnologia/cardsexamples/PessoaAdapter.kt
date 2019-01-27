package br.com.veronezitecnologia.cardsexamples

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.pessoa_item.view.*

class PessoaAdapter(private val context: Context, private var pessoaList: MutableList<Pessoa>):
    RecyclerView.Adapter<PessoaAdapter.PessoaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PessoaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.pessoa_item, parent, false)
        return PessoaViewHolder(view)
    }

    override fun getItemCount() = pessoaList.size

    override fun onBindViewHolder(holder: PessoaViewHolder, position: Int) {
        holder.bindView(pessoaList[position])
    }

    class PessoaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textViewNome = itemView.textViewNome
        val textViewEmail = itemView.textViewEmail
        val textViewTelefone = itemView.textViewTelefone

        fun bindView(pessoa: Pessoa) {
            textViewNome.text = pessoa.nome
            textViewEmail.text = pessoa.email
            textViewTelefone.text = pessoa.telefone
        }
    }
}