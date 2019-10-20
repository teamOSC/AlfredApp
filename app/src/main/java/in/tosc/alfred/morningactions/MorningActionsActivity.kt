package `in`.tosc.alfred.morningactions

import `in`.tosc.alfred.R
import `in`.tosc.alfred.morningactions.verifier.VerifyActionFragment
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit

class MorningActionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_morning_actions)
        goToNextFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_debug, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.toggle_debug) {
            MorningActions.DEBUG = !MorningActions.DEBUG
            Toast.makeText(this, "Debug mode ${MorningActions.DEBUG}", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
    fun goToVerifyFragment() {
        val nextFragment = VerifyActionFragment.newInstance(MorningActions.currentStep)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, nextFragment)
            commit()
        }
    }

    fun goToNextFragment() {
        if (MorningActions.currentStep == MorningActions.ACTION_STEP_VIDEOS.size - 1) {
            finish()
            return
        }
        val nextFragment = SampleActionVideoFragment.newInstance(MorningActions.nextStep())
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, nextFragment)
            commit()
        }
    }

    override fun onDestroy() {
        MorningActions.currentStep = -1
        super.onDestroy()
    }


}
