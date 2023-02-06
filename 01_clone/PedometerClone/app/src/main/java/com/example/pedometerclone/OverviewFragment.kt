package com.example.pedometerclone

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.pedometerclone.databinding.FragmentOverviewBinding
import com.example.pedometerclone.room.DataBase
import com.example.pedometerclone.room.Steps
import com.example.pedometerclone.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.eazegraph.lib.models.PieModel
import java.lang.Exception
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.max


class OverviewFragment : Fragment(), SensorEventListener {

    private val TAG = this::class.java.simpleName

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    private var _sliceCurrent: PieModel? = null
    private val sliceCurrent get() = _sliceCurrent!!

    private var _sliceGoal: PieModel? = null
    private val sliceGoal get() = _sliceGoal!!


    private var showSteps = true
    private var todayOffset = 0
    private var totalStart = 0
    private var goal = 0
    private var sinceBoot = 0
    private var totalDays = 0


    companion object {
        val formatter: NumberFormat = NumberFormat.getInstance(Locale.getDefault())

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        if (Build.VERSION.SDK_INT >= 26) {
            API26Wrapper.startForegroundService(
                requireContext(),
                Intent(activity, SensorListener::class.java)
            )
        } else {
            activity?.startActivity(Intent(activity, SensorListener::class.java))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)

        // slice for the steps taken today
        // slice for the steps taken today
        _sliceCurrent = PieModel("", 0F, Color.parseColor("#99CC00"))
        binding.graph.addPieSlice(sliceCurrent)

        _sliceGoal = PieModel("", DEFAULT_GOAL.toFloat(), Color.parseColor("#CC0000"))
        binding.graph.addPieSlice(sliceGoal)

        binding.graph.setOnClickListener {
            showSteps = !showSteps
            stepsDistanceChanged()
        }

        binding.graph.isDrawValueInPie = false
        binding.graph.isUsePieRotation = true
        binding.graph.startAnimation()
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val context = this
        activity?.let { a ->
            a.actionBar?.setDisplayHomeAsUpEnabled(false)
            val db = Room.databaseBuilder(a, DataBase::class.java, "steps").build().stepsDao()
            lifecycleScope.launch(Dispatchers.IO) {
                // read today offset
                todayOffset = db.getSteps(Util.getToday())

                val prefs = a.getSharedPreferences("pedomter", Context.MODE_PRIVATE)
                goal = prefs.getInt("goal", DEFAULT_GOAL)
                sinceBoot = db.getCurrentSteps()
                val pauseDifference = sinceBoot - prefs.getInt("pauseCount", sinceBoot)

                // register a sensor listener to live update the UI if a step is taken

                val sm = a.getSystemService(Context.SENSOR_SERVICE) as SensorManager
                val sensor = sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
                if (sensor == null) {
                    AlertDialog.Builder(a).setTitle("Sensor not found")
                        .setMessage("This app requires a dedicated hardware step sensor - which your device does not have. This app won\\'t run on your device.")
                        .setOnDismissListener { a.finish() }
                        .setNeutralButton(
                            "Ok"
                        ) { dialog, _ -> dialog?.dismiss() }.create().show()
                } else {
                    sm.registerListener(context, sensor, SensorManager.SENSOR_DELAY_UI, 0)
                }

                sinceBoot -= pauseDifference
                totalStart = db.getTotalWithoutToday(Util.getToday())
                totalDays = db.getDaysWithoutToday(Util.getToday()) + 1 // getDays
                stepsDistanceChanged()
            }
        }
    }

    private fun stepsDistanceChanged() {
        if (showSteps) {
            binding.unit.text = "Steps"
        } else {
            var unit = activity?.getSharedPreferences("pedometer", Context.MODE_PRIVATE)
                ?.getString("stepsize_unit", DEFAULT_STEP_UNIT)
            unit = if (unit == "cm") {
                "km"
            } else {
                "mi"
            }
            binding.unit.text = unit
            updatePie()
            updateBars()
        }
    }

