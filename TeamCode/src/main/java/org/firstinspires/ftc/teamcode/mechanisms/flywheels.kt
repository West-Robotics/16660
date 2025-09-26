package org.firstinspires.ftc.teamcode.mechanisms

import org.firstinspires.ftc.teamcode.setup.motorSetup
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.HardwareMap

class flywheels(hardwareMap: HardwareMap) {
    val leftFly = motorSetup(hardwareMap,"leftFly",DcMotorSimple.Direction.FORWARD,DcMotor.ZeroPowerBehavior.BRAKE)
    val rightFly = motorSetup(hardwareMap,"rightFly",DcMotorSimple.Direction.REVERSE,DcMotor.ZeroPowerBehavior.BRAKE)

    fun variablePower(power: Double){
        leftFly.effort = power
        rightFly.effort = power
    }

    fun maxPower(){
        leftFly.effort = 1.0
        rightFly.effort = 1.0
    }

    fun zeroPower() {
        leftFly.effort = 0.0
        rightFly.effort = 0.0
    }

    fun reversePower(){
        leftFly.effort = -1.0
        rightFly.effort = -1.0
    }

    fun runMotors(){
        leftFly.toPower()
        rightFly.toPower()
    }


}