package org.firstinspires.ftc.teamcode.FTC.TeleOp.Subsystems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.FTC.mechanisms.drivetrain
import org.firstinspires.ftc.teamcode.FTC.mechanisms.lime.Limelight
import org.firstinspires.ftc.teamcode.setup.Controller
import org.firstinspires.ftc.teamcode.setup.motorSetup
import org.firstinspires.ftc.teamcode.setup.servoSetup
import org.firstinspires.ftc.teamcode.util.ServoConstants
import kotlin.math.PI
import kotlin.math.pow

class LauncherSubsystem(val hardwareMap: HardwareMap, val drive: drivetrain, val controller1: Controller, val lime: Limelight) {
    val launcher = motorSetup(hardwareMap,"launcher", DcMotorSimple.Direction.FORWARD,DcMotor.ZeroPowerBehavior.BRAKE)

    val hood = servoSetup(hardwareMap,"angle", ServoConstants.AXON)

    val yeeter = motorSetup(hardwareMap, "yeeter", DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.BRAKE)
    var distance = 0.0

    fun updateLime(){
        lime.update()
        distance = lime.distance
    }

    fun align(){
        lime.updateAlignment()
        drive.fieldCentricEffort(-controller1.left_stick_y,controller1.left_stick_x,-lime.AlignmentPower)
    }

    fun setHoodAngle(){
        val p = (distance+3.0)/39.37 - 3.55
        val angleInRadians = 0.436 + .14*p +0.02*p.pow(2) + 0.024*p.pow(3 )+ 0.01.pow(4) + 0.001*p.pow(5)
        val differenceInServo = 0.36 * angleInRadians * (180/(25*PI))
        hood.position = 0.82 - differenceInServo
    }

    fun power(){
        if (distance>50.0) { // 0.95 higher, 0.87
            launcher.effort = 0.95 * 12.8 / hardwareMap.voltageSensor.iterator().next().voltage  //TODO EDIT AND TEST
        } else{
            launcher.effort = 0.9 * 12.8 / hardwareMap.voltageSensor.iterator().next().voltage  //TODO EDIT AND TEST
        }
        yeeter.effort = 1.0
    }

    fun zero(){
        launcher.effort = 0.0
        yeeter.effort = 0.0
    }

    fun write(){
        launcher.write()
        yeeter.write()
        hood.write()
    }

}