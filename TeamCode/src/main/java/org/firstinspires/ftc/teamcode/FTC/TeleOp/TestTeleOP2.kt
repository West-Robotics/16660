package org.firstinspires.ftc.teamcode.FTC.TeleOp

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.FTC.TeleOp.tests.yeeter
import org.firstinspires.ftc.teamcode.FTC.mechanisms.BallColor
import org.firstinspires.ftc.teamcode.FTC.mechanisms.drivetrain
import org.firstinspires.ftc.teamcode.FTC.mechanisms.lime.Limelight
import org.firstinspires.ftc.teamcode.setup.Controller
import org.firstinspires.ftc.teamcode.setup.motorSetup
import org.firstinspires.ftc.teamcode.setup.servoSetup
import org.firstinspires.ftc.teamcode.util.Robotconstants
import org.firstinspires.ftc.teamcode.util.ServoConstants
import kotlin.math.PI
import kotlin.math.pow

abstract class TestTeleOP2 : LinearOpMode(){
    abstract var blue: Boolean
    enum class State(){
        INTAKING,
        MANUAL_INTAKING,
        FULL,
        PREPARE_LAUNCH,
        MANUAL_PREPARE_LAUNCH,
        LAUNCHING,
        MANUAL_LAUNCHING,
        LAUNCHING_ORDER,
        BACKUP
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
        var lastSpindexerPos = Robotconstants.SpindexerPosition.ONE_INTAKE

        var state = State.MANUAL_INTAKING

        var yeeterToggle = false
        var launcherToggle = false
        var limeToggle = false
        var intakeToggle = false
        val afterIntakingtimer = ElapsedTime()
        val intakingTimer = ElapsedTime()
        val launchingTimer = ElapsedTime()
        var intaking = false
        if (blue){
            lime.init(8)
        } else{
            lime.init(9)
        }

        var rightTriggerCounter = 0

        fun intakeLoop(){
            if (controller1.bOnce()){
                intakeToggle = !intakeToggle
            }

            if (intakeToggle){
                intake.effort = 1.0
            } else{
                intake.effort = 0.0
            }
        }
        var intakeCounter = 0
        fun spindexerAutomations(){
            if (intakeCounter >=3){
                return
            }
            if(!lime.ifSpindexerFull() && !intaking && afterIntakingtimer.seconds()>0.8 ){
                if (lime.hasBall()){
                    intaking = true
                    intakingTimer.reset()
                }
            }
            if (intaking && intakingTimer.seconds()>0.59){
                lime.updateBallColor(spindexerpos)
                afterIntakingtimer.reset()
                intaking = false
                when(spindexerpos){
                    Robotconstants.SpindexerPosition.ONE_INTAKE ->{
                        spindexerpos = Robotconstants.SpindexerPosition.TWO_INTAKE
                        lastSpindexerPos = Robotconstants.SpindexerPosition.ONE_INTAKE
                        intakeCounter+=1
                    }
                    Robotconstants.SpindexerPosition.TWO_INTAKE -> {
                        spindexerpos = Robotconstants.SpindexerPosition.THREE_INTAKE
                        lastSpindexerPos = Robotconstants.SpindexerPosition.TWO_INTAKE
                        intakeCounter+=1
                    }
                    else ->{
                        spindexerpos = Robotconstants.SpindexerPosition.ONE_INTAKE
                        lastSpindexerPos = Robotconstants.SpindexerPosition.THREE_INTAKE
                        intakeCounter+=1
                    }
                }
            }
        }
        fun goToLastPositionSpindexer(){
            if (lime.intakePosOne.position == lastSpindexerPos){
                lime.intakePosOne.color = BallColor.EMPTY
            } else if (lime.intakePosTwo.position == lastSpindexerPos){
                lime.intakePosTwo.color = BallColor.EMPTY
            } else if (lime.intakePosThree.position == lastSpindexerPos){
                lime.intakePosThree.color = BallColor.EMPTY
            }
            spindexerpos = lastSpindexerPos
            afterIntakingtimer.reset()
        }

