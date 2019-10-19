package `in`.tosc.alfred.morningactions

import `in`.tosc.alfred.R

object MorningActions {

    public const val MORNING_ACTION_STEP = "morning_action_step"

    public val ACTION_STEP_VIDEOS = intArrayOf(
        R.raw.eyeball_look_left,
        R.raw.eyeball_look_right,
        R.raw.neck_turn_left,
        R.raw.neck_turn_right,
        R.raw.neck_tilt_left,
        R.raw.neck_tilt_right,
        R.raw.wave_left_hand,
        R.raw.wave_right_hand,
        R.raw.wink_left_eye,
        R.raw.wink_right_eye,
        R.raw.mouth_move,
        R.raw.mouth_smile_open_o,
        R.raw.shrug
    )

    private var currentStep = -1
    public fun nextStep () = ++currentStep
}