package `in`.tosc.alfred.morningactions


import `in`.tosc.alfred.R
import `in`.tosc.alfred.morningactions.MorningActions.MORNING_ACTION_STEP
import android.net.Uri
import android.os.Bundle
import android.os.Handler
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            actionStep = it.getInt(MORNING_ACTION_STEP)
        }
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

        Handler().postDelayed({
            rootView.videoMorningActionSample.stopPlayback()
            nextStep()
        }, 5000)

        return rootView
    }

    fun nextStep() {
        (activity as MorningActionsActivity).goToNextFragment()
    }


    companion object {
        @JvmStatic
        fun newInstance(step: Int) =
            SampleActionVideoFragment().apply {
                arguments = Bundle().apply {
                    putInt(MORNING_ACTION_STEP, step)
                }
            }
    }
}
