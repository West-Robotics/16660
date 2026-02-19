package org.firstinspires.ftc.teamcode.architecture.teleop

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.architecture.CommandScheduler
import org.firstinspires.ftc.teamcode.architecture.Gamepader
import org.firstinspires.ftc.teamcode.architecture.subsystems.IntakeSubsystem
import org.firstinspires.ftc.teamcode.FTC.mechanisms.drivetrain


abstract class CommandOpMode : LinearOpMode(){
    lateinit var gamer1: Gamepader

    lateinit var gamer2: Gamepader
    lateinit var drive: drivetrain
    lateinit var intake: IntakeSubsystem


    final override fun runOpMode(){
        drive = drivetrain(hardwareMap, telemetry)
        intake = IntakeSubsystem(hardwareMap)
        CommandScheduler.registerSubsystem(intake)

        gamer1 = Gamepader(gamepad1)

        waitForStart()

        initBindings()

        while (opModeIsActive()) {
            drive.mecanumEffort(gamer1.left_stick_y,gamer1.left_stick_x,gamer1.right_stick_x)
            CommandScheduler.run(telemetry)
            drive.write()
            telemetry.addData("activeCommands", CommandScheduler.scheduledCommands)
            telemetry.addData("subsystems", CommandScheduler.subsystems)
            telemetry.addData("triggers", CommandScheduler.triggers)
            val bindings = mutableListOf<MutableList<Function0<Unit>>>()
            CommandScheduler.triggers.forEach { bindings.add(it.bindings) }
            val current = mutableListOf<Boolean>()
            val previous = mutableListOf<Boolean>()
            CommandScheduler.triggers.forEach { current.add(it.current)}
            CommandScheduler.triggers.forEach { previous.add(it.previous)}
            val triggerspressed = mutableListOf<Boolean>()
            CommandScheduler.triggers.forEach { triggerspressed.add(it.pressed)}
            telemetry.addData("triggersbindigs",bindings)
            telemetry.addData("triggerscurrent",current)
            telemetry.addData("triggersprevious",previous)
            telemetry.addData("triggers pressed",triggerspressed)
            telemetry.addData("initialized", CommandScheduler.initialized)
            telemetry.update()
        }
        CommandScheduler.cancelAll()
        CommandScheduler.resetAll()

    }
    open fun initBindings(){
    }
    open fun addTelemetry(){

    }
}