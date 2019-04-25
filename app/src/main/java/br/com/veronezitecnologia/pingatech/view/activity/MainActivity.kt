package br.com.veronezitecnologia.pingatech.view.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast
import br.com.veronezitecnologia.pingatech.R
import br.com.veronezitecnologia.pingatech.model.PingaModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        var pinga = intent.getParcelableExtra<PingaModel>(pingaObj)

        createDetail(pinga)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun createDetail(pinga: PingaModel) {
        image_detail.setImageDrawable(ContextCompat.getDrawable(this, pinga.resourceId))
        name_detail.text = pinga.name
        history_detail.text = pinga.city
    }

    companion object {
        val pingaObj = "PINGA"
    }

}
