package `in`.tosc.alfred.morningactions.verifier


import `in`.tosc.alfred.R
import `in`.tosc.alfred.morningactions.MorningActions
import `in`.tosc.alfred.morningactions.MorningActionsActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

    private var verifier: IVerifier? = null
    private lateinit var verifyListener: VerificationListener

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

        rootView.cameraView.facing = cameraFacing
        rootView.cameraView.audio = Audio.OFF

        verifyListener = object : VerificationListener {
            override fun onVerificationCompleted(success: Boolean) {
                destroyVerifier()

                Log.d("FRAG", "onVerificationCompleted: step = ${MorningActions.currentStep}");
                Log.d("FRAG", "onVerificationCompleted: success = ${success}");

                MorningActions.stepVerifyStatus[MorningActions.ACTION_STEP_VIDEOS[MorningActions.currentStep]] =
                    success
                Log.d("FRAG", MorningActions.stepVerifyStatus.toString());
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
        verifier?.verify(frame)
    }

    fun nextStep(success: Boolean) {
        Log.e("FRAG", "actionStep = $actionStep, success = $success activity = $activity")

        if (activity != null) {
            Toast.makeText(activity, success.toString(), Toast.LENGTH_SHORT).show()
            (activity as MorningActionsActivity).goToNextFragment()
        } else {
            throw Exception("bhosdike")
        }
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
