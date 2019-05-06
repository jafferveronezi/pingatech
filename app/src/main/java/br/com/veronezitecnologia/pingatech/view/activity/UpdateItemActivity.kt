package br.com.veronezitecnologia.pingatech.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.widget.ProgressBar
import android.widget.Toast
import br.com.veronezitecnologia.pingatech.R
import br.com.veronezitecnologia.pingatech.model.PingaData
import br.com.veronezitecnologia.pingatech.repository.DataBasePinga
import br.com.veronezitecnologia.pingatech.utils.ConvertBitmapUtils
import kotlinx.android.synthetic.main.activity_update_item.*
import kotlinx.android.synthetic.main.content_register.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class UpdateItemActivity : AppCompatActivity() {

    val REQUEST_TAKE_PHOTO = 1

    var currentPhotoPath: String = ""
    val REQUEST_IMAGE_CAPTURE = 1
    var imageByte: ByteArray = byteArrayOf()
    var idPinga: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_item)

        idPinga = intent.getIntExtra(pingaObj, -1)

        val db = DataBasePinga.getDatabase(this)
        var pinga = SearchAsyncTask(db!!).execute(idPinga).get()

        fillRegister(pinga)

        photo_button.setOnClickListener {
            dispatchTakePictureIntent(this)
        }

        register_button.setText(this.getString(R.string.update))

        register_button.setOnClickListener {
            if (valideInputsRegister()) {
                saveDatabase()
            }
        }
    }

    private fun dispatchTakePictureIntent(context : Context) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(context?.packageManager!!)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        context,
                        "com.example.android.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap!!)
            imageByte = ConvertBitmapUtils().getBytes(imageBitmap!!)
        }
    }

    private fun convertImageDefault() : ByteArray  {
        var convertDefault = BitmapFactory.decodeResource(this.getResources(), R.drawable.barril)
        return  ConvertBitmapUtils().getBytes(convertDefault)
    }

    private fun valideInputsRegister(): Boolean {
        var valide = true

        if ("".equals(ed_name_register.text.toString())) {
            ed_name_register.setError(this.getString(R.string.error_name))
            valide = false
        }
        if ("".equals(ed_city_register.text.toString())) {
            ed_city_register.setError(this.getString(R.string.error_city))
            valide = false
        }
        if ("".equals(ed_manufacturingYear_register.text.toString())) {
            ed_manufacturingYear_register.setError(this.getString(R.string.error_manufacture_year))
            valide = false
        }
        if ("".equals(ed_type_register.text.toString())) {
            ed_type_register.setError(this.getString(R.string.error_type))
            valide = false
        }
        if ("".equals(ed_telephone_register.text.toString())) {
            ed_telephone_register.setError(this.getString(R.string.error_telephone))
            valide = false
        }
        if ("".equals(ed_description_register.text.toString())) {
            ed_description_register.setError(this.getString(R.string.error_description))
            valide = false
        }
        return valide
    }

    private fun saveDatabase() {
        val db = DataBasePinga.getDatabase(this)

//        var imagePinga = imageByte
        var imagePinga = ConvertBitmapUtils().convertByteArrayToBase64(imageByte)

        if (imagePinga.isEmpty()) {
//            imagePinga = convertImageDefault()
            imagePinga = ConvertBitmapUtils().convertByteArrayToBase64(convertImageDefault())
        }

        val pinga = PingaData(
            idPinga, imagePinga, ed_name_register.text.toString(), ed_city_register.text.toString(),
            ed_manufacturingYear_register.text.toString(), ed_type_register.text.toString(),
            ed_telephone_register.text.toString(), ed_description_register.text.toString()
        )

        if (pinga.name != "" && pinga.city != "" && pinga.manufacturingYear != "" &&
            pinga.type != "" && pinga.telephone != "" && pinga.description != "" && pinga.id != -1
        ) {

            UpdateAsyncTask(db!!).execute(pinga)

            Toast.makeText(this, this.getString(R.string.register_ok), Toast.LENGTH_LONG)
                .show()
        }
    }

    fun fillRegister(pinga: PingaData) {
        ed_name_register.setText(pinga.name)
        ed_city_register.setText(pinga.city)
        ed_manufacturingYear_register.setText(pinga.manufacturingYear)
        ed_type_register.setText(pinga.type)
        ed_telephone_register.setText(pinga.telephone)
        ed_description_register.setText(pinga.description)
        imageView.setImageBitmap(ConvertBitmapUtils().convertStringToBitmap(pinga.resourceId!!))
    }

    private inner class UpdateAsyncTask internal
    constructor(appDatabase: DataBasePinga) : AsyncTask<PingaData, Void, String>() {
        private val db: DataBasePinga = appDatabase

        override fun doInBackground(vararg params: PingaData): String {
            db.pingaDAO().atualizar(params[0])
            return ""
        }
    }

    private inner class SearchAsyncTask internal
    constructor(appDatabase: DataBasePinga) : AsyncTask<Int, Void, PingaData>() {
        private val db: DataBasePinga = appDatabase

        override fun doInBackground(vararg p0: Int?): PingaData {
            return db.pingaDAO().buscarPor(p0[0]!!)
        }
    }

    companion object {
        val pingaObj = "PINGA"
    }

    override fun onBackPressed() {
        val detailIntent = Intent(this, DetailsItemActivity::class.java)
        detailIntent.putExtra(pingaObj, idPinga)
        startActivity(detailIntent)
        super.onBackPressed()
    }
}
