package es.alruiz.biometric

import android.content.Context
import android.hardware.biometrics.BiometricManager
import android.os.Build
import androidx.core.hardware.fingerprint.FingerprintManagerCompat

fun isBiometricAvailable(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT < 29) {
        isHardwareSupported(context) && isFingerprintAvailable(context)
    } else { //Biometric manager (from Android Q)
        val biometricManager = context.getSystemService(BiometricManager::class.java)
        if (biometricManager != null) {
            biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS
        } else false
    }
}

private fun isHardwareSupported(context: Context) = FingerprintManagerCompat.from(context).isHardwareDetected

private fun isFingerprintAvailable(context: Context) = FingerprintManagerCompat.from(context).hasEnrolledFingerprints()
