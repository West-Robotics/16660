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

@TeleOp(name = "old1")
class TeleOP : LinearOpMode(){

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

        lime.init(8)
        var yeeterToggle = false
        var launcherToggle = false
        var limeToggle = false
        var intakeToggle = false
        var timer = ElapsedTime()
        waitForStart()
        timer.reset()


        while (opModeIsActive()){
            controller1.update()
            drive.odoUpdate()
            lime.update()


            if (controller1.rightBumperOnce()){
                limeToggle = !limeToggle
                lime.resetAlignment()
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

            if (controller1.leftBumperOnce()){
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
                spindexer.position = spindexerpos.pos1
            }

            if (!limeToggle){
                drive.fieldCentricEffort(-controller1.left_stick_y,controller1.left_stick_x,controller1.right_stick_x)
            } else{
                lime.updateAlignment()
                drive.fieldCentricEffort(-controller1.left_stick_y,controller1.left_stick_x,-lime.AlignmentPower)
            }
            if (controller1.bOnce()){
                intakeToggle = !intakeToggle
            }

            if (intakeToggle){
                intake.effort = 1.0
            } else{
                intake.effort = 0.0
            }

            if (controller1.yOnce()){
                launcherToggle = !launcherToggle
            }

            if (launcherToggle){
                val p = (lime.distance+3.0)/39.37 - 3.55
                val angleInRadians = 0.436 + .14*p +0.02*p.pow(2) + 0.024*p.pow(3 )+ 0.01.pow(4) + 0.001*p.pow(5)
                val differenceInServo = 0.36 * angleInRadians * (180/(25*PI))
                hood.position = 0.82 - differenceInServo
                launcher.effort = 0.95 * 12.8/hardwareMap.voltageSensor.iterator().next().voltage
            } else{
                launcher.effort = 0.0
            }


            if (controller1.xOnce()){
                yeeterToggle = !yeeterToggle
            }

            if (yeeterToggle){
                yeeter.effort = 1.0
            } else{
                yeeter.effort = 0.0
            }
            /*
            if (controller1.dpad_LeftOnce()){
                hood.position = hood.position - 0.02
            }
            if (controller1.dpad_RightOnce()){
                hood.position = hood.position + 0.02
            }
            if (controller1.dpad_UpOnce()){
                hood.position = 1.0
            }
            if (controller1.dpad_DownOnce()){
                hood.position = 0.0
            }

             */
            telemetry.addData("position",hood.position)
            telemetry.addData("spindexerpos",spindexerpos)
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