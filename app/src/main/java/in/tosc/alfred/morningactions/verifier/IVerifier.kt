package `in`.tosc.alfred.morningactions.verifier

import com.otaliastudios.cameraview.frame.Frame

interface IVerifier {

    fun verify(frame: Frame)
}