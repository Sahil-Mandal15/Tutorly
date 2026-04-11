package com.sahilm.tutorly.ui.helpers

import android.content.Context
import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.sahilm.tutorly.R
import com.sahilm.tutorly.core.utils.ResultState
import com.sahilm.tutorly.domain.model.UserData
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class GoogleAuthUiHelper(
    private val context: Context,
    private val firebaseAuth: FirebaseAuth
) {

    suspend fun signInWithGoogle(): ResultState<UserData> = try {
        val credentialManager = CredentialManager.create(context)

        val credentialOptions = GetSignInWithGoogleOption.Builder(
            serverClientId = context.getString(R.string.web_client_id)
        ).build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(credentialOptions)
            .build()

        val result = credentialManager.getCredential(context, request)
        val credential = result.credential

        handleSignIn(credential)
    } catch (e: GetCredentialCancellationException) {
        Log.d(TAG, "Credential Cancellation: ${e.message}")
        ResultState.Error("sign-in failed")
    }
    catch (e: Exception) {
        Log.d(TAG, "signInWithGoogle: ${e.message}")
        ResultState.Error(e.message ?: "SignIn failed")
    }

    private suspend fun handleSignIn(credential: Credential): ResultState<UserData> {
        return if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            firebaseAuth(googleIdTokenCredential.idToken)
        } else {
            Log.d(TAG, "handleSignIn: Credential type mismatch")
            ResultState.Error("SignIn Failed")
        }
    }

    private suspend fun firebaseAuth(idToken: String?): ResultState<UserData> =
        suspendCancellableCoroutine { continuation ->
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser = task.result.user
                        if (firebaseUser != null) {
                            continuation.resume(
                                ResultState.Success(
                                    UserData(
                                        userId = firebaseUser.uid,
                                        userName = firebaseUser.displayName,
                                        profilePictureUrl = firebaseUser.photoUrl?.toString()
                                    )
                                )
                            )
                        } else {
                            continuation.resume(ResultState.Error("Firebase user is null"))
                        }
                    } else {
                        continuation.resume(ResultState.Error("Firebase sign-in failed"))
                    }
                }
                .addOnCanceledListener {
                    continuation.resume(ResultState.Error("Firebase sign-in cancelled"))
                }
        }

    suspend fun signOutUser() {
        firebaseAuth.signOut()
    }
}

const val TAG = "GoogleAuthUiHelper"
