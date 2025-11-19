package org.firstinspires.ftc.teamcode.mechanisms

import com.qualcomm.hardware.sparkfun.SparkFunOTOS
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.teamcode.setup.motorSetup
import org.firstinspires.ftc.teamcode.setup.Pinpoint
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sin

class drivetrain(hardwareMap: HardwareMap){
    val odo = Pinpoint(hardwareMap)
    val frontLeft = motorSetup(hardwareMap, "frontLeft",DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.BRAKE )
    val frontRight = motorSetup(hardwareMap, "frontRight",DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE )
    val backLeft = motorSetup(hardwareMap, "backLeft",DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.BRAKE )
    val backRight = motorSetup(hardwareMap, "backRight",DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE )


    fun fieldCentricEffort(y: Double, x: Double, rx: Double){
        val pos = odo.pos
        val botHeading = pos.getHeading(AngleUnit.DEGREES)
        var rotX = x * cos(-botHeading) - y * sin(-botHeading)
        var rotY = x * sin(-botHeading) + y * cos(-botHeading)
        rotX = rotX * 1.1 // Counteract imperfect strafing
        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        val denominator: Double = max(abs(rotY) + abs(rotX) + Math.abs(rx), 1.0)
        frontLeft.effort = (rotY + rotX + rx) / denominator
        backLeft.effort = (rotY - rotX + rx) / denominator
        frontRight.effort = (rotY - rotX - rx) / denominator
        backRight.effort = (rotY + rotX - rx) / denominator
    }

    fun mecanumEffort(y:Double, x:Double, rx:Double){
        val denominator = max(abs(x)+abs(y)+abs(rx),1.0)
        frontLeft.effort = (y+x+rx)/denominator
        backLeft.effort = (y-x+rx)/denominator
        frontRight.effort = (y-x-rx)/denominator
        backRight.effort = (y+x-rx)/denominator
    }


    fun write(){
        frontLeft.write()
        frontRight.write()
        backLeft.write()
        backRight.write()
    }
}