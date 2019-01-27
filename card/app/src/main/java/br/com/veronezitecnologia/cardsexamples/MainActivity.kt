package br.com.veronezitecnologia.cardsexamples

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val pessoaList: MutableList<Pessoa> = mutableListOf(
        Pessoa("Adérito Tibiriçá", "atibirica@email.com", "1111-1111"),
        Pessoa("Cleiton Siqueira ", "csiqueira@email.com", "2222-2222"),
        Pessoa("Carlos Proença", "cproenca@email.com", "3333-3333"),
        Pessoa("Filipe Valadão", "fvaladao@email.om", "4444-4444"),
        Pessoa("Flávio Noite", "fnoite@email.com", "5555-5555"),
        Pessoa("Gisela Siebra", "gsiebra@email.com", "6666-6666"),
        Pessoa("Marco Lousã", "mlousa@email.com", "7777-7777"),
        Pessoa("Miriam Tabosa", "mtabosa@email.com", "8888-8888"),
        Pessoa("Rufus Ramírez", "rramirez@email.com", "9999-9999"),
        Pessoa("Socorro Cabreira", "scabreira@email.com", "1010-1010")
    )

    lateinit var pessoaAdapter: PessoaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pessoaAdapter = PessoaAdapter(this, pessoaList)
        recyclerViewPessoas.adapter = pessoaAdapter
        recyclerViewPessoas.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewPessoas.smoothScrollToPosition(pessoaList.size)
    }
}
