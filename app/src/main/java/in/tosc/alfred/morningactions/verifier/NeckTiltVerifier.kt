package `in`.tosc.alfred.morningactions.verifier

import android.util.Log
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark
import com.otaliastudios.cameraview.frame.Frame

class NeckTiltVerifier(val vListener: VerificationListener?, val position: String) :
    Verifier(vListener), IVerifier {

    override fun verify(frame: Frame) {
        if (verificationExpired()) {
            listener?.onVerificationCompleted(false, null)
        }
        faceDetector.detectInImage(super.visionImage(frame)).addOnSuccessListener { faces ->
            Log.d("FACES", "${this.javaClass.simpleName}: ${faces.size}");
            if (faces.isNotEmpty()) {
                for (face in faces) {

                    if (face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EAR) != null &&
                        face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EAR) != null
                    ) {

                        if (position == "left") {
                            val success =
                                (face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EAR)!!.position.y
                                        - face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EAR)!!.position.y
                                        ) > 100
                            if (success)
                                listener?.onVerificationCompleted(true, face)
                        }

                    } else if (position == "right") {
                        val success =
                            (face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EAR)!!.position.y
                                    - face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EAR)!!.position.y
                                    ) > 100
                        if (success)
                            listener?.onVerificationCompleted(true, face)
                    }
                }
            }

        }.addOnFailureListener {

        }

    }
}