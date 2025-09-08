package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.setup.Controller;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp
class Teleop: LinearOpMode(){
    val gamepad: Gamepad = Gamepad()

    val controller: Controller = Controller(gamepad)

    override fun runOpMode(){

    }



}