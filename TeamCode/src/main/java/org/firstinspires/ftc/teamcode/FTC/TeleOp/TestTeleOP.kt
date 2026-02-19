package org.firstinspires.ftc.teamcode.FTC.TeleOp

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.FTC.TeleOp.tests.yeeter
import org.firstinspires.ftc.teamcode.FTC.mechanisms.drivetrain
import org.firstinspires.ftc.teamcode.FTC.mechanisms.lime.Limelight
import org.firstinspires.ftc.teamcode.setup.Controller
import org.firstinspires.ftc.teamcode.setup.motorSetup
import org.firstinspires.ftc.teamcode.setup.servoSetup
import org.firstinspires.ftc.teamcode.util.Robotconstants
import org.firstinspires.ftc.teamcode.util.ServoConstants
import kotlin.math.PI
import kotlin.math.pow

@TeleOp(name = "ColorSensorRead")
class TestTeleOP : LinearOpMode(){

    enum class State(){
        INTAKING,
        PREPARE_LAUNCH,
        LAUNCHING
    }

    override fun runOpMode() {
        val drive = drivetrain(hardwareMap,telemetry)
        val controller1 = Controller(gamepad1)
        val launcher = motorSetup(hardwareMap,"launcher", DcMotorSimple.Direction.FORWARD,DcMotor.ZeroPowerBehavior.BRAKE)
        val hood = servoSetup(hardwareMap,"angle", ServoConstants.AXON)
        val intake = motorSetup(hardwareMap, "intake", DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE)
        val yeeter = motorSetup(hardwareMap, "yeeter", DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.BRAKE)
        val spindexer = servoSetup(hardwareMap,"spindexer", ServoConstants.AXON)
        val lime = Limelight(hardwareMap,telemetry)
        var spindexerpos = Robotconstants.SpindexerPosition.ONE_INTAKE

        var state = State.INTAKING

        lime.init(8)
        var yeeterToggle = false
        var launcherToggle = false
        var limeToggle = false
        var intakeToggle = false
        val afterIntakingtimer = ElapsedTime()
        val intakingTimer = ElapsedTime()
        var intaking = false
        waitForStart()
        afterIntakingtimer.reset()
        intakingTimer.reset()

        lime.update()
        while (opModeIsActive()){
            controller1.update()

            when(state){
                State.INTAKING -> {
                    lime.addColorTelemetry()
                    if (controller1.bOnce()){
                        intakeToggle = !intakeToggle
                    }

                    if (intakeToggle){
                        intake.effort = 1.0
                    } else{
                        intake.effort = 0.0
                    }
                    lime.updateColorSensor()




                }
                State.PREPARE_LAUNCH -> {

                }
                State.LAUNCHING -> {

                }

            }

            lime.addTelemetry()
            telemetry.addData("position",hood.position)
            hood.write()  //0.82 max down,  0.47 max up
            drive.write()
            launcher.write()
            yeeter.write()
            spindexer.write()
            intake.write()
            telemetry.update()
        }
    }
}