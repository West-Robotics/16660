package org.firstinspires.ftc.teamcode.setup

import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.CRServo
import kotlin.math.abs

class CRservoSetup(hardwareMap: HardwareMap,name: String,) {
    val servo = hardwareMap.get(name) as CRServo
    private var _effort = 0.0
    var effort: Double
        get() = _effort
        set(value) = if (abs(value-_effort)>0.005){
            _effort = value
        } else Unit

    fun write(){
        servo.power = effort
    }

}