package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.setup.Controller;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.mechanisms.drivetrain

@TeleOp(name = "TestTele")
class Teleop: LinearOpMode(){

    override fun runOpMode(){
        val drive = drivetrain(this.hardwareMap)
        val controller1 = Controller(this.gamepad1)

        waitForStart()

        while (opModeIsActive()){
            controller1.update()
            drive.mecanumEffort(controller1.left_stick_x,controller1.left_stick_y,controller1.right_stick_x)
            drive.runMotors()
        }
    }



}