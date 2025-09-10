package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.setup.Controller;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.mechanisms.drivetrain
import kotlin.math.max
import kotlin.math.abs

@TeleOp
class Teleop: LinearOpMode(){
    override fun runOpMode(){
        val controller = Controller(this.gamepad1)
        val drive = drivetrain(hardwareMap = this.hardwareMap)

        waitForStart()

        while (opModeIsActive()){
            var x = controller.left_stick_x
            var y = controller.left_stick_y
            var turn = controller.right_stick_x

            var changeToOne:Double = 1/max(abs(x)+abs(y)+abs(turn),1.0)

            drive.
        }
    }



}