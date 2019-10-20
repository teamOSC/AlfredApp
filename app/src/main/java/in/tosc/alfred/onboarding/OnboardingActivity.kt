package `in`.tosc.alfred.onboarding

import `in`.tosc.alfred.R
import `in`.tosc.alfred.utils.ZoomOutPageTransformer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import kotlinx.android.synthetic.main.activity_onboarding.*

class OnboardingActivity : AppCompatActivity() {

    var pageSlidesAdapter = OnBoardingSlidesAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        viewpagerOnboarding.adapter = pageSlidesAdapter
        viewpagerOnboarding.setPageTransformer(ZoomOutPageTransformer())
    }

    fun goToNextFragment() {
        viewpagerOnboarding.currentItem++
    }


    class OnBoardingSlidesAdapter(fragmentActivity: FragmentActivity) :

        FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int = OnBoarding.ONBOARD_STEP_GIFS.size

        override fun createFragment(position: Int): Fragment =
            OnboardingStepFragment.newInstance(position)
    }
}
