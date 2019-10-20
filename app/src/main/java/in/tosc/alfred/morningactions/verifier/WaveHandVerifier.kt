package `in`.tosc.alfred.morningactions.verifier

import com.otaliastudios.cameraview.frame.Frame

class WaveHandVerifier(private val listener: VerificationListener, val position: String): Verifier(), IVerifier {

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