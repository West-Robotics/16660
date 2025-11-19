package org.firstinspires.ftc.teamcode.mechanisms

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.teamcode.setup.motorSetup
import org.firstinspires.ftc.teamcode.setup.Pinpoint
import org.firstinspires.ftc.teamcode.util.*
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
        var rotX = x * cosDegrees(-botHeading) - y * sinDegrees(-botHeading)
        var rotY = x * sinDegrees(-botHeading) + y * cosDegrees(-botHeading)
        rotX = rotX * 1.1 // Counteract imperfect strafing
        val denominator: Double = max(abs(rotY) + abs(rotX) + abs(rx), 1.0)
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