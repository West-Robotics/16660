package org.firstinspires.ftc.teamcode.architecture

import com.qualcomm.robotcore.util.ElapsedTime
import kotlin.time.Duration.Companion.milliseconds


class WaitFor(private val duration:Int):Command() {
    private var time = ElapsedTime()
    override fun initialize() {
        time.reset()
        time.startTime()
    }

    override fun isFinished(): Boolean = time.milliseconds()>=duration

}