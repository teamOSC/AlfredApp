package `in`.tosc.alfred.morningactions


import `in`.tosc.alfred.R
import `in`.tosc.alfred.morningactions.MorningActions.MORNING_ACTION_STEP
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_sample_action_video.view.*


/**
 * A simple [Fragment] subclass.
 * Use the [SampleActionVideoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SampleActionVideoFragment : Fragment() {

    // TODO: Rename and change types of parameters
    var actionStep: Int = 0
    var ttObj: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            actionStep = it.getInt(MORNING_ACTION_STEP)
        }
        ttObj = TextToSpeech(activity) {}

    }

    override fun onDestroy() {
        ttObj?.shutdown()
        super.onDestroy()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_sample_action_video, container, false)


        val videoPath = "android.resource://" +
                activity?.packageName +
                "/" + MorningActions.ACTION_STEP_VIDEOS[actionStep]

        rootView.videoMorningActionSample.setVideoURI(Uri.parse(videoPath))
        rootView.videoMorningActionSample.start()

        rootView.videoMorningActionSample.setOnCompletionListener {
            rootView.videoMorningActionSample.setZOrderOnTop(true);
            rootView.videoMorningActionSample.stopPlayback()
            rootView.videoMorningActionSample.suspend()
            verifyStep()
        }

        rootView.videoMorningActionSample.setOnPreparedListener {
            rootView.videoMorningActionSample.setZOrderOnTop(false);
            rootView.textViewInstruction.text = MorningActions.ACTION_STEP_INSTRUCTIONS[actionStep]
            it.setVolume(0f, 0f)
        }

        Handler().postDelayed({
            ttObj?.speak(
                MorningActions.ACTION_STEP_INSTRUCTIONS[actionStep],
                TextToSpeech.QUEUE_FLUSH,
                null
            )
        }, 500)


        return rootView
    }

    private fun verifyStep() {
        (activity as MorningActionsActivity).goToVerifyFragment()
    }

    fun nextStep() {
        (activity as? MorningActionsActivity)?.goToNextFragment()
    }

    companion object {
        fun newInstance(step: Int): SampleActionVideoFragment {
            return SampleActionVideoFragment().apply {
                arguments = Bundle().apply {
                    putInt(MORNING_ACTION_STEP, step)
                }
            }
        }
    }
}
