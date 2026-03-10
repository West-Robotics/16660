package org.firstinspires.ftc.teamcode.FTC.TeleOp

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.FTC.TeleOp.Subsystems.IntakeSubsystem
import org.firstinspires.ftc.teamcode.FTC.TeleOp.Subsystems.LauncherSubsystem
import org.firstinspires.ftc.teamcode.FTC.TeleOp.Subsystems.RobotState
import org.firstinspires.ftc.teamcode.FTC.TeleOp.Subsystems.SpindexerSubsystem
import org.firstinspires.ftc.teamcode.FTC.mechanisms.drivetrain
import org.firstinspires.ftc.teamcode.FTC.mechanisms.lime.Limelight
import org.firstinspires.ftc.teamcode.setup.Controller


class CleanTeleOp : LinearOpMode() {

    enum class State(){
        INTAKING,
        PREPARE_LAUNCH,
        LAUNCHING
    }

    override fun runOpMode() {
        val lime = Limelight(hardwareMap, telemetry)
        lime.init(8)  // 8 is for blue goal 9 is for red goal
        val drive = drivetrain(hardwareMap, telemetry)
        var robotState = RobotState()
        val controller1 = Controller(gamepad1)
        val intake = IntakeSubsystem(hardwareMap)
        val launcher = LauncherSubsystem(hardwareMap, drive, controller1, lime)
        val spindexer = SpindexerSubsystem(hardwareMap,robotState)

        waitForStart()
        lime.update()

        while (opModeIsActive()){
            controller1.update()
            drive.odoUpdate()
            drive.fieldCentricEffort(-controller1.left_stick_y,controller1.left_stick_x,controller1.right_stick_x)

            when(robotState.current){
                State.INTAKING ->{
                    launcher.zero() // set launcher and yeeter to no power
                    if (controller1.bOnce()){
                        intake.toggle()   // turn intake off or on
                    }
                    if (controller1.leftBumperOnce()){
                        spindexer.intakeCycle()    // switch spindexer intake positions
                    }
                    if (controller1.rightBumperOnce()){
                        robotState.current = State.PREPARE_LAUNCH   // prepare launch
                    }

                }
                State.PREPARE_LAUNCH -> {
                    // intake is set to no power limelight is updated, alignment, hood angle, and power set
                    intake.zero()
                    launcher.updateLime()
                    launcher.align()
                    launcher.setHoodAngle()
                    launcher.power() // make PID in LauncherSubsystem

                    if(controller1.aOnce()){
                        spindexer.initLaunchCycle()  // goes to Launching State, goes to first shooting position
                    }
                }
                State.LAUNCHING -> {
                    intake.zero()
                    if(controller1.aOnce()){  // cycles through the shooting states then
                        spindexer.launchCycle() // goes to Intaking State
                    }
                }
            }
            drive.write()
            intake.write()
            spindexer.write()
            launcher.write()
        }
    }
}