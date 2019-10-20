package `in`.tosc.alfred.onboarding


import `in`.tosc.alfred.R
import `in`.tosc.alfred.onboarding.OnBoarding.ONBOARD_STEP
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_onboarding_step.*
import kotlinx.android.synthetic.main.fragment_onboarding_step.view.*


/**
 * A simple [Fragment] subclass.
 * Use the [OnboardingStepFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OnboardingStepFragment : Fragment() {

    private var onboardStep: Int = 0
    var ttObj: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            onboardStep = it.getInt(ONBOARD_STEP)
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
        val rootView = inflater.inflate(R.layout.fragment_onboarding_step, container, false)

        Glide
            .with(this)
            .load(OnBoarding.ONBOARD_STEP_GIFS[onboardStep])
            .into(rootView.imageOnBoard)

        rootView.textViewInstruction.text = OnBoarding.ONBOARD_STEP_INSTRUCTIONS[onboardStep]

        activity?.window?.statusBarColor = ContextCompat.getColor(activity!!, R.color.colorAccent)
        activity?.window?.navigationBarColor =
            ContextCompat.getColor(activity!!, R.color.colorPrimaryDark)

        return rootView
    }

    override fun onResume() {
        super.onResume()
        Log.d("FRAG", "resumed: $onboardStep");

        Handler().apply {
            // Speak
            postDelayed({
                ttObj?.speak(
                    OnBoarding.ONBOARD_STEP_INSTRUCTIONS[onboardStep],
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
            }, 500)

            // Next button after 5 seconds
            postDelayed({
                buttonNext?.let {
                    if (onboardStep == 0) {
                        it.text = "Start"
                    }
                    if (onboardStep == OnBoarding.ONBOARD_STEP_GIFS.size - 1) {
                        it.text = "Bye!"
                    }

                    it.visibility = View.VISIBLE
                    it.setOnClickListener {
                        (activity as? OnboardingActivity)?.goToNextFragment()
                    }
                }
            }, 3000)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(step: Int) =
            OnboardingStepFragment().apply {
                arguments = Bundle().apply {
                    putInt(ONBOARD_STEP, step)
                }
            }
    }
}
