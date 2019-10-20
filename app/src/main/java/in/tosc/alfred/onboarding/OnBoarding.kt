package `in`.tosc.alfred.onboarding

import `in`.tosc.alfred.R

object OnBoarding {

    public const val ONBOARD_STEP = "onboard_step"

    public val ONBOARD_STEP_GIFS = intArrayOf(
        R.drawable.namaste
    )

    public val ONBOARD_STEP_INSTRUCTIONS = arrayOf(
        "Namaste! I am Alfred, your friendly assistant"
    )

    private var currentStep = -1;
    public fun nextStep() = ++currentStep

}