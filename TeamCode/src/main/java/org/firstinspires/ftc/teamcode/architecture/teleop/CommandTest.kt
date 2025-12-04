package org.firstinspires.ftc.teamcode.architecture.teleop

import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.architecture.CommandScheduler
import org.firstinspires.ftc.teamcode.architecture.commands.IntakeCommand
import org.firstinspires.ftc.teamcode.architecture.subsystems.IntakeSubsystem
import org.firstinspires.ftc.teamcode.mechanisms.drivetrain
import org.firstinspires.ftc.teamcode.setup.Controller
import org.firstinspires.ftc.teamcode.util.Robotconstants

class CommandTest : LinearOpMode(){
    override fun runOpMode(){

        val drive = drivetrain(hardwareMap, telemetry)
        val intake = IntakeSubsystem(hardwareMap)
        val scheduler = CommandScheduler
        val controller = Controller(gamepad1)
        var intakeToggle = true

        waitForStart()

        while (opModeIsActive()) {
            controller.update()
            drive.fieldCentricEffort(controller.left_stick_y,controller.left_stick_x,controller.right_stick_x)

            if (controller.aOnce()){
                scheduler.schedule(IntakeCommand(intake,intakeToggle))
                intakeToggle = !intakeToggle
            }

            

            drive.write()
            scheduler.run()

        }

    }

}