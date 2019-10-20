package `in`.tosc.alfred.morningactions.verifier

import android.util.Log
import com.otaliastudios.cameraview.frame.Frame
import java.util.logging.Handler

class EyeballVerifier(val vListener: VerificationListener?, val eye: String): Verifier(vListener), IVerifier {

    override fun verify(frame: Frame) {
        if (verificationExpired()) {
            listener?.onVerificationCompleted(false, null)
        }

        faceDetector.detectInImage(super.visionImage(frame)).addOnSuccessListener { faces ->
            Log.d("FACES", "${this.javaClass.simpleName}: ${faces.size}");
            if (faces.isNotEmpty()) {
                for (face in faces) {
                    android.os.Handler().postDelayed({
                        listener?.onVerificationCompleted(true, face)
                    }, 1000)
                }
            }

        }.addOnFailureListener {
        }

    }
}