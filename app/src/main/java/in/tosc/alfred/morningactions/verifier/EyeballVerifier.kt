package `in`.tosc.alfred.morningactions.verifier

import android.util.Log
import com.otaliastudios.cameraview.frame.Frame

class EyeballVerifier(private val listener: VerificationListener, val eye: String): Verifier(), IVerifier {

    override fun verify(frame: Frame) {
        if (verificationExpired()) {
            listener.onVerificationCompleted(false)
        }
        faceDetector.detectInImage(super.visionImage(frame)).addOnSuccessListener { faces ->
            if (faces.isNotEmpty()) {
                for (face in faces) {
                    listener.onVerificationCompleted(true)
                }
            }

        }.addOnFailureListener {
        }

    }
}