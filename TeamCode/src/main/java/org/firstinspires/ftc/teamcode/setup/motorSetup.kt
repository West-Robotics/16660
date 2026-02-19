package org.firstinspires.ftc.teamcode.setup

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit
import kotlin.math.abs

class motorSetup (hardwareMap: HardwareMap,name:String, direction: DcMotorSimple.Direction,zeroPower: DcMotor.ZeroPowerBehavior){

    var eps = 0.0005
    var motor = hardwareMap.dcMotor.get(name) as DcMotorEx

    init {
        motor.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        motor.direction = direction
        motor.zeroPowerBehavior = zeroPower
        motor.setCurrentAlert(9.5, CurrentUnit.AMPS)
    }
    val velocity
        get() = motor.velocity

    val isOverCurrent
        get() = motor.isOverCurrent

    val current
        get() = motor.getCurrent(CurrentUnit.AMPS)
    private var _effort = 0.0
    var effort
        get() = _effort
        set(value)= if(abs(value-_effort)>eps){
            _effort = value
        } else Unit
    private var lastPower = 0.0

    fun write(){
        if (abs(effort-lastPower)>eps) {
            motor.power = effort
            lastPower = effort
        }
    }

}