package `in`.tosc.alfred.morningactions.verifier

import com.otaliastudios.cameraview.frame.Frame

class SmileVerifier(val vListener: VerificationListener?): Verifier(vListener), IVerifier {

    override fun verify(frame: Frame) {
        if (verificationExpired()) {
            listener?.onVerificationCompleted(false)
        }
        faceDetector.detectInImage(super.visionImage(frame)).addOnSuccessListener { faces ->
            if (faces.isNotEmpty()) {
                for (face in faces) {
                   if (face.smilingProbability > 80) listener?.onVerificationCompleted(true)
                }
            }

        }.addOnFailureListener {

        }

    }
}