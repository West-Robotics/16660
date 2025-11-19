package org.firstinspires.ftc.teamcode.setup


import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import kotlin.math.abs
class servoSetup(hardwareMap: HardwareMap,name:String) {
    val servo = hardwareMap.get(name)
    private var _postion = 0.0

    var position: Double
        get() = _postion
        set(value) = if (abs(value-_postion) >0.005){
            _postion = value
        } else Unit


}