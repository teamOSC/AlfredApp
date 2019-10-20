package `in`.tosc.alfred.morningactions.verifier

import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark
import com.otaliastudios.cameraview.frame.Frame

class NeckTurnVerifier(private val listener: VerificationListener, val position: String): Verifier(), IVerifier {

    var initialFaceDetected = false

    override fun verify(frame: Frame) {
        if (verificationExpired()) {
            listener.onVerificationCompleted(false)
        }
        super.faceDetector().detectInImage(super.visionImage(frame)).addOnSuccessListener { faces ->
            if (faces.isNotEmpty()) {
                for (face in faces) {
                    if (!initialFaceDetected) {
                        initialFaceDetected = (
                                face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EAR) != null
                                        && face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EAR) != null
                                )
                    }

                    if (initialFaceDetected) {
                        if (position == "left") {
                            if (face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EAR) != null) {
                                listener.onVerificationCompleted(true)
                            }

                        } else if (position == "right") {
                            if (face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EAR) != null) {
                                listener.onVerificationCompleted(true)
                            }
                        }
                    }
                }
            }

        }.addOnFailureListener {

        }

    }
}