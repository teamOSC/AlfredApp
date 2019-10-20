package `in`.tosc.alfred.morningactions

import `in`.tosc.alfred.R
import `in`.tosc.alfred.morningactions.verifier.VerifyActionFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit

class MorningActionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_morning_actions)
        goToNextFragment()
    }

    fun goToVerifyFragment() {
        val nextFragment = VerifyActionFragment.newInstance(MorningActions.currentStep)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, nextFragment)
            commit()
        }
    }

    fun goToNextFragment() {
        val nextFragment = SampleActionVideoFragment.newInstance(MorningActions.nextStep())
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, nextFragment)
            commit()
        }
    }


}
