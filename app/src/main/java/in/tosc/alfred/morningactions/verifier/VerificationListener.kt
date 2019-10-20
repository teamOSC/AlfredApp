package `in`.tosc.alfred.morningactions.verifier

import com.google.firebase.ml.vision.face.FirebaseVisionFace

interface VerificationListener {
    fun onVerificationCompleted(success: Boolean, face: FirebaseVisionFace?)

}