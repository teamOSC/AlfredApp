package `in`.tosc.alfred.drawactions

import `in`.tosc.alfred.R
import `in`.tosc.alfred.morningactions.MorningActions
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit

class DrawActionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw_action)
        goToNextFragment()
    }

    fun goToNextFragment() {
        val nextFragment = DrawActionSampleFragment.newInstance(MorningActions.nextStep())
        supportFragmentManager.commit {
            replace(R.id.fragmentContainer, nextFragment)
        }
    }
}
