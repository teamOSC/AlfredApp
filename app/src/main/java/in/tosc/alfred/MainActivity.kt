package `in`.tosc.alfred

import `in`.tosc.alfred.morningactions.MorningActionsActivity
import `in`.tosc.alfred.onboarding.OnboardingActivity
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setBackgroundDrawable(getDrawable(R.color.colorAccentDark))
        window?.navigationBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)

        buttonHelp.setOnClickListener {
            startActivity(Intent(this, OnboardingActivity::class.java))
        }

        buttonTests.setOnClickListener {
            startActivity(Intent(this, MorningActionsActivity::class.java))
        }

    }
}
