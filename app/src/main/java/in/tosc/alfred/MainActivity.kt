package `in`.tosc.alfred

import `in`.tosc.alfred.morningactions.MorningActionsActivity
import `in`.tosc.alfred.onboarding.OnboardingActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        startActivity(Intent(this, MorningActionsActivity::class.java))
        startActivity(Intent(this, OnboardingActivity::class.java))
    }
}
