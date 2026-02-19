package org.firstinspires.ftc.teamcode.FTC.TeleOp.Scrim

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.FTC.mechanisms.DrivetrainTank
import org.firstinspires.ftc.teamcode.setup.Controller


class TankTele: LinearOpMode() {
    override fun runOpMode() {
        val drive = DrivetrainTank(hardwareMap)
        val controller1 = Controller(gamepad1)
        waitForStart()
        while (opModeIsActive()){
            controller1.update()
            drive.stickEffort(-controller1.left_stick_y,-controller1.right_stick_y)
            drive.write()
        }
    }
}