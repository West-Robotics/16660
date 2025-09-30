package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.mechanisms.drivetrain


@Autonomous
class Auto: LinearOpMode() {

    override fun runOpMode(){
        val drive = drivetrain(hardwareMap,false)
        val elapsedtime = ElapsedTime()
        waitForStart()
        elapsedtime.reset()
        while (opModeIsActive()&&elapsedtime.seconds()<2){
            drive.tankEffort(0.35,0.35)
            drive.runMotors()
        }


    }
}

