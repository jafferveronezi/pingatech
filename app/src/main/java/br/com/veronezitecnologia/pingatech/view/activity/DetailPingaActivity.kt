package br.com.veronezitecnologia.pingatech.view.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import br.com.veronezitecnologia.pingatech.R
import br.com.veronezitecnologia.pingatech.model.PingaModel
import kotlinx.android.synthetic.main.activity_detail_pinga.*

class DetailPingaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pinga)

        val pinga = intent.getParcelableExtra<PingaModel>("PINGA")
        Toast.makeText(this, pinga.name, Toast.LENGTH_LONG).show()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}