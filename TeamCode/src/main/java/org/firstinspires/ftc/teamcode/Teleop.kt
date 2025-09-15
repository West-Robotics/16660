package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.setup.Controller;
import org.firstinspires.ftc.teamcode.mechanisms.drivetrain
import kotlin.math.max
import kotlin.math.abs

@TeleOp(name = "TestTele")
class Teleop: LinearOpMode(){

    override fun runOpMode(){
        val drive = drivetrain(hardwareMap)
        val controller1 = Controller(gamepad1)

        waitForStart()

        while (opModeIsActive()){

            var x = controller1.left_stick_x
            var y = controller1.left_stick_y
            var turn = controller1.right_stick_x
            controller1.update()
            drive.mecanumEffort(controller1.left_stick_x,controller1.left_stick_y,controller1.right_stick_x)
            drive.runMotors()

        }
    }



}