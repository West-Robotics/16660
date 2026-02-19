package org.firstinspires.ftc.teamcode.FTC.TeleOp.tests

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.FTC.mechanisms.drivetrain
import org.firstinspires.ftc.teamcode.setup.Controller
import org.firstinspires.ftc.teamcode.setup.motorSetup
import org.firstinspires.ftc.teamcode.setup.servoSetup
import org.firstinspires.ftc.teamcode.util.ServoConstants

@TeleOp(name = "HoodTest")
class HoodAngleTest: LinearOpMode() {
    override fun runOpMode() {
        val hood = servoSetup(hardwareMap,"angle", ServoConstants.AXON)
        val controller1 = Controller(gamepad1)
        val drive = drivetrain(hardwareMap,telemetry)
        var yeeter = motorSetup(hardwareMap, "yeeter", DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE)
        val launcher = motorSetup(hardwareMap,"launcher", DcMotorSimple.Direction.FORWARD,DcMotor.ZeroPowerBehavior.BRAKE)
        waitForStart()
        hood.position = 0.1
        while (opModeIsActive()){
            controller1.update()

            drive.mecanumEffort(-controller1.left_stick_y,controller1.left_stick_x,controller1.right_stick_x)
            launcher.effort = controller1.right_trigger
            if (controller1.b()){
                yeeter.effort = 1.0
            } else {
                yeeter.effort =0.0
            }
            if (controller1.dpad_RightOnce()){
                hood.position = hood.position +0.05
            }
            if (controller1.dpad_LeftOnce()){
                hood.position = hood.position -0.05
            }
            if (controller1.dpad_UpOnce()) {
                hood.position = 1.0
            }
            if (controller1.dpad_DownOnce()){
                hood.position = 0.0
            }
            if (controller1.xOnce()){
                hood.position = 0.5
            }

            drive.write()
            hood.write()
            launcher.write()
            yeeter.write()
            telemetry.addData("hoodServo",hood.position)
            telemetry.update()
        }

    }
}