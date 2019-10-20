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

    var currentStep = -1

    public val ACTION_STEP_INSTRUCTIONS = arrayOf(
        "Look left without moving your head, like this",
        "Look right without moving your head, like this",
        "Now, turn your neck left",
        "Ok, let's try turning the neck right now",
        "Tilt your neck to the left",
        "And now, tilt it to the right",
        "Raise your left hand and wave",
        "Raise your right hand to wave too",
        "Wink with only your left eye",
        "Now wink with your right eye",
        "Narrow your lips and move your lips left and right like this",
        "Let's see a big smile on your face, and then open your mouth",
        "Try to shrug as shown"
    )

    public fun nextStep () = ++currentStep

    var stepVerifyStatus = hashMapOf<Int, Boolean>()
}