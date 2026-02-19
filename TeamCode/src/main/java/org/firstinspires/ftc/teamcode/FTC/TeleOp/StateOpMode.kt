package org.firstinspires.ftc.teamcode.FTC.TeleOp

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

@TeleOp(name = "backup")
abstract class StateOpMode : LinearOpMode(){

    enum class Game_State(){
        PREPARE_LAUNCH,
        LAUNCHING,
        INTAKING
    }

    open abstract var blueTeamColor:Boolean
    
    override fun runOpMode() {
        var gamestater = Game_State.INTAKING
        var spindexerpos = Robotconstants.SpindexerPosition.ONE_INTAKE
        val hood = servoSetup(hardwareMap,"angle", ServoConstants.AXON)
        val intake = motorSetup(hardwareMap, "intake", DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE)
        val yeeter = motorSetup(hardwareMap, "yeeter", DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.BRAKE)
        val spindexer = servoSetup(hardwareMap,"spindexer", ServoConstants.AXON)
        val controller1 = Controller(gamepad1)
        val drive = drivetrain(hardwareMap,telemetry)
        val sensor = hardwareMap.get("color") as ColorSensor
        val launcher = motorSetup(hardwareMap,"launcher", DcMotorSimple.Direction.FORWARD,DcMotor.ZeroPowerBehavior.BRAKE)
        var toggle = false
        var btoggle = false
        var limeToggle = false

        val timer = ElapsedTime()
        val telemetryTimer = ElapsedTime()
        var lastTime = System.nanoTime()

        waitForStart()

        spindexer.position = spindexerpos.pos1
        hood.position = 0.1
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
                    launcher.effort = 0.0
                    yeeter.effort = 0.0
                    if (controller1.xOnce()){
                        toggle = !toggle
                    }
                    if (controller1.bOnce()){
                        btoggle = !btoggle
                    }
                    if (toggle){
                        yeeter.effort = 1.0
                    } else{
                        yeeter.effort = 0.0
                    }

                    if (controller1.aOnce()){
                        gamestater = Game_State.PREPARE_LAUNCH
                        timer.reset()
                        timer.startTime()
                    }
                    if (controller1.right_trigger>0){
                        launcher.effort = controller1.right_trigger
                    }

                    if (controller1.dpad_RightOnce()&& hood.position < 0.16){
                        hood.position = hood.position +0.01
                    }
                    if (controller1.dpad_LeftOnce() && hood.position > 0.1){
                        hood.position = hood.position -0.01
                    }
                    if (controller1.dpad_UpOnce()){
                        hood.position = 0.1
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
                        spindexer.position = spindexerpos.pos1
                    }
                    if (controller1.rightBumperOnce()){
                        when (spindexerpos){
                            Robotconstants.SpindexerPosition.ONE_SHOOT ->{
                                spindexerpos = Robotconstants.SpindexerPosition.THREE_SHOOT
                            }
                            Robotconstants.SpindexerPosition.THREE_SHOOT ->{
                                spindexerpos = Robotconstants.SpindexerPosition.TWO_SHOOT
                            } else -> {
                            spindexerpos = Robotconstants.SpindexerPosition.ONE_SHOOT
                        }
                        }
                        spindexer.position = spindexerpos.pos1
                    }

                }
                Game_State.PREPARE_LAUNCH -> {
                    spindexerpos = Robotconstants.SpindexerPosition.ONE_SHOOT
                    spindexer.position = spindexerpos.pos1
                    yeeter.effort = 0.0
                    launcher.effort = 1.0
                    if (controller1.aOnce()){
                        gamestater = Game_State.INTAKING
                    }
                    if (timer.seconds()>2.0){
                        gamestater = Game_State.LAUNCHING
                        timer.reset()
                        timer.startTime()
                    }
                }
                Game_State.LAUNCHING -> {
                    if (controller1.aOnce()){
                        gamestater = Game_State.INTAKING
                    }
                    launcher.effort = 1.0
                    spindexer.position = spindexerpos.pos1
                    yeeter.effort = 1.0
                    if (timer.seconds() <2.0){
                        spindexerpos = Robotconstants.SpindexerPosition.ONE_SHOOT
                    } else if (2.0<=timer.seconds() && timer.seconds()<4.0){
                        spindexerpos = Robotconstants.SpindexerPosition.THREE_SHOOT
                    } else if (4.0<=timer.seconds() && timer.seconds()<6.0){
                        spindexerpos = Robotconstants.SpindexerPosition.TWO_SHOOT
                    } else if (6.0<=timer.seconds() && timer.seconds()<7.0){
                        spindexerpos = Robotconstants.SpindexerPosition.ONE_INTAKE
                        spindexer.position = spindexerpos.pos1
                    } else {
                        gamestater = Game_State.INTAKING
                    }
                }

            }
            if(btoggle){
                intake.effort = 1.0
            } else{
                intake.effort = 0.0
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

            hood.write()
            drive.write()
            intake.write()
            spindexer.write()
            launcher.write()
            yeeter.write()

            val now = System.nanoTime()
            val dt = (now-lastTime) / 1e6
            lastTime = now
            telemetry.addData("SystemLoop", dt)
            telemetry.update()
        }
    }
}