package br.com.veronezitecnologia.pingatech.utils

import android.graphics.BitmapFactory
import android.graphics.Bitmap
import com.google.android.gms.common.util.IOUtils.toByteArray
import android.graphics.Bitmap.CompressFormat
import java.io.ByteArrayOutputStream


class ConvertBitmapUtils {

    // convert from bitmap to byte array
    fun getBytes(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(CompressFormat.PNG, 0, stream)
        return stream.toByteArray()
    }

    // convert from byte array to bitmap
    fun getImage(image: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(image, 0, image.size)
    }
}