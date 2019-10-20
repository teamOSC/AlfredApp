package `in`.tosc.alfred

import `in`.tosc.alfred.onboarding.OnboardingActivity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onboarded = getSharedPreferences("alfred", Context.MODE_PRIVATE)
            .getBoolean("onboarded", false)

        if (onboarded) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            startActivity(Intent(this, OnboardingActivity::class.java))
        }
        finish()
    }
}
