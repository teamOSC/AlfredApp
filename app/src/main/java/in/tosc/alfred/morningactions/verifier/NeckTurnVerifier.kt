package `in`.tosc.alfred.morningactions.verifier

import android.util.Log
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark
import com.otaliastudios.cameraview.frame.Frame

class NeckTurnVerifier(private val listener: VerificationListener, val position: String): Verifier(), IVerifier {

    var initialFaceDetected = false
    var initialNoseX: Float = 0F

    override fun verify(frame: Frame) {
        if (verificationExpired()) {
            listener.onVerificationCompleted(false)
        }
        super.faceDetector().detectInImage(super.visionImage(frame)).addOnSuccessListener { faces ->
            if (faces.isNotEmpty()) {
                for (face in faces) {
                    Log.e("lol initial $initialNoseX", face.getLandmark(FirebaseVisionFaceLandmark.NOSE_BASE)!!.position.x.toString())
                    if (!initialFaceDetected) {
                        initialFaceDetected = (
                                face.getLandmark(FirebaseVisionFaceLandmark.NOSE_BASE) != null
                                )
                        initialNoseX = face.getLandmark(FirebaseVisionFaceLandmark.NOSE_BASE)!!.position.x
                    }

                    if (initialFaceDetected && initialNoseX != 0f) {
                        if (position == "left") {
                            if (face.getLandmark(FirebaseVisionFaceLandmark.NOSE_BASE)!!.position.x - initialNoseX  > 200) {
                                listener.onVerificationCompleted(true)
                            }

                        } else if (position == "right") {
                            if (initialNoseX- face.getLandmark(FirebaseVisionFaceLandmark.NOSE_BASE)!!.position.x > 200) {
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