        var manualIntaking = true
        waitForStart()
        afterIntakingtimer.reset()
        intakingTimer.reset()
        launchingTimer.reset()
        val afterShootingTimer = ElapsedTime()
        lime.update()
        while (opModeIsActive()){
            controller1.update()
            val rx = controller1.right_stick_x
            drive.odoUpdate()
            drive.fieldCentricEffort(-controller1.left_stick_y,controller1.left_stick_x,rx)

            when(state){
                State.INTAKING -> {
                    manualIntaking = false
                    launcher.effort = 0.0
                    yeeter.effort = 0.0
                    lime.addColorTelemetry()
                    intakeLoop()
                    lime.updateColorSensor()
                    spindexerAutomations()
                    spindexer.position = spindexerpos.pos1
                    if (controller1.rightBumperOnce()){
                        state = State.PREPARE_LAUNCH
                    }
                    if (controller1.dpad_UpOnce()){
                        goToLastPositionSpindexer()
                    }
                    if (controller1.dpad_DownOnce()){
                        state = State.MANUAL_INTAKING
                        spindexerpos = Robotconstants.SpindexerPosition.ONE_INTAKE
                        lime.resetBalls()
                    }



                }
                State.MANUAL_INTAKING -> {
                    intakeLoop()
                    manualIntaking = true
                    launcher.effort = 0.0
                    yeeter.effort = 0.0
                    if (controller1.rightBumperOnce()){
                        state = State.PREPARE_LAUNCH
                    }
                    if(controller1.right_trigger > 0.1){
                        rightTriggerCounter += 1
                    } else{
                        rightTriggerCounter = 0
                    }
                    if (rightTriggerCounter == 1){
                        when(spindexerpos){
                            Robotconstants.SpindexerPosition.TWO_INTAKE ->{
                                spindexerpos = Robotconstants.SpindexerPosition.THREE_INTAKE
                            }
                            Robotconstants.SpindexerPosition.THREE_INTAKE -> {
                                spindexerpos = Robotconstants.SpindexerPosition.ONE_INTAKE
                            }
                            else ->{
                                spindexerpos = Robotconstants.SpindexerPosition.TWO_INTAKE
                            }
                        }

                    }
                    spindexer.position = spindexerpos.pos1
                }
                State.FULL ->{
                    if(controller1.leftBumperOnce()){
                        state = State.PREPARE_LAUNCH
                    }
                    if(controller1.startOnce()){
                        state = State.INTAKING

                    }
                }
                State.PREPARE_LAUNCH -> {
                    intake.effort = 0.0
                    if(controller1.rightBumperOnce()){
                        state = State.MANUAL_INTAKING
                    }
                    if (rx>0.1){
                        state = State.MANUAL_INTAKING
                    }
                    lime.update()
                    lime.updateAlignment()
                    drive.fieldCentricEffort(-controller1.left_stick_y,controller1.left_stick_x,-lime.AlignmentPower)
                    val distance = lime.distance
                    val p = (distance+3.0)/39.37 - 3.55
                    val angleInRadians = 0.436 + .14*p +0.02*p.pow(2) + 0.024*p.pow(3 )+ 0.01.pow(4) + 0.001*p.pow(5)
                    val differenceInServo = 0.36 * angleInRadians * (180/(25*PI))
                    hood.position = 0.82 - differenceInServo
                    if (distance>50.0) { // 0.95 higher, 0.87
                        launcher.effort = 0.95 * 12.8 / hardwareMap.voltageSensor.iterator().next().voltage  //TODO EDIT AND TEST
                    } else{
                        launcher.effort = 0.9 * 12.8 / hardwareMap.voltageSensor.iterator().next().voltage  //TODO EDIT AND TEST
                    }
                    yeeter.effort = 1.0
                    if (controller1.left_trigger>0.1){
                        state = State.LAUNCHING
                        launchingTimer.reset()
                    }

                    if (controller1.aOnce()){
                        state = State.MANUAL_LAUNCHING
                        spindexerpos = Robotconstants.SpindexerPosition.ONE_SHOOT
                    }
                }
                State.MANUAL_PREPARE_LAUNCH ->{

                }
                State.MANUAL_LAUNCHING ->{
                    intake.effort = 0.0
                    if(controller1.aOnce()) {
                        when (spindexerpos) {
                            Robotconstants.SpindexerPosition.ONE_SHOOT -> {
                                spindexerpos = Robotconstants.SpindexerPosition.TWO_SHOOT
                            }

                            Robotconstants.SpindexerPosition.TWO_SHOOT -> {
                                spindexerpos = Robotconstants.SpindexerPosition.THREE_SHOOT
                            }
                            else -> {
                                if (manualIntaking){
                                    state = State.MANUAL_INTAKING
                                } else{
                                    state = State.INTAKING
                                }
                                spindexerpos = Robotconstants.SpindexerPosition.TWO_INTAKE
                            }
                        }
                    }
                    spindexer.position = spindexerpos.pos1
                }
                State.LAUNCHING -> {
                    intake.effort = 0.0
                    if(controller1.startOnce()){
                        state = State.INTAKING
                    }

                    lime.update()
                    val distance = lime.distance
                    val p = (distance+3.0)/39.37 - 3.55
                    val angleInRadians = 0.436 + .14*p +0.02*p.pow(2) + 0.024*p.pow(3 )+ 0.01.pow(4) + 0.001*p.pow(5)
                    val differenceInServo = 0.36 * angleInRadians * (180/(25*PI))
                    hood.position = 0.82 - differenceInServo
                    if (distance<50.0) {
                        launcher.effort = 0.95 * 12.8 / hardwareMap.voltageSensor.iterator().next().voltage  //TODO EDIT AND TEST
                    } else{
                        launcher.effort = 0.87 * 12.8 / hardwareMap.voltageSensor.iterator().next().voltage  //TODO EDIT AND TEST
                    }
                    yeeter.effort = 1.0
                    if (launchingTimer.seconds()<0.5){
                        spindexerpos = Robotconstants.SpindexerPosition.ONE_SHOOT
                    }
                    if (launchingTimer.seconds()>=1.0 && launchingTimer.seconds() <1.0){
                        spindexerpos = Robotconstants.SpindexerPosition.TWO_SHOOT
                    }
                    if (launchingTimer.seconds()>=2.0 && launchingTimer.seconds() < 1.5){
                        spindexerpos = Robotconstants.SpindexerPosition.THREE_SHOOT
                    }
                    if (launchingTimer.seconds()>=3.0){
                        spindexerpos = Robotconstants.SpindexerPosition.ONE_INTAKE
                        lime.resetBalls()
                        afterIntakingtimer.reset()
                        state= State.MANUAL_INTAKING
                    }
                    spindexer.position = spindexerpos.pos1
                }
                State.LAUNCHING_ORDER->{

                }
                State.BACKUP ->{

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