    override fun onPause() {
        super.onPause()
        try {
            val sm = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            sm.unregisterListener(this)
        } catch (e: Exception) {
            Log.d(TAG, e.toString())
            e.printStackTrace()
        }

        val db = Room.databaseBuilder(requireActivity(), DataBase::class.java, "steps").build()
            .stepsDao()
        lifecycleScope.launch(Dispatchers.IO) {
            db.saveCurrentSteps(sinceBoot)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_split_count -> {
                SplitDialog.getDialog(
                    requireActivity(),
                    totalStart + max(todayOffset + sinceBoot, 0)
                ).show()
                true
            }
            else -> {
                return (activity as MainActivity).onOptionsItemSelected(item)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // won't happen
    }

    override fun onSensorChanged(event: SensorEvent?) {
        Log.d(
            TAG,
            "UI - sensorChanged | todayOffset: $todayOffset since boot: ${event?.values?.get(0)}"
        )

        if (event?.values?.get(0)?.toInt()!! > Integer.MAX_VALUE || event?.values?.get(0)
                ?.toInt() == 0
        ) {
            return;
        }

        if (todayOffset == 0) {
            // no values for today
            // we dont know when the reboot was, so set todays steps to 0 by
            // initializing them with - STEPS_SINCE_BOOT
            todayOffset = -event?.values[0].toInt()
            val db = Room.databaseBuilder(requireActivity(), DataBase::class.java, "steps").build()
                .stepsDao()
            lifecycleScope.launch(Dispatchers.IO) {
                db.insertNewDay(Steps(0, Util.getToday(), event?.values[0].toInt()))
                lifecycleScope.launch(Dispatchers.Main) {
                    sinceBoot = event?.values[0].toInt()
                    updatePie()
                }
            }
        }
    }

    /**
     * Updates the pie graph to show todays steps/distance as well as the yesterday and total value.
     * Should be called when switching from step count to distance.
     */
    private fun updatePie() {
        Log.d(TAG, "UI - update steps: $sinceBoot")

        // todayOffset might still be Integer.MIN_VALUe on first start
        val stepsToday = max(todayOffset + sinceBoot, 0)
        sliceCurrent.value = stepsToday.toFloat()
        if(goal - stepsToday > 0) {
            // goal not reached yet
            if(binding.graph.data.size == 1) {
                // can happen if the goal value was changed: old goal value was
                // reached  but now there are some steps missing for the new goal
                binding.graph.addPieSlice(sliceGoal)
            }
            sliceGoal.value = (goal - stepsToday).toFloat()
        } else {
            // goal reached
            binding.graph.clearChart()
            binding.graph.addPieSlice(sliceCurrent)
        }
        binding.graph.update()
        if(showSteps) {
            binding.steps.text = formatter.format(stepsToday)
            binding.total.text = formatter.format(totalStart + stepsToday)
            binding.average.text = formatter.format((totalStart + stepsToday) / totalDays)
        } else {
            // update only every 10 steps when displaying distance
            val prefs = requireActivity().getSharedPreferences("pedometer", Context.MODE_PRIVATE)
            val stepsize = prefs.getFloat("stepsize_value", DEFAULT_STEP_SIZE)
            var distanceToday = stepsToday * stepsize
            var distanceTotal = (totalStart + stepsToday) * stepsize
            if(prefs.getString("stepsize_unit", DEFAULT_STEP_UNIT).equals("cm")) {
                distanceToday /= 100000
                distanceTotal /= 100000
            } else {
                distanceToday /= 5280
                distanceTotal /= 5280
            }

            binding.steps.text = formatter.format(distanceToday)
            binding.total.text = formatter.format(distanceTotal)
            binding.average.text = formatter.format(distanceTotal / totalDays)
        }
    }

    /**
     * Updates the bar graph to show the steps/distance of the last week.
     * Should be called when switching from step count to distance.
     */
    private fun updateBars() {
        val sdf = SimpleDateFormat("E", Locale.getDefault())
        if(binding.bargraph.data.size > 0) binding.bargraph.clearChart()


    }


}