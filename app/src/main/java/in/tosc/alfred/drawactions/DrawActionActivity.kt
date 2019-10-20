package `in`.tosc.alfred.drawactions

import `in`.tosc.alfred.R
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
        if (DrawActions.currentStep == DrawActions.DRAW_ACTION_GIFS.size - 1) {
            finish()
            return
        }
        val nextFragment = DrawActionSampleFragment.newInstance(DrawActions.nextStep())
        supportFragmentManager.commit {
            replace(R.id.fragmentContainer, nextFragment)
        }
    }

    override fun onDestroy() {
        DrawActions.currentStep = -1
        super.onDestroy()
    }
}
