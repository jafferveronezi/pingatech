package br.com.veronezitecnologia.pingatech.services

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class PingaFirebaseIntanceIdService:FirebaseInstanceIdService(){

    override fun onTokenRefresh() {
        super.onTokenRefresh()
        Log.i("TOKEN", FirebaseInstanceId.getInstance().token)
    }

}