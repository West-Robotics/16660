package org.firstinspires.ftc.teamcode.mechanisms

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.setup.motorSetup
import com.qualcomm.robotcore.hardware.DcMotorSimple
import kotlin.math.max
import kotlin.math.abs

class drivetrain (hardwareMap: HardwareMap){
    val frontLeft = motorSetup(hardwareMap, "frontLeft",DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.FLOAT )
    val frontRight = motorSetup(hardwareMap, "frontLeft",DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.FLOAT )
    val backLeft = motorSetup(hardwareMap, "frontLeft",DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.FLOAT )
    val backRight = motorSetup(hardwareMap, "frontLeft",DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.FLOAT )



    fun mecanumEffort(x:Double, y:Double, rx:Double){
        val denominator = max(abs(x)+abs(y)+abs(rx),1.0)
        frontLeft.effort = (y+x+rx)/denominator
        backLeft.effort = (y-x+rx)/denominator
        frontRight.effort = (y-x-rx)/denominator
        backRight.effort = (y+x-rx)/denominator
    }



    fun runMotors(){
        frontLeft.toPower()
        frontRight.toPower()
        backLeft.toPower()
        backRight.toPower()
    }

}