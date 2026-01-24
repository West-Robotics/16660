package org.firstinspires.ftc.teamcode.autonomous.lime

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.mechanisms.drivetrain

@TeleOp
class LimelightTest : LinearOpMode(){
    override fun runOpMode() {
        val drive = drivetrain(hardwareMap, telemetry)
        val align = Alignment(hardwareMap, telemetry,drive)
        waitForStart()

        while (opModeIsActive()){
            align.align()
            telemetry.update()
        }
    }

}