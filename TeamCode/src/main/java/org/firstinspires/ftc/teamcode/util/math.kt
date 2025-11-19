package org.firstinspires.ftc.teamcode.util

import kotlin.math.PI
import kotlin.math.sin

class math {
    fun sinDegrees(degrees: Double):Double{
        val r = degrees * (PI /180)
        return sin(r)
    }

}