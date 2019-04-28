package br.com.veronezitecnologia.pingatech.view.activity

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity;
import android.view.View
import android.widget.Toast
import br.com.veronezitecnologia.pingatech.R
import br.com.veronezitecnologia.pingatech.model.PingaModel
import br.com.veronezitecnologia.pingatech.utils.PermissionUtils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.io.ByteArrayOutputStream
import java.util.*

class DetailActivity : AppCompatActivity() {

    private lateinit var mMap: GoogleMap
    val permissoesLocalizacao = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CALL_PHONE
    )

    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private var permissionWrite = false

    companion object {
        val pingaObj = "PINGA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        var pinga = intent.getStringExtra(pingaObj)
//        createDetail(pinga)

        PermissionUtils.validaPermissao(permissoesLocalizacao.toTypedArray(), this, 1)
//
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)

        contact_detail.setOnClickListener {
            if (permissionCall()) {
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" +"2342354235"))
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "Sem permissão de acesso ao Telefone!", Toast.LENGTH_LONG).show()
            }
        }

        compartilhar_button.setOnClickListener {
            if (permissionWrite()) {
                share_bitMap_to_Apps()
            } else {
                Toast.makeText(applicationContext, "Sem perimssão de acesso a escrita!", Toast.LENGTH_LONG).show()
            }
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun createDetail(pinga: PingaModel) {
        image_detail.setImageDrawable(ContextCompat.getDrawable(this, pinga.resourceId))
        name_value.text = pinga.name
        history_detail.text = pinga.city
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
        i.putExtra(Intent.EXTRA_STREAM, getImageUri(this, getBitmapFromView(content_shared)))
        try {
            startActivity(Intent.createChooser(i, "My Profile ..."))
        } catch (ex: ActivityNotFoundException) {
            ex.printStackTrace()
        }
    }


    fun getBitmapFromView(view: View): Bitmap {
        var returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888)
        var canvas = Canvas(returnedBitmap)
        var bgDrawable = view.getBackground()
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

    // ******************* shared ********************************************************

//    private fun iniLocationListener() {
//        locationListener = object : LocationListener {
//            override fun onLocationChanged(location: Location?) {
//                var minhaPosicao = LatLng(location?.latitude!!, location?.longitude)
//                addMarcador(minhaPosicao, "Mãe to no Maps!")
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(minhaPosicao, 12.0F))
//            }
//
//            override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
//
//            }
//
//            override fun onProviderEnabled(p0: String?) {
//
//            }
//
//            override fun onProviderDisabled(p0: String?) {
//
//            }
//        }
//    }

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        for (resposta in grantResults) {
//            if (resposta == PackageManager.PERMISSION_DENIED) {
//                Toast.makeText(applicationContext, "Sem perimssão de acesso!", Toast.LENGTH_LONG).show()
//            } else {
//                requestLocationUpdates()
//            }
//        }
//    }
//
//    private fun requestLocationUpdates() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//            == PackageManager.PERMISSION_GRANTED
//        ) {
//            var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//            locationManager.requestLocationUpdates(
//                LocationManager.GPS_PROVIDER,
//                0,
//                0.1f,
//                locationListener
//            )
//        }
//    }
//
//    private fun addMarcador(latLng: LatLng, titulo: String) {
//        mMap.addMarker(
//            MarkerOptions()
//                .position(latLng)
//                .title(titulo)
//        )
//    }
//
//    private fun getEnderecoFormatado(latLng: LatLng): String {
//        val geocoder = Geocoder(applicationContext, Locale.getDefault())
//        val endereco = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
//
//        return "${endereco[0].thoroughfare}, ${endereco[0].subThoroughfare} " +
//                "${endereco[0].subLocality}, ${endereco[0].locality} - " +
//                "${endereco[0].postalCode}"
//    }
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//
//        iniLocationListener()
//
//        // Add a marker in Sydney and move the camera
//        val fiapPaulista = LatLng(-23.5641095, -46.6545986)
//        val fiapAclimacao = LatLng(-23.574081, -46.6256473)
//        val fiapVilaOlimpia = LatLng(-23.5953251, -46.7100846)
//        val fiapAlphaville = LatLng(-23.5148613, -46.7589067)
//
//        mMap.setOnMapClickListener {
//            addMarcador(it, getEnderecoFormatado(it))
//        }
//
//        mMap.setOnMapLongClickListener {
//            addMarcador(it, getEnderecoFormatado(it))
//        }
//
//        mMap.addMarker(
//            MarkerOptions()
//                .position(fiapPaulista)
//                .title("FIAP Paulista")
//                .snippet(getEnderecoFormatado(fiapPaulista))
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
//        )
//
//        mMap.addMarker(
//            MarkerOptions()
//                .position(fiapAclimacao)
//                .title("FIAP Aclimação")
//                .snippet(getEnderecoFormatado(fiapAclimacao))
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.macador))
//        )
//
//        mMap.addMarker(
//            MarkerOptions()
//                .position(fiapVilaOlimpia)
//                .snippet(getEnderecoFormatado(fiapVilaOlimpia))
//                .title("FIAP Vila Olímpia")
//        )
//
//        mMap.addMarker(
//            MarkerOptions()
//                .position(fiapAlphaville)
//                .snippet(getEnderecoFormatado(fiapAlphaville))
//                .title("FIAP Alphaville")
//        )
//
//        var circulo = CircleOptions()
//        circulo.center(fiapPaulista)
//        circulo.radius(100.0)
//        circulo.fillColor(Color.argb(128, 0, 51, 102))
//        circulo.strokeWidth(1F)
//        mMap.addCircle(circulo)
//    }

}
