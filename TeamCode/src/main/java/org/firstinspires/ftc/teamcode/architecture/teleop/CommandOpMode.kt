package org.firstinspires.ftc.teamcode.architecture.teleop

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.architecture.CommandScheduler
import org.firstinspires.ftc.teamcode.architecture.Gamepad
import org.firstinspires.ftc.teamcode.architecture.commands.IntakeCommand
import org.firstinspires.ftc.teamcode.architecture.subsystems.IntakeSubsystem
import org.firstinspires.ftc.teamcode.mechanisms.drivetrain
import org.firstinspires.ftc.teamcode.setup.Controller

abstract class CommandOpMode : LinearOpMode(){
    lateinit var gamer1: Gamepad

    lateinit var gamer2: Gamepad
    lateinit var drive: drivetrain
    lateinit var intake: IntakeSubsystem

    override fun runOpMode(){
        val scheduler = CommandScheduler
        drive = drivetrain(hardwareMap, telemetry)
        intake = IntakeSubsystem(hardwareMap)

        gamer1 = Gamepad(gamepad1)




        waitForStart()

        initBindings()

        while (opModeIsActive()) {
            drive.mecanumEffort(gamer1.left_stick_y,gamer1.left_stick_x,gamer1.right_stick_x)
            drive.write()
            scheduler.run()

        }

    }
    open fun initBindings(){
    }
    open fun addTelemetry(){

    }
}