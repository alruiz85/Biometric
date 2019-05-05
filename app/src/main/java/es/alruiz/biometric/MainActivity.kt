package es.alruiz.biometric

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var executor: Executor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        executor = Executors.newSingleThreadExecutor()

        val biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    // user clicked negative button
                    setTextInfo(text = "Biometric CANCELLED")
                } else {
                    // error and the operation is complete
                    setTextInfo(text = "Biometric ERROR")
                }
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                // biometric is recognized
                setTextInfo(text = "Biometric OK")

            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                // biometric is valid but not recognized
                setTextInfo(text = "Biometric ERROR")
            }
        })


        btnStart.setOnClickListener {
            if (isFingerprintAvailable(applicationContext) && isHardwareSupported(applicationContext)) {
                //Create prompt info
                val promptInfo = BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Biometric Authentication")
                    .setSubtitle("")
                    .setDescription("Place your finger on the sensor")
                    .setNegativeButtonText("Cancel")
                    .build()

                //Authenticate
                biometricPrompt.authenticate(promptInfo)
            } else {
                setTextInfo(text = "Hardware not supported")
            }
        }

    }

    private fun setTextInfo(text: String) {
        runOnUiThread {
            txtResult.text = text
        }
    }
}
