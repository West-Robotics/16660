package org.firstinspires.ftc.teamcode.mechanisms

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D
import org.firstinspires.ftc.teamcode.setup.motorSetup
import org.firstinspires.ftc.teamcode.setup.Pinpoint
import org.firstinspires.ftc.teamcode.util.*
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sin

class drivetrain(hardwareMap: HardwareMap,telemetry: Telemetry){
    val odo = Pinpoint(hardwareMap)
    val frontLeft = motorSetup(hardwareMap, "frontLeft",DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.BRAKE )
    val frontRight = motorSetup(hardwareMap, "frontRight",DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE )
    val backLeft = motorSetup(hardwareMap, "backLeft",DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.BRAKE )
    val backRight = motorSetup(hardwareMap, "backRight",DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE )

    val telemetry = telemetry
    var XPod:Double = 0.0
    var YPod: Double = 0.0
    var Heading: Double = 0.0

    fun odoUpdate(){
        odo.update()
        val pos = odo.pos
        XPod = pos.getX(DistanceUnit.CM)
        YPod = pos.getY(DistanceUnit.CM)
        Heading = pos.getHeading(AngleUnit.DEGREES)
    }

    fun fieldCentricEffort(y: Double, x: Double, rx: Double){
        var rotX = x * cosDegrees(-Heading) - y * sinDegrees(-Heading)
        var rotY = x * sinDegrees(-Heading) + y * cosDegrees(-Heading)
        rotX = rotX * 1.1 // Counteract imperfect strafing
        val denominator: Double = max(abs(rotY) + abs(rotX) + abs(rx), 1.0)
        frontLeft.effort = (rotY + rotX + rx)/denominator
        backLeft.effort = (rotY - rotX + rx)/denominator
        frontRight.effort = (rotY - rotX - rx)/denominator
        backRight.effort = (rotY + rotX - rx)/denominator
    }



    fun mecanumEffort(y:Double, x:Double, rx:Double){
        val denominator = max(abs(x)+abs(y)+abs(rx),1.0)
        frontLeft.effort = (y+x+rx)/denominator
        backLeft.effort = (y-x+rx)/denominator
        frontRight.effort = (y-x-rx)/denominator
        backRight.effort = (y+x-rx)/denominator
    }



    fun addTelemetry(){
        telemetry.addData("frontLeftAMPS",frontLeft.current)
        telemetry.addData("frontRightAMPS",frontRight.current)
        telemetry.addData("backLeftAMPS",backLeft.current)
        telemetry.addData("backRightAMPS",backRight.current)
        telemetry.addData("XPod",XPod)
        telemetry.addData("YPod",YPod)
        telemetry.addData("Heading",Heading)
    }

    fun write(){
        frontLeft.write()
        frontRight.write()
        backLeft.write()
        backRight.write()
    }
}