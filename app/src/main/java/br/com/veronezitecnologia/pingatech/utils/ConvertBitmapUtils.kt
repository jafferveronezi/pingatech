package br.com.veronezitecnologia.pingatech.utils

import android.graphics.BitmapFactory
import android.graphics.Bitmap
import com.google.android.gms.common.util.IOUtils.toByteArray
import android.graphics.Bitmap.CompressFormat
import java.io.ByteArrayOutputStream
import android.R.attr.bitmap
import android.util.Base64


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

    fun convertByteArrayToBase64(byteArray: ByteArray): String = Base64.encodeToString(byteArray, Base64.DEFAULT)

    @Throws(IllegalArgumentException::class)
    fun convertStringToBitmap(base64Str: String): Bitmap {
        val decodedBytes = Base64.decode(
            base64Str.substring(base64Str.indexOf(",") + 1),
            Base64.DEFAULT
        )

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

    fun convert(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
    }
}