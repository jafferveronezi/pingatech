package br.com.veronezitecnologia.pingatech.view.fragment

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.veronezitecnologia.pingatech.model.PingaData
import br.com.veronezitecnologia.pingatech.repository.DataBasePinga
import kotlinx.android.synthetic.main.fragment_register.*
import android.provider.MediaStore
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.support.v4.content.FileProvider
import br.com.veronezitecnologia.pingatech.R
import br.com.veronezitecnologia.pingatech.utils.ConvertBitmapUtils
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class FragmentDashboard : Fragment() {

    val REQUEST_TAKE_PHOTO = 1

    var currentPhotoPath: String = ""
    val REQUEST_IMAGE_CAPTURE = 1
    var imageByte: ByteArray =  byteArrayOf(0x2E, 0x38)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        var rootView = inflater!!.inflate(R.layout.fragment_register, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photo_button.setOnClickListener {
            dispatchTakePictureIntent(view.context)
        }

        register_button.setOnClickListener {
            if (valideInputsRegister()) {
                saveDatabase()
            }
        }
    }

    private fun dispatchTakePictureIntent(context : Context) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(context?.packageManager!!)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
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
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data.extras.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
            imageByte = ConvertBitmapUtils().getBytes(imageBitmap)
        }
    }

    private fun convertImageDefault() : ByteArray  {
        var convertDefault = BitmapFactory.decodeResource(context?.getResources(), R.drawable.barril);
       return  ConvertBitmapUtils().getBytes(convertDefault)
    }

    private fun valideInputsRegister(): Boolean {
        var valide = true

        if ("".equals(ed_name_register.text.toString())) {
            ed_name_register.setError(context?.getString(R.string.error_name))
            valide = false
        }
        if ("".equals(ed_city_register.text.toString())) {
            ed_city_register.setError(context?.getString(R.string.error_city))
            valide = false
        }
        if ("".equals(ed_manufacturingYear_register.text.toString())) {
            ed_manufacturingYear_register.setError(context?.getString(R.string.error_manufacture_year))
            valide = false
        }
        if ("".equals(ed_type_register.text.toString())) {
            ed_type_register.setError(context?.getString(R.string.error_type))
            valide = false
        }
        if ("".equals(ed_telephone_register.text.toString())) {
            ed_telephone_register.setError(context?.getString(R.string.error_telephone))
            valide = false
        }
        if ("".equals(ed_description_register.text.toString())) {
            ed_description_register.setError(context?.getString(R.string.error_description))
            valide = false
        }
        return valide
    }

    private fun saveDatabase() {
        val db = DataBasePinga.getDatabase(context?.applicationContext!!)

        var imagePinga = imageByte

        if (imagePinga == null) {
            imagePinga = convertImageDefault()
        }

        val pinga = PingaData(
            imagePinga, ed_name_register.text.toString(), ed_city_register.text.toString(),
            ed_manufacturingYear_register.text.toString(), ed_type_register.text.toString(),
            ed_telephone_register.text.toString(), ed_description_register.text.toString()
        )

        if (pinga.name != "" && pinga.city != "" && pinga.manufacturingYear != "" &&
            pinga.type != "" && pinga.telephone != "" && pinga.description != ""
        ) {

            InsertAsyncTask(db!!).execute(pinga)

            Toast.makeText(context?.applicationContext!!, this.getString(R.string.register_ok), Toast.LENGTH_LONG)
                .show()

            clearRegister()
        }
    }

    fun clearRegister() {
        ed_name_register.text = null
        ed_city_register.text = null
        ed_manufacturingYear_register.text = null
        ed_type_register.text = null
        ed_telephone_register.text = null
        ed_description_register.text = null
    }

    private inner class InsertAsyncTask internal
    constructor(appDatabase: DataBasePinga) : AsyncTask<PingaData, Void, String>() {
        private val db: DataBasePinga = appDatabase

        override fun doInBackground(vararg params: PingaData): String {
            db.pingaDAO().inserir(params[0])
            return ""
        }
    }
}
