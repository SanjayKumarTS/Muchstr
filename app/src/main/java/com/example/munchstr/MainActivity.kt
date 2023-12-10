package com.example.munchstr

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.cloudinary.android.MediaManager
import com.example.munchstr.ui.screens.home.HomePage
import com.example.munchstr.ui.theme.MunchstrTheme
import dagger.hilt.android.AndroidEntryPoint
import android.Manifest
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.munchstr.utils.ConnectivityObserver
import com.example.munchstr.utils.NetworkStatusHolder
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val MY_CAMERA_PERMISSION_CODE = 100

    @Inject
    lateinit var connectivityObserver: ConnectivityObserver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                Log.d("LifeCycle", "onCreate() called")
                connectivityObserver.observe().collect { status ->
                    NetworkStatusHolder.updateNetworkStatus(status)
                    Log.d("LifeCycle", "Status ${status.toString()}")
                }
            }
        }

        val config: HashMap<Any?, Any?> = HashMap<Any?, Any?>().apply {
            put("cloud_name", BuildConfig.CLOUDINARY_CLOUD_NAME)
            put("api_key", BuildConfig.CLOUDINARY_API_KEY)
            put("api_secret", BuildConfig.CLOUDINARY_SECRET)
        }
        MediaManager.init(this, config)
        setContent {
            MunchstrTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                MY_CAMERA_PERMISSION_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_CAMERA_PERMISSION_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                } else {
                }
                return
            }
        }
    }
}
