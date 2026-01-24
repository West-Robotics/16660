package org.firstinspires.ftc.teamcode.TeleOp.tests

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.setup.CRservoSetup
import org.firstinspires.ftc.teamcode.setup.Controller
import org.firstinspires.ftc.teamcode.setup.motorSetup
import org.firstinspires.ftc.teamcode.setup.servoSetup
import org.firstinspires.ftc.teamcode.util.CRServoConstants
import org.firstinspires.ftc.teamcode.util.ServoConstants

@TeleOp
class testtele : LinearOpMode(){
    override fun runOpMode() {
        val leftMotor = motorSetup(
            hardwareMap,
            "leftMotor",
            DcMotorSimple.Direction.FORWARD,
            DcMotor.ZeroPowerBehavior.BRAKE
        )
        val rightMotor = motorSetup(
            hardwareMap,
            "rightMotor",
            DcMotorSimple.Direction.REVERSE,
            DcMotor.ZeroPowerBehavior.BRAKE
        )
        val servo = servoSetup(hardwareMap, "servo1", ServoConstants.AXON)
        val servo2 = CRservoSetup(hardwareMap, "servo2", CRServoConstants.AXON)
        val controller1 = Controller(gamepad1)
        waitForStart()
        while (opModeIsActive()){
            controller1.update()
            leftMotor.effort = controller1.left_stick_y
            servo2.effort = controller1.right_stick_y

            if (controller1.dpad_UpOnce()){
                servo.position = 1.0
            }
            if (controller1.dpad_DownOnce()){
                servo.position = 0.0
            }
            if (controller1.dpad_RightOnce()){
                servo.position = 0.5
            }
            if (controller1.yOnce()){
                servo.position = servo.position + 0.05
            }
            if (controller1.bOnce()){
                servo.position = servo.position - 0.05
            }


            telemetry.addData("servopos",servo.position)
            telemetry.update()
            servo.write()
            servo2.write()
            rightMotor.write()
            leftMotor.write()
        }
    }

}