package `in`.tosc.alfred.morningactions.verifier

import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions
import com.otaliastudios.cameraview.frame.Frame

open class Verifier {

    val verifyStart = System.currentTimeMillis()

    fun visionImage(frame: Frame): FirebaseVisionImage {
        val width = frame.size.width
        val height = frame.size.height

        val metadata = FirebaseVisionImageMetadata.Builder()
            .setWidth(width)
            .setHeight(height)
            .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
            .setRotation(FirebaseVisionImageMetadata.ROTATION_270)
            .build()

        val firebaseVisionImage = FirebaseVisionImage.fromByteArray(frame.data, metadata)

        return firebaseVisionImage

    }

    fun faceDetector(): FirebaseVisionFaceDetector {

        val options = FirebaseVisionFaceDetectorOptions.Builder()
            .setContourMode(FirebaseVisionFaceDetectorOptions.ALL_CONTOURS)
            .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
            .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
            .build()

        return FirebaseVision.getInstance().getVisionFaceDetector(options)
    }

    fun verificationExpired(): Boolean = (System.currentTimeMillis() - verifyStart) > 5000
}