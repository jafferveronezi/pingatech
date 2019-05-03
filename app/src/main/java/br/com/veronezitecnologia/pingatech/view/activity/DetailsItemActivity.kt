package br.com.veronezitecnologia.pingatech.view.activity

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import br.com.veronezitecnologia.pingatech.R
import br.com.veronezitecnologia.pingatech.model.PingaData
import br.com.veronezitecnologia.pingatech.utils.PermissionUtils
import kotlinx.android.synthetic.main.activity_details_item.*
import java.io.ByteArrayOutputStream

class DetailsItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_item)

        var pinga = intent.getParcelableExtra<PingaData>(pingaObj)

        PermissionUtils.validaPermissao(PermissionUtils.listPermissions(), this, 1)

        title_detail.text = pinga.name
        city_detail.text = pinga.city
        manufactureYear_detail.text = pinga.manufacturingYear
        type_detail.text = pinga.type
        description_detail.text = pinga.description

        shared_button.setOnClickListener {
            if (permissionWrite()) {
                share_bitMap_to_Apps()
            } else {
                Toast.makeText(this, this.getString(R.string.info_permission_write), Toast.LENGTH_LONG).show()
            }
        }

        update_button.setOnClickListener {
            val updateIntent = Intent(this, UpdateItemActivity::class.java)
            updateIntent.putExtra(pingaObj, pinga)
            startActivity(updateIntent)
        }
    }


    fun permissionWrite(): Boolean {
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        return false
    }

    fun permissionCall(): Boolean {
        if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        return false
    }

    fun share_bitMap_to_Apps() {

        val i = Intent(Intent.ACTION_SEND);
        i.setType("image/*")
        var stream = ByteArrayOutputStream();
        /*compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();*/
        i.putExtra(Intent.EXTRA_STREAM, getImageUri(this, getBitmapFromView(content)))
        try {
            startActivity(Intent.createChooser(i, "My Profile ..."))
        } catch (ex: ActivityNotFoundException) {
            ex.printStackTrace()
        }
    }


    fun getBitmapFromView(view: View): Bitmap {
        var returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888)
        var canvas = Canvas(returnedBitmap)
        var bgDrawable = view.background
        if (bgDrawable != null)
            bgDrawable.draw(canvas)
        else
            canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        return returnedBitmap
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        var bytes = ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        var path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null)
        return Uri.parse(path)
    }

    companion object {
        val pingaObj = "PINGA"
    }
}
