package `in`.tosc.alfred.drawactions


import `in`.tosc.alfred.R
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.rm.freedrawview.PathDrawnListener
import kotlinx.android.synthetic.main.fragment_draw_action_sample.view.*


/**
 * A simple [Fragment] subclass.
 * Use the [DrawActionSampleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DrawActionSampleFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var drawStep: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            drawStep = it.getInt(DrawActions.DRAW_ACTION_STEP)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView =  inflater.inflate(R.layout.fragment_draw_action_sample, container, false)

        rootView.textViewInstruction.text = DrawActions.DRAW_ACTION_INSTRUCTIONS[drawStep]

        Glide.with(this)
            .load(DrawActions.DRAW_ACTION_GIFS[drawStep])
            .into(rootView.imageSampleDrawing)

        rootView.buttonStart.setOnClickListener {
//            rootView.imageSampleDrawing.visibility = View.GONE
            rootView.freeDrawView.visibility = View.VISIBLE
        }

        rootView.freeDrawView.setOnPathDrawnListener(object: PathDrawnListener {
            override fun onPathStart() {
            }

            override fun onNewPathDrawn() {

                Glide.with(this@DrawActionSampleFragment)
                    .load(R.drawable.react_wow)
                    .into(rootView.imageSampleDrawing)
                rootView.freeDrawView.visibility = View.GONE

                Handler().postDelayed({
                    (activity as? DrawActionActivity)?.goToNextFragment()
                }, 3000)

            }
        })

        return rootView
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DrawActionSampleFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(step: Int) =
            DrawActionSampleFragment().apply {
                arguments = Bundle().apply {
                    putInt(DrawActions.DRAW_ACTION_STEP, step)
                }
            }
    }
}
