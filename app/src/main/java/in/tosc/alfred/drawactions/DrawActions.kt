package `in`.tosc.alfred.drawactions

import `in`.tosc.alfred.R
import `in`.tosc.alfred.morningactions.MorningActions

object DrawActions {

    public const val DRAW_ACTION_STEP = "draw_action_step"

    public val DRAW_ACTION_GIFS = intArrayOf(
        R.drawable.draw_spiral_trans,
        R.drawable.draw_star_trans
    )

    public val DRAW_ACTION_INSTRUCTIONS = arrayOf(
        "Draw a clockwise spiral like shown",
        "Draw a start like the one shown below"
    )

    var currentStep = -1

    public fun nextStep () = ++MorningActions.currentStep
}