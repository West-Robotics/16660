package org.firstinspires.ftc.teamcode.TeleOp.Scrim.backups

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.setup.Controller
import org.firstinspires.ftc.teamcode.setup.motorSetup
import org.firstinspires.ftc.teamcode.setup.servoSetup
import org.firstinspires.ftc.teamcode.util.ServoConstants
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.mechanisms.drivetrain
import org.firstinspires.ftc.teamcode.setup.CRservoSetup
import org.firstinspires.ftc.teamcode.util.CRServoConstants
import com.qualcomm.robotcore.hardware.ColorSensor
import com.qualcomm.robotcore.robot.Robot
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.util.Robotconstants

@TeleOp(name = "2backup2")
class backup2 : LinearOpMode(){

    enum class Game_State(){
        PREPARE_LAUNCH,
        LAUNCHING,
        INTAKING
    }
    override fun runOpMode() {
        var gamestater = Game_State.INTAKING
        var spindexerpos = Robotconstants.SpindexerPosition.ONE_INTAKE
        val intake = motorSetup(
            hardwareMap,
            "intake",
            DcMotorSimple.Direction.FORWARD,
            DcMotor.ZeroPowerBehavior.BRAKE
        )
        val spindexer = servoSetup(hardwareMap,"spindexer", ServoConstants.AXON)
        val controller1 = Controller(gamepad1)
        val controller2 = Controller(gamepad2)
        val drive = drivetrain(hardwareMap,telemetry)
        val sensor = hardwareMap.get("color") as ColorSensor
        val yeeter1 = CRservoSetup(hardwareMap,"yeeter1", CRServoConstants.GOBILDA_SPEED,
            DcMotorSimple.Direction.REVERSE)
        val yeeter2 = CRservoSetup(hardwareMap,"yeeter2", CRServoConstants.GOBILDA_SPEED,
            DcMotorSimple.Direction.FORWARD)

        val launcher = motorSetup(hardwareMap,"launcher", DcMotorSimple.Direction.FORWARD,DcMotor.ZeroPowerBehavior.BRAKE)
        var atoggle = true
        var toggle = false
        var btoggle = false
        var timer = ElapsedTime()
        var bumperToggle = false
        timer.reset()
        spindexer.position = 0.98
        waitForStart()
        while (opModeIsActive()) {
            controller1.update()
            controller2.update()
            drive.odoUpdate()
            drive.fieldCentricEffort(-controller1.left_stick_y,controller1.left_stick_x,controller1.right_stick_x)

            when (gamestater){
                Game_State.INTAKING->{
                    launcher.effort = 0.0
                    yeeter1.effort = 0.0
                    yeeter2.effort = 0.0
                    if (controller1.xOnce()){
                        toggle = !toggle
                    }
                    if (controller1.bOnce()){
                        btoggle = !btoggle
                    }

                    if (toggle){
                        yeeter2.effort = 1.0
                        yeeter1.effort = 1.0
                    } else{
                        yeeter2.effort = 0.0
                        yeeter1.effort = 0.0
                    }

                    if (controller1.aOnce()){
                        gamestater = Game_State.PREPARE_LAUNCH
                        timer.reset()
                        timer.startTime()
                    }
                    if (controller1.right_trigger>0){
                        launcher.effort = controller1.right_trigger
                    }

                    if (controller1.dpad_RightOnce()){
                        spindexer.position = spindexer.position +0.025
                    }
                    if (controller1.dpad_LeftOnce()){
                        spindexer.position = spindexer.position -0.025
                    }
                    if (controller1.dpad_DownOnce()){
                        when(spindexerpos){
                            Robotconstants.SpindexerPosition.ONE_INTAKE ->{
                                spindexerpos = Robotconstants.SpindexerPosition.TWO_INTAKE
                            }
                            Robotconstants.SpindexerPosition.TWO_INTAKE -> {
                                spindexerpos = Robotconstants.SpindexerPosition.THREE_INTAKE
                            }
                            Robotconstants.SpindexerPosition.THREE_INTAKE ->{
                                spindexerpos = Robotconstants.SpindexerPosition.ONE_INTAKE
                            } else ->{
                                spindexerpos = Robotconstants.SpindexerPosition.ONE_INTAKE
                            }
                        }
                        spindexer.position = spindexerpos.pos1
                    }

                }
                Game_State.PREPARE_LAUNCH -> {
                    spindexerpos = Robotconstants.SpindexerPosition.ONE_SHOOT
                    spindexer.position = spindexerpos.pos1
                    yeeter1.effort = 0.0
                    yeeter2.effort = 0.0
                    launcher.effort = 1.0
                    if (controller1.aOnce()){
                        gamestater = Game_State.INTAKING
                    }
                    if (launcher.velocity < -1800 || timer.seconds()>1.2){
                        gamestater = Game_State.LAUNCHING
                        timer.reset()
                        timer.startTime()
                    }
                }
                Game_State.LAUNCHING -> {
                    if (controller1.aOnce()){
                        gamestater = Game_State.INTAKING
                    }
                    launcher.effort =0.8
                    spindexer.position = spindexerpos.pos1
                    yeeter1.effort = 1.0
                    yeeter2.effort = 1.0
                    if (timer.seconds() <2.0){
                        spindexerpos = Robotconstants.SpindexerPosition.ONE_SHOOT
                    } else if (2.0<=timer.seconds() && timer.seconds()<4.0){
                        spindexerpos = Robotconstants.SpindexerPosition.TWO_SHOOT
                    } else if (4.0<=timer.seconds() && timer.seconds()<6.0){
                        spindexerpos = Robotconstants.SpindexerPosition.THREE_SHOOT
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



            //launcher.effort = controller1.right_trigger

            /*
            if (controller1.dpad_LeftOnce()){
                spindexer.position = Robotconstants.SpindexerPosition.ONE_INTAKE.pos1
            }
            if (controller1.dpad_UpOnce()){
                spindexer.position = Robotconstants.SpindexerPosition.TWO_INTAKE.pos1
            }
            if (controller1.dpad_RightOnce()){
                spindexer.position = Robotconstants.SpindexerPosition.THREE_INTAKE.pos1
            }
            if (controller1.dpad_DownOnce()){
                spindexer.position = Robotconstants.SpindexerPosition.ONE_SHOOT.pos1
            }
            if (controller1.xOnce()){
                spindexer.position = Robotconstants.SpindexerPosition.TWO_SHOOT.pos1
            }
            if (controller1.yOnce()){
                spindexer.position = Robotconstants.SpindexerPosition.THREE_SHOOT.pos1
            }

            if (controller1.dpad_RightOnce()){
                spindexer.position = spindexer.position +0.05
            }
            if (controller1.dpad_UpOnce()){
                spindexer.position = 0.5
            }
            if (controller1.dpad_LeftOnce()){
                spindexer.position = spindexer.position -0.05
            }

            if (controller1.aOnce()){
                toggle = !toggle
            }
             */
            telemetry.addData("velocity",launcher.velocity)
            telemetry.addData("Red",sensor.red())
            telemetry.addData("Green",sensor.green())
            telemetry.addData("Blue",sensor.blue())
            telemetry.addData("yeeter1",yeeter1.effort)
            telemetry.addData("yeeter2",yeeter2.effort)
            telemetry.addData("intake",intake.effort)
            telemetry.addData("spindexer",spindexer.position)
            telemetry.addLine("B Intake, A Automation, right trigger launcher variable power,")
            telemetry.addLine("right dpad for right, left dpad for left; spindexer")
            telemetry.addLine("X for yeeter, dpad down for cycling spindexer")
            telemetry.update()
            drive.write()
            intake.write()
            spindexer.write()
            launcher.write()
            yeeter1.write()
            yeeter2.write()

        }
    }
}