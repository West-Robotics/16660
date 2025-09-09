package org.firstinspires.ftc.teamcode.mechanisms

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.setup.motorSetup
import com.qualcomm.robotcore.hardware.DcMotorSimple

class drivetrain (hardwareMap: HardwareMap){
    val frontLeft = motorSetup(hardwareMap, "frontLeft",DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.FLOAT )
    val frontRight = motorSetup(hardwareMap, "frontLeft",DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.FLOAT )
    val backLeft = motorSetup(hardwareMap, "frontLeft",DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.FLOAT )
    val backRight = motorSetup(hardwareMap, "frontLeft",DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.FLOAT )






    fun runMotors(){
        frontLeft.toPower()
        frontRight.toPower()
        backLeft.toPower()
        backRight.toPower()
    }

}