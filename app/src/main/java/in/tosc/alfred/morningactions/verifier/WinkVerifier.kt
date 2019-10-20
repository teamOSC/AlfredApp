package `in`.tosc.alfred.morningactions.verifier

import android.util.Log
import com.otaliastudios.cameraview.frame.Frame

class WinkVerifier(val vListener: VerificationListener?, val eye: String): Verifier(vListener), IVerifier {

    override fun verify(frame: Frame) {
        if (verificationExpired()) {
            listener?.onVerificationCompleted(false)
        }
        faceDetector.detectInImage(super.visionImage(frame)).addOnSuccessListener { faces ->
            Log.d("FACES", "${this.javaClass.simpleName}: ${faces.size}");
            if (faces.isNotEmpty()) {
                for (face in faces) {
                    if (eye == "left") {
                        if (face.leftEyeOpenProbability < 30)
                            listener?.onVerificationCompleted(true)
                    } else if (eye == "right") {
                        if (face.rightEyeOpenProbability < 50)
                            listener?.onVerificationCompleted(true)
                    }
                }
            }

        }.addOnFailureListener {

        }

    }
}