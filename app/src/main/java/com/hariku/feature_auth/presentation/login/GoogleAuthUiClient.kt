package com.hariku.feature_auth.presentation.login

import android.content.Context
import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.hariku.R
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

class GoogleAuthUiClient(
    private val context: Context
) {
    private val tag = "GoogleAuthClient"
    private val credentialManager = CredentialManager.create(context)
    private val webClientId = context.getString(R.string.web_client_id)

//    fun isSignedIn(): Boolean {
//        return auth.currentUser != null
//    }

    suspend fun signIn(): GoogleIdTokenCredential? {
//        if(isSignedIn()){
//            return true
//        }

        try {
            val result = buildCredentialRequest()
            return handleSignInResult(result)
        } catch (e: Exception){
            Log.e(tag, "signIn: ${e.message}")
            e.printStackTrace()
            if(e is CancellationException) {
                throw e
            }
            if(e is GetCredentialException){
                Log.e(tag, "signIn, GetCredentialException: ${e.message}")
            }
            return null
        }
    }

    private suspend fun handleSignInResult(result: GetCredentialResponse): GoogleIdTokenCredential? {
        val credential = result.credential
        if(
            credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ){
            try {
                val tokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

                println(tag + "name: ${tokenCredential.displayName}")
                println(tag + "email: ${tokenCredential.id}")
                println(tag + "image: ${tokenCredential.profilePictureUri}")

//                val authCredential = GoogleAuthProvider.getCredential(
//                    tokenCredential.idToken, null
//                )
//                val authResult = auth.signInWithCredential(authCredential).await()
//
//                return authResult.user != null
                return tokenCredential

            } catch(e: GoogleIdTokenParsingException) {
                println(tag + "GoogleIdTokenParsingException: ${e.message}")
                return null
            }
        } else {
            println(tag + "credential is not GoogleIdTokenParsingException")
            return null
        }
    }
    private suspend fun buildCredentialRequest(): GetCredentialResponse{
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(
                GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(webClientId)
                    .setAutoSelectEnabled(false)
                    .build()
            )
            .build()
        return credentialManager.getCredential(context = context, request = request)
    }

    suspend fun signOut(auth: FirebaseAuth){
        credentialManager.clearCredentialState(
            ClearCredentialStateRequest()
        )
        auth.signOut()
    }
}