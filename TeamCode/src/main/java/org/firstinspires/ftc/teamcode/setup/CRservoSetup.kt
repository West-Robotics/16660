package org.firstinspires.ftc.teamcode.setup

import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.CRServoImplEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.PwmControl
import kotlin.math.abs
import org.firstinspires.ftc.teamcode.util.CRServoConstants

class CRservoSetup(hardwareMap: HardwareMap,
                   name: String,
                   direction: DcMotorSimple.Direction = DcMotorSimple.Direction.FORWARD,
                   pwm : CRServoConstants) {
    val servo = hardwareMap.get(name) as CRServoImplEx

    init{
        servo.direction = direction
        servo.pwmRange = PwmControl.PwmRange(pwm.min,pwm.max)
    }
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