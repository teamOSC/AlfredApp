package `in`.tosc.alfred.onboarding


import `in`.tosc.alfred.R
import `in`.tosc.alfred.onboarding.OnBoarding.ONBOARD_STEP
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * A simple [Fragment] subclass.
 * Use the [OnboardingStepFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OnboardingStepFragment : Fragment() {
    private var onboardStep: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            onboardStep = it.getInt(ONBOARD_STEP)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboarding_step, container, false)
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
