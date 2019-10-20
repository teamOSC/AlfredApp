package `in`.tosc.alfred.morningactions.verifier

import android.util.Log
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark
import com.otaliastudios.cameraview.frame.Frame

class NeckTiltVerifier(private val listener: VerificationListener, val position: String) :
    Verifier(), IVerifier {

    override fun verify(frame: Frame) {
        if (verificationExpired()) {
            listener.onVerificationCompleted(false)
        }
        super.faceDetector().detectInImage(super.visionImage(frame)).addOnSuccessListener { faces ->
            if (faces.isNotEmpty()) {
                for (face in faces) {

                    if (face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EAR) != null &&
                        face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EAR) != null
                    ) {
                        Log.e(
                            "lol left y ",
                            face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EAR)!!.position.y.toString()
                        )
                        Log.e(
                            "lol right y ",
                            face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EAR)!!.position.y.toString()
                        )

                        if (position == "left") {
                            val success =
                                (face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EAR)!!.position.y
                                        - face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EAR)!!.position.y
                                        ) > 100
                            if (success)
                                listener.onVerificationCompleted(true)
                        }

                    } else if (position == "right") {
                        val success =
                            (face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EAR)!!.position.y
                                    - face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EAR)!!.position.y
                                    ) > 100
                        if (success)
                            listener.onVerificationCompleted(true)
                    }
                }
            }

        }.addOnFailureListener {

        }

    }
}