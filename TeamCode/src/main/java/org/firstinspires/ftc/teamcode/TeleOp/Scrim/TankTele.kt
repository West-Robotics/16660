package org.firstinspires.ftc.teamcode.TeleOp.Scrim

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.mechanisms.drivetrain
import org.firstinspires.ftc.teamcode.setup.Controller

@TeleOp(name="Tank Drive")
class TankTele: LinearOpMode() {
    override fun runOpMode() {
        val drive = drivetrain(hardwareMap, false)
        val controller1 = Controller(gamepad1)
        waitForStart()
        while (opModeIsActive()){
            controller1.update()
            drive.tankEffort(-controller1.left_stick_y,-controller1.right_stick_y)
            drive.write()
        }
    }
}