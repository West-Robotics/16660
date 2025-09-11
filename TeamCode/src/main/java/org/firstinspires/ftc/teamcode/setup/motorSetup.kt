package org.firstinspires.ftc.teamcode.setup

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap

class motorSetup (hardwareMap: HardwareMap,name:String, direction: DcMotorSimple.Direction,zeroPower: DcMotor.ZeroPowerBehavior){

    val motor = hardwareMap.dcMotor.get(name) as DcMotorEx

    init {
        motor.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        motor.direction = direction
        motor.zeroPowerBehavior = zeroPower
    }

    var effort = 0.0





    fun toPower(){
        motor.power = 1.0
    }

}