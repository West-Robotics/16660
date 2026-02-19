package org.firstinspires.ftc.teamcode.FTC.TeleOp.Scrim.backups

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.setup.Controller
import org.firstinspires.ftc.teamcode.setup.motorSetup
import org.firstinspires.ftc.teamcode.setup.servoSetup
import org.firstinspires.ftc.teamcode.util.ServoConstants
import org.firstinspires.ftc.teamcode.FTC.mechanisms.drivetrain
import com.qualcomm.robotcore.hardware.ColorSensor
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.util.Robotconstants
import org.firstinspires.ftc.teamcode.FTC.Hardware

@TeleOp(name = "backup2")
class backup2 : LinearOpMode(){

    enum class Game_State(){
        PREPARE_LAUNCH,
        LAUNCHING,
        INTAKING
    }
    override fun runOpMode() {
        var gamestater = Game_State.INTAKING
        var spindexerpos = Robotconstants.SpindexerPosition.ONE_INTAKE
        val hardware = Hardware(hardwareMap)
        val controller1 = Controller(gamepad1)
        val drive = drivetrain(hardwareMap,telemetry)
        val sensor = hardwareMap.get("color") as ColorSensor
        var toggle = false
        var btoggle = false
        var limeToggle = false

        val timer = ElapsedTime()
        val telemetryTimer = ElapsedTime()
        var lastTime = System.nanoTime()


        waitForStart()

        hardware.spindexer.position = spindexerpos.pos1
        hardware.hood.position = 0.1
        while (opModeIsActive()) {
            controller1.update()
            drive.odoUpdate()
            if (controller1.leftBumperOnce()){
                limeToggle = !limeToggle
            }

            if (!limeToggle){
                drive.fieldCentricEffort(-controller1.left_stick_y,controller1.left_stick_x,controller1.right_stick_x)
            }

            when (gamestater){
                Game_State.INTAKING->{
                    hardware.launcher.effort = 0.0
                    hardware.yeeter.effort = 0.0
                    if (controller1.xOnce()){
                        toggle = !toggle
                    }
                    if (controller1.bOnce()){
                        btoggle = !btoggle
                    }
                    if (toggle){
                        hardware.yeeter.effort = 1.0
                    } else{
                        hardware.yeeter.effort = 0.0
                    }

                    if (controller1.aOnce()){
                        gamestater = Game_State.PREPARE_LAUNCH
                        timer.reset()
                        timer.startTime()
                    }
                    if (controller1.right_trigger>0){
                        hardware.launcher.effort = controller1.right_trigger
                    }

                    if (controller1.dpad_RightOnce()&& hardware.hood.position < 0.16){
                        hardware.hood.position = hardware.hood.position +0.01
                    }
                    if (controller1.dpad_LeftOnce() && hardware.hood.position > 0.1){
                        hardware.hood.position = hardware.hood.position -0.01
                    }
                    if (controller1.dpad_UpOnce()){
                        hardware.hood.position = 0.1
                    }

                    if (controller1.dpad_DownOnce()){
                        when(spindexerpos){
                            Robotconstants.SpindexerPosition.ONE_INTAKE ->{
                                spindexerpos = Robotconstants.SpindexerPosition.TWO_INTAKE
                            }
                            Robotconstants.SpindexerPosition.TWO_INTAKE -> {
                                spindexerpos = Robotconstants.SpindexerPosition.THREE_INTAKE
                            }
                             else ->{
                                spindexerpos = Robotconstants.SpindexerPosition.ONE_INTAKE
                            }
                        }
                        hardware.spindexer.position = spindexerpos.pos1
                    }
                    if (controller1.rightBumperOnce()){
                        when (spindexerpos){
                            Robotconstants.SpindexerPosition.ONE_SHOOT ->{
                                spindexerpos = Robotconstants.SpindexerPosition.TWO_SHOOT
                            }
                            Robotconstants.SpindexerPosition.TWO_SHOOT ->{
                                spindexerpos = Robotconstants.SpindexerPosition.THREE_SHOOT
                            } else -> {
                                spindexerpos = Robotconstants.SpindexerPosition.ONE_SHOOT
                            }
                        }
                        hardware.spindexer.position = spindexerpos.pos1
                    }

                }
                Game_State.PREPARE_LAUNCH -> {
                    spindexerpos = Robotconstants.SpindexerPosition.ONE_SHOOT
                    hardware.spindexer.position = spindexerpos.pos1
                    hardware.yeeter.effort = 0.0
                    hardware.launcher.effort = 1.0
                    if (controller1.aOnce()){
                        gamestater = Game_State.INTAKING
                    }
                    if (timer.seconds()>2.3){
                        gamestater = Game_State.LAUNCHING
                        timer.reset()
                        timer.startTime()
                    }
                }
                Game_State.LAUNCHING -> {
                    if (controller1.aOnce()){
                        gamestater = Game_State.INTAKING
                    }
                    hardware.launcher.effort = 1.0
                    hardware.spindexer.position = spindexerpos.pos1
                    hardware.yeeter.effort = 1.0
                    if (timer.seconds() <1.0){
                        spindexerpos = Robotconstants.SpindexerPosition.ONE_SHOOT
                    } else if (1.0<=timer.seconds() && timer.seconds()<2.0){
                        spindexerpos = Robotconstants.SpindexerPosition.TWO_SHOOT
                    } else if (2.0<=timer.seconds() && timer.seconds()<3.0){
                        spindexerpos = Robotconstants.SpindexerPosition.THREE_SHOOT
                    } else if (3.0<=timer.seconds() && timer.seconds()<2.0){
                        spindexerpos = Robotconstants.SpindexerPosition.ONE_INTAKE
                        hardware.spindexer.position = spindexerpos.pos1
                    } else {
                        gamestater = Game_State.INTAKING
                    }
                }

            }
            if(btoggle){
                hardware.intake.effort = 1.0
            } else{
                hardware.intake.effort = 0.0
            }

            /*
            if (telemetryTimer.milliseconds() > 150){
                drive.addTelemetry()
                telemetry.addData("velocity",launcher.velocity)
                telemetry.addData("hood",hood.position)
                telemetry.addData("Red",sensor.red())
                telemetry.addData("Green",sensor.green())
                telemetry.addData("Blue",sensor.blue())
                telemetry.addData("intake",intake.effort)
                telemetry.addData("spindexer",spindexer.position)
                telemetry.addLine("B Intake, A Automation, right trigger launcher variable power,")
                telemetry.addLine("right dpad for right, left dpad for left; spindexer")
                telemetry.addLine("X for yeeter, dpad down for cycling spindexer")

            }

             */

            hardware.hood.write()
            drive.write()
            hardware.intake.write()
            hardware.spindexer.write()
            hardware.launcher.write()
            hardware.yeeter.write()

            val now = System.nanoTime()
            val dt = (now-lastTime) / 1e6
            lastTime = now
            telemetry.addData("velocity", hardware.launcher.velocity)
            telemetry.addData("SystemLoop", dt)
            telemetry.update()
        }
    }
}