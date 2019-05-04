package es.alruiz.biometric

import android.content.Context
import android.os.Build
import androidx.core.hardware.fingerprint.FingerprintManagerCompat

fun isSdkVersionSupported() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

fun isHardwareSupported(context: Context) = FingerprintManagerCompat.from(context).isHardwareDetected

fun isFingerprintAvailable(context: Context) = FingerprintManagerCompat.from(context).hasEnrolledFingerprints()
