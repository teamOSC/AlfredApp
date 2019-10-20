package `in`.tosc.alfred.morningactions.verifier


import `in`.tosc.alfred.R
import `in`.tosc.alfred.morningactions.MorningActions
import `in`.tosc.alfred.morningactions.MorningActionsActivity
import android.graphics.*
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.ml.vision.face.FirebaseVisionFace
import com.google.firebase.ml.vision.face.FirebaseVisionFaceContour
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.controls.Audio
import com.otaliastudios.cameraview.controls.Facing
import com.otaliastudios.cameraview.frame.Frame
import com.otaliastudios.cameraview.frame.FrameProcessor
import kotlinx.android.synthetic.main.fragment_verify_action.*
import kotlinx.android.synthetic.main.fragment_verify_action.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [VerifyActionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VerifyActionFragment : Fragment(), FrameProcessor {

    var actionStep: Int = 0

    private var cameraFacing: Facing = Facing.FRONT

    private lateinit var cameraView: CameraView
    private lateinit var cameraImageView: ImageView

    private var verifier: IVerifier? = null
    private lateinit var verifyListener: VerificationListener

    private var lastProcessed:Long = 0

    var width: Int = 0
    var height: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            actionStep = it.getInt(MorningActions.MORNING_ACTION_STEP)
            Log.d("FRAG", "onCreate: actionStep = $actionStep");
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_verify_action, container, false)

        cameraView = rootView.cameraView
        cameraImageView = rootView.cameraImageView

        rootView.cameraView.facing = cameraFacing
        rootView.cameraView.audio = Audio.OFF

        verifyListener = object : VerificationListener {
            override fun onVerificationCompleted(success: Boolean, face: FirebaseVisionFace?) {
                destroyVerifier()

                Log.d("FRAG", "onVerificationCompleted: step = ${MorningActions.currentStep}");
                Log.d("FRAG", "onVerificationCompleted: success = ${success}");

                MorningActions.stepVerifyStatus[MorningActions.ACTION_STEP_VIDEOS[MorningActions.currentStep]] =
                    success
                Log.d("FRAG", MorningActions.stepVerifyStatus.toString());

                if (MorningActions.DEBUG && face != null && success) {
                    drawOverlines(face)
                }
                nextStep(success)
            }
        }

        return rootView
    }

    fun setupVerifier() {
        MorningActions.ACTION_STEP_VIDEOS[actionStep].apply {
            when (this) {
                R.raw.wink_left_eye -> {
                    verifier = WinkVerifier(verifyListener, "left")
                }
                R.raw.wink_right_eye -> {
                    verifier = WinkVerifier(verifyListener, "right")
                }
                R.raw.mouth_smile_open_o -> {
                    verifier = SmileVerifier(verifyListener)
                }
                R.raw.neck_turn_left -> {
                    verifier = NeckTurnVerifier(verifyListener, "left")
                }
                R.raw.neck_turn_right -> {
                    verifier = NeckTurnVerifier(verifyListener, "right")
                }
                R.raw.neck_tilt_left -> {
                    verifier = NeckTiltVerifier(verifyListener, "left")
                }
                R.raw.neck_tilt_right -> {
                    verifier = NeckTiltVerifier(verifyListener, "right")
                }
                R.raw.eyeball_look_left -> {
                    verifier = EyeballVerifier(verifyListener, "left")
                }
                R.raw.eyeball_look_right -> {
                    verifier = EyeballVerifier(verifyListener, "right")
                }
                R.raw.mouth_move -> {
                    verifier = MouthMoveVerifier(verifyListener)
                }
                R.raw.shrug -> {
                    verifier = ShrugVerifier(verifyListener)
                }
                R.raw.wave_left_hand -> {
                    verifier = WaveHandVerifier(verifyListener, "left")
                }
                R.raw.wave_right_hand -> {
                    verifier = WaveHandVerifier(verifyListener, "right")
                }
            }
        }
        Log.d("FRAG", "verifier: ${verifier}");
    }

    fun destroyVerifier() {
        (verifier as? Verifier)?.removeListener()
    }

    fun drawOverlines(face: FirebaseVisionFace) {
        cameraImageView.setImageBitmap(null)

        val bitmap = Bitmap.createBitmap(height, width, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val dotPaint = Paint()
        dotPaint.color = Color.RED
        dotPaint.style = Paint.Style.FILL
        dotPaint.strokeWidth = 4F
        val linePaint = Paint()
        linePaint.color = Color.GREEN
        linePaint.style = Paint.Style.STROKE
        linePaint.strokeWidth = 2F

        if (verifier is EyeballVerifier || verifier is WinkVerifier) {

            if ((verifier as? EyeballVerifier)?.eye == "left" || (verifier as? WinkVerifier)?.eye == "left" ) {
                val leftEyeContours = face.getContour(FirebaseVisionFaceContour.LEFT_EYE).points
                for ((i, contour) in leftEyeContours.withIndex()) {
                    if (i != leftEyeContours.lastIndex)
                        canvas.drawLine(contour.x, contour.y, leftEyeContours[i + 1].x, leftEyeContours[i + 1].y, linePaint)
                    else
                        canvas.drawLine(contour.x, contour.y, leftEyeContours[0].x, leftEyeContours[0].y, linePaint)
                    canvas.drawCircle(contour.x, contour.y, 4F, dotPaint)
                }
            }

            if ((verifier as? EyeballVerifier)?.eye == "right" || (verifier as? WinkVerifier)?.eye == "right" ) {
                val rightEyeContours = face.getContour(FirebaseVisionFaceContour.RIGHT_EYE).points
                for ((i, contour) in rightEyeContours.withIndex()) {
                    if (i != rightEyeContours.lastIndex)
                        canvas.drawLine(contour.x, contour.y, rightEyeContours[i + 1].x, rightEyeContours[i + 1].y, linePaint)
                    else
                        canvas.drawLine(contour.x, contour.y, rightEyeContours[0].x, rightEyeContours[0].y, linePaint)
                    canvas.drawCircle(contour.x, contour.y, 4F, dotPaint)
                }
            }


        } else if (verifier is MouthMoveVerifier || verifier is SmileVerifier) {
            val upperLipTopContours = face.getContour(FirebaseVisionFaceContour.UPPER_LIP_TOP).points
            for ((i, contour) in upperLipTopContours.withIndex()) {
                if (i != upperLipTopContours.lastIndex)
                    canvas.drawLine(contour.x, contour.y, upperLipTopContours[i + 1].x, upperLipTopContours[i + 1].y, linePaint)
                canvas.drawCircle(contour.x, contour.y, 4F, dotPaint)
            }

            val upperLipBottomContours = face.getContour(FirebaseVisionFaceContour.UPPER_LIP_BOTTOM).points
            for ((i, contour) in upperLipBottomContours.withIndex()) {
                if (i != upperLipBottomContours.lastIndex)
                    canvas.drawLine(contour.x, contour.y, upperLipBottomContours[i + 1].x, upperLipBottomContours[i + 1].y, linePaint)
                canvas.drawCircle(contour.x, contour.y, 4F, dotPaint)
            }

            val lowerLipTopContours = face.getContour(FirebaseVisionFaceContour.LOWER_LIP_TOP).points
            for ((i, contour) in lowerLipTopContours.withIndex()) {
                if (i != lowerLipTopContours.lastIndex)
                    canvas.drawLine(contour.x, contour.y, lowerLipTopContours[i + 1].x, lowerLipTopContours[i + 1].y, linePaint)
                canvas.drawCircle(contour.x, contour.y, 4F, dotPaint)
            }

            val lowerLipBottomContours = face.getContour(FirebaseVisionFaceContour.LOWER_LIP_BOTTOM).points
            for ((i, contour) in lowerLipBottomContours.withIndex()) {
                if (i != lowerLipBottomContours.lastIndex)
                    canvas.drawLine(contour.x, contour.y, lowerLipBottomContours[i + 1].x, lowerLipBottomContours[i + 1].y, linePaint)
                canvas.drawCircle(contour.x, contour.y, 4F, dotPaint)
            }
        } else if (verifier is NeckTiltVerifier || verifier is NeckTurnVerifier){

            val faceContours = face.getContour(FirebaseVisionFaceContour.FACE).points
            for ((i, contour) in faceContours.withIndex()) {
                if (i != faceContours.lastIndex)
                    canvas.drawLine(
                        contour.x,
                        contour.y,
                        faceContours[i + 1].x,
                        faceContours[i + 1].y,
                        linePaint
                    )
                else
                    canvas.drawLine(
                        contour.x,
                        contour.y,
                        faceContours[0].x,
                        faceContours[0].y,
                        linePaint
                    )
                canvas.drawCircle(contour.x, contour.y, 4F, dotPaint)
            }
        }

        val matrix = Matrix()
        matrix.preScale(-1F, 1F)
        val flippedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        cameraImageView.setImageBitmap(flippedBitmap)
    }

    override fun onResume() {
        super.onResume()
        setupVerifier()
        cameraView.addFrameProcessor(this)
        cameraView.open()
        Log.d("FRAG", "onResume this = $this");
    }

    override fun onPause() {
        cameraView.removeFrameProcessor(this)
        cameraView.clearCameraListeners()
        cameraView.close()
        destroyVerifier()
        super.onPause()
        Log.d("FRAG", "onPause this = $this");
    }

    override fun onDestroy() {
        cameraView.destroy()
        verifier = null
        super.onDestroy()
    }


    override fun process(frame: Frame) {
        if (System.currentTimeMillis() - lastProcessed > 1000) {
            width = frame.size.width
            height = frame.size.height
            verifier?.verify(frame)
            lastProcessed = System.currentTimeMillis()
        }
    }

    fun nextStep(success: Boolean) {
        Log.e("FRAG", "actionStep = $actionStep, success = $success activity = $activity")
        Handler().postDelayed({
            if (activity != null) {
//                Toast.makeText(activity, success.toString(), Toast.LENGTH_SHORT).show()
                (activity as MorningActionsActivity).goToNextFragment()
            } else {
                throw Exception("bhosdike")
            }
        }, 1000)

    }

    companion object {

        fun newInstance(step: Int): VerifyActionFragment {
            return VerifyActionFragment().apply {
                arguments = Bundle().apply {
                    Log.d("FRAG", "arg step = $step");
                    putInt(MorningActions.MORNING_ACTION_STEP, step)
                }
            }
        }
    }
}
