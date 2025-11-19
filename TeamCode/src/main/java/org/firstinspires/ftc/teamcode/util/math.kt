package org.firstinspires.ftc.teamcode.util

import kotlin.math.PI
import kotlin.math.sin
import kotlin.math.cos

fun sinDegrees(degrees: Double):Double{
    val r = degrees * (PI /180)
    return sin(r)
}

fun sinRadians(radians:Double):Double {return sin(radians)}

fun cosDegrees(degrees: Double):Double{
    val r = degrees * (PI/180)
    return cos(r)
}

fun cosRadians(radians: Double):Double {return cos(radians)}

class math {
}