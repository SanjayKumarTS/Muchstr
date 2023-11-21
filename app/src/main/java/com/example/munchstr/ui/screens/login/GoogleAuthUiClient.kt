package com.example.munchstr.ui.screens.login

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class GoogleAuthUiClient(
    private val context: Context,
    private val oneTapClient: SignInClient
) {
    suspend fun signIn(): IntentSender? = suspendCancellableCoroutine { continuation ->
        Log.d("GoogleAuthUiClient", "Attempting to begin sign-in")
        oneTapClient.beginSignIn(buildSignInRequest())
            .addOnSuccessListener { result ->
                Log.d("GoogleAuthUiClient", "Sign-in intent created successfully")
                continuation.resume(result.pendingIntent.intentSender)
            }
            .addOnFailureListener { exception ->
                Log.e("GoogleAuthUiClient", "Sign-in intent creation failed", exception)
                continuation.resumeWithException(exception)
            }
    }
    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId("398353061615-7dsv6r3fpo2pc0l3escj6hk8h52kufmn.apps.googleusercontent.com")
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            )
            .build()
    }
    suspend fun signOut() = suspendCancellableCoroutine { continuation ->
        oneTapClient.signOut()
            .addOnSuccessListener {
                continuation.resume(Unit)
            }
            .addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }
    suspend fun getSignInCredentialFromIntent(data: Intent): SignInCredential = suspendCancellableCoroutine { continuation ->
        try {
            val credential = oneTapClient.getSignInCredentialFromIntent(data)
            continuation.resume(credential)
        } catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }
}