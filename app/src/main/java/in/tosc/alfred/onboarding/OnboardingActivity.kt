package `in`.tosc.alfred.onboarding

import `in`.tosc.alfred.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit

class OnboardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        goToNextFragment()
    }

    fun goToNextFragment() {
        val nextFragment = OnboardingStepFragment.newInstance(OnBoarding.nextStep())
        supportFragmentManager.commit {
            replace(R.id.fragmentContainer, nextFragment)
        }
    }
}
