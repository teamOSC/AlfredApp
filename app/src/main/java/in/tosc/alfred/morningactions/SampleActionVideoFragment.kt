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
import android.widget.VideoView
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

    private val startTime = System.currentTimeMillis();

    private lateinit var videoView: VideoView
    private val delayHandler: Handler = Handler()

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

        videoView = rootView.videoMorningActionSample
        rootView.videoMorningActionSample.setVideoURI(Uri.parse(videoPath))
        rootView.videoMorningActionSample.start()

        rootView.videoMorningActionSample.setOnCompletionListener {
                goNext()
        }

        val instruction = getString(MorningActions.ACTION_STEP_INSTRUCTIONS[actionStep])

        rootView.videoMorningActionSample.setOnPreparedListener {
            rootView.videoMorningActionSample.setZOrderOnTop(false);
            rootView.textViewInstruction.text = instruction
            it.setVolume(0f, 0f)
        }

        Handler().postDelayed({
            ttObj?.speak(
                instruction,
                TextToSpeech.QUEUE_FLUSH,
                null
            )
        }, 500)


        return rootView
    }

    private fun goNext() {
        val timeLeft = System.currentTimeMillis() - startTime
        if (timeLeft < 3000) {
            delayHandler.postDelayed({
                goNext()
            }, timeLeft)
            return
        }
        videoView.setZOrderOnTop(true);
        videoView.stopPlayback()
        videoView.suspend()
        verifyStep()
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
