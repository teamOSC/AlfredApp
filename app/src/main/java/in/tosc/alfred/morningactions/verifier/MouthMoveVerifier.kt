package `in`.tosc.alfred.morningactions.verifier

import com.otaliastudios.cameraview.frame.Frame

class MouthMoveVerifier(val vListener: VerificationListener?): Verifier(vListener), IVerifier {

    override fun verify(frame: Frame) {
        if (verificationExpired()) {
            listener?.onVerificationCompleted(false, null)
        }
        faceDetector.detectInImage(super.visionImage(frame)).addOnSuccessListener { faces ->
            if (faces.isNotEmpty()) {
                for (face in faces) {
                    android.os.Handler().postDelayed({
                        listener?.onVerificationCompleted(true, face)
                    }, 1000)
                }
            }

        }.addOnFailureListener {

        }
        faceDetector.close()

    }
}