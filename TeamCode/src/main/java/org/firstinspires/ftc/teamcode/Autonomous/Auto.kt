package org.firstinspires.ftc.teamcode.Autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.mechanisms.DrivetrainTank
import org.firstinspires.ftc.teamcode.mechanisms.drivetrain

@Autonomous
class Auto: LinearOpMode() {

    override fun runOpMode(){
        val drive = DrivetrainTank(hardwareMap)
        val elapsedtime = ElapsedTime()
        waitForStart()
        elapsedtime.reset()
        while (opModeIsActive()&&elapsedtime.seconds()<2){
            drive.stickEffort(0.35,0.35)
            drive.write()
        }


    }
}