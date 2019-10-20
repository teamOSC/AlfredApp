package `in`.tosc.alfred.morningactions.verifier

import android.util.Log
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark
import com.otaliastudios.cameraview.frame.Frame

class NeckTurnVerifier(val vListener: VerificationListener?, val position: String): Verifier(vListener), IVerifier {

    var initialFaceDetected = false
    var initialNoseX: Float = 0F

    override fun verify(frame: Frame) {
        if (verificationExpired()) {
            listener?.onVerificationCompleted(false)
        }
        faceDetector.detectInImage(super.visionImage(frame)).addOnSuccessListener { faces ->
            Log.d("FACES", "${this.javaClass.simpleName}: ${faces.size}");
            if (faces.isNotEmpty()) {
                for (face in faces) {
                    if (!initialFaceDetected) {
                        initialFaceDetected = (
                                face.getLandmark(FirebaseVisionFaceLandmark.NOSE_BASE) != null
                                )
                        initialNoseX = face.getLandmark(FirebaseVisionFaceLandmark.NOSE_BASE)!!.position.x
                    }

                    if (initialFaceDetected && initialNoseX != 0f) {
                        if (position == "left") {
                            if (face.getLandmark(FirebaseVisionFaceLandmark.NOSE_BASE)!!.position.x - initialNoseX  > 200) {
                                listener?.onVerificationCompleted(true)
                            }

                        } else if (position == "right") {
                            if (initialNoseX- face.getLandmark(FirebaseVisionFaceLandmark.NOSE_BASE)!!.position.x > 200) {
                                listener?.onVerificationCompleted(true)
                            }
                        }
                    }
                }
            }

        }.addOnFailureListener {

        }

    }
}