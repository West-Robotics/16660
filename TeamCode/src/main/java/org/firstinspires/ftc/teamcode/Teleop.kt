package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.setup.Controller;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.mechanisms.drivetrain

@TeleOp
class Teleop: LinearOpMode(){


    override fun runOpMode(){
        val gamepad = Gamepad()
        val drive = drivetrain()
        val controller1 = Controller(gamepad)
        waitForStart()

        while (opModeIsActive()){

        }
    }



}