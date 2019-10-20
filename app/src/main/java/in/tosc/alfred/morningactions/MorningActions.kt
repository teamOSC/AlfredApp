package `in`.tosc.alfred.morningactions

import `in`.tosc.alfred.R

object MorningActions {

    public const val MORNING_ACTION_STEP = "morning_action_step"

    var DEBUG = false

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

    var currentStep = -1

    public val ACTION_STEP_INSTRUCTIONS = intArrayOf(
        R.string.morning_action_look_left,
        R.string.morning_action_look_right,
        R.string.morning_action_turn_left,
        R.string.morning_action_turn_right,
        R.string.morning_action_tilt_left,
        R.string.morning_action_wave_left,
        R.string.morning_action_wave_right,
        R.string.morning_action_wink_left,
        R.string.morning_action_wink_right,
        R.string.morning_action_lips,
        R.string.morning_action_mouth,
        R.string.morning_action_shrug
    )

    public fun nextStep () = ++currentStep

    var stepVerifyStatus = hashMapOf<Int, Boolean>()
}