package `in`.tosc.alfred.morningactions.verifier

import com.otaliastudios.cameraview.frame.Frame

class WinkVerifier(private val listener: VerificationListener, val eye: String): Verifier(), IVerifier {

    override fun verify(frame: Frame) {
        if (verificationExpired()) {
            listener.onVerificationCompleted(false)
        }
        faceDetector.detectInImage(super.visionImage(frame)).addOnSuccessListener { faces ->
            if (faces.isNotEmpty()) {
                for (face in faces) {
                    if (eye == "left") {
                        if (face.leftEyeOpenProbability < 30)
                            listener.onVerificationCompleted(true)
                    } else if (eye == "right") {
                        if (face.rightEyeOpenProbability < 50)
                            listener.onVerificationCompleted(true)
                    }
                }
            }

        }.addOnFailureListener {

        }

    }
}