package org.firstinspires.ftc.teamcode.setup


import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.PwmControl
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.hardware.ServoImplEx
import org.firstinspires.ftc.teamcode.util.ServoConstants
import kotlin.math.abs
class servoSetup(hardwareMap: HardwareMap,
                 name:String,
                 pwm: ServoConstants,
                 direction: Servo.Direction = Servo.Direction.FORWARD) {
    val eps = 0.005
    val servo = hardwareMap.get(name) as ServoImplEx

    init {
        servo.direction = direction
        servo.pwmRange = PwmControl.PwmRange(pwm.min,pwm.max)
    }
    private var _postion = 0.0

    var position: Double
        get() = _postion
        set(value) = if (abs(value-_postion) >eps){
            _postion = value
        } else Unit

    fun write(){servo.position = position}

}