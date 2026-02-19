package org.firstinspires.ftc.teamcode.FTC.autonomous

import androidx.core.graphics.component1
import com.pedropathing.follower.Follower
import com.pedropathing.geometry.BezierLine
import com.pedropathing.geometry.Pose
import com.pedropathing.paths.PathChain
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.FTC.Hardware
import org.firstinspires.ftc.teamcode.FTC.TeleOp.tests.yeeter
import org.firstinspires.ftc.teamcode.FTC.autonomous.pedrotuning.Constants
import org.firstinspires.ftc.teamcode.FTC.mechanisms.Ball
import org.firstinspires.ftc.teamcode.FTC.mechanisms.BallOrder
import org.firstinspires.ftc.teamcode.FTC.mechanisms.drivetrain
import org.firstinspires.ftc.teamcode.FTC.mechanisms.lime.Limelight
import org.firstinspires.ftc.teamcode.setup.Controller
import org.firstinspires.ftc.teamcode.setup.motorSetup
import org.firstinspires.ftc.teamcode.setup.servoSetup
import org.firstinspires.ftc.teamcode.util.Robotconstants
import org.firstinspires.ftc.teamcode.util.ServoConstants
import kotlin.math.PI
import kotlin.math.pow

abstract class PedroOpMode : LinearOpMode(){

    abstract var blue:Boolean
    lateinit var launcher:motorSetup
    lateinit var hood:servoSetup
    lateinit var intake:motorSetup
    lateinit var yeeter:motorSetup
    lateinit var spindexer:servoSetup
    lateinit var lime: Limelight
    lateinit var follower: Follower
    var spindexerpos = Robotconstants.SpindexerPosition.ONE_INTAKE
    var intakeTimer = ElapsedTime()
    var hasBall = false

    open fun looping(){}


    override fun runOpMode() {
        launcher = motorSetup(hardwareMap,"launcher", DcMotorSimple.Direction.FORWARD,DcMotor.ZeroPowerBehavior.BRAKE)
        hood = servoSetup(hardwareMap,"angle", ServoConstants.AXON)
        intake = motorSetup(hardwareMap, "intake", DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE)
        yeeter = motorSetup(hardwareMap, "yeeter", DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.BRAKE)
        spindexer = servoSetup(hardwareMap,"spindexer", ServoConstants.AXON)
        lime = Limelight(hardwareMap,telemetry)
        lime.init(6)

        follower = Constants.createFollower(hardwareMap)
        spindexer.position = spindexerpos.pos1
        spindexer.write()
        afterIntakingTimer.reset()
        looping()
    }
    fun buildPath(from: Pose, to: Pose): PathChain {
        return follower.pathBuilder()
            .addPath(BezierLine(from, to))
            .setLinearHeadingInterpolation(from.heading, to.heading)
            .build()
    }

    fun initialize(){
        lime.update()
        lime.readObelisk()
    }

    var afterIntakingTimer = ElapsedTime()
    var intaking = false
    var intakingTimer = ElapsedTime()
    var intakeCounter = 0
    var indexing = 0
    val intakeMaxTimer = ElapsedTime()
    fun initIntake():Boolean{
        if(indexing<40) {
            intakeMaxTimer.reset()
            intakeTimer.reset()
            afterIntakingTimer.reset()
            spindexerpos = Robotconstants.SpindexerPosition.TWO_INTAKE
            spindexer.position = spindexerpos.pos1
            indexing +=1
            return false
        } else{ return true}
    }
    fun isDoneIntake(): Boolean{
        if(intakeCounter>=3 || intakeMaxTimer.seconds() >7.0){
            return true
        } else{
            return false
        }
    }
    fun intakeLoop(path: PathChain){
            follower.followPath(path,0.85,true)
            intake.effort = 1.0
            lime.updateColorSensor()
            if (intakeCounter>=3){
                return
            }
            if(!lime.ifSpindexerFull() && !intaking && afterIntakingTimer.seconds()>0.3 ){
                if (lime.hasBall()){
                    intaking = true
                    intakingTimer.reset()
                }
            }
            if (intaking && intakingTimer.seconds()>0.38){
                lime.updateBallColor(spindexerpos)
                afterIntakingTimer.reset()
                intaking = false
                when(spindexerpos){
                    Robotconstants.SpindexerPosition.TWO_INTAKE ->{
                        spindexerpos = Robotconstants.SpindexerPosition.THREE_INTAKE
                        intakeCounter += 1
                    }
                    Robotconstants.SpindexerPosition.THREE_INTAKE -> {
                        spindexerpos = Robotconstants.SpindexerPosition.ONE_INTAKE
                        intakeCounter +=1
                    }
                    else ->{
                        spindexerpos = Robotconstants.SpindexerPosition.TWO_INTAKE
                        intakeCounter +=1
                    }
                }
            }
            spindexer.position = spindexerpos.pos1
            spindexer.write()
            intake.write()
            return
    }
    var shootingState = 0
    val shootingTimer = ElapsedTime()
    var shootingOrder: BallOrder? = null

    fun shootInit(ball1: Ball, ball2: Ball, ball3: Ball){
        shootingOrder = lime.getOrderFromOrder(ball1, ball2, ball3)
        shootingState = 0
        shootingTimer.reset()
        follower.breakFollowing()
        if(blue){
            lime.limelight.pipelineSwitch(8)
        } else{
            lime.limelight.pipelineSwitch(9)
        }
    }

    fun getReadySpindexer(){
        spindexerpos = Robotconstants.SpindexerPosition.ONE_INTAKE
        spindexer.position = spindexerpos.pos1
        spindexer.write()
    }
    fun shootLoop(): Boolean {
        runHood()
        when(shootingState){
            0 -> {
                if (spindexerpos == Robotconstants.SpindexerPosition.ONE_INTAKE){
                    shootingState = 1
                } else{
                    launcher.effort = 0.0
                    spindexer.position = Robotconstants.SpindexerPosition.ONE_INTAKE.pos1
                    spindexer.write()
                    shootingTimer.reset()
                    shootingState = 1
                }

            }
            1 -> if(shootingTimer.seconds() > 0.5){
                runHood()
                launcher.write()
                hood.write()
                yeeter.effort = 1.0
                yeeter.write()
                shootingTimer.reset()
                shootingState = 2
            }

            2 -> if(shootingTimer.seconds() > 2.4){
                runHood()
                launcher.write()
                hood.write()
                spindexer.position = shootingOrder!!.position1.pos1
                spindexer.write()
                shootingTimer.reset()
                shootingState = 3
            }
            3 -> if(shootingTimer.seconds() > 1.55){
                runHood()
                launcher.write()
                hood.write()
                spindexer.position = shootingOrder!!.position2.pos1
                spindexer.write()

                shootingTimer.reset()
                shootingState = 4
            }

            4 -> if(shootingTimer.seconds() > 1.55){
                runHood()
                launcher.write()
                hood.write()
                spindexer.position = shootingOrder!!.position3.pos1
                spindexer.write()

                shootingTimer.reset()
                shootingState = 6
            }

            6 -> if(shootingTimer.seconds() > 0.62){

                launcher.effort = 0.0
                yeeter.effort = 0.0

                launcher.write()
                yeeter.write()

                shootingState = 8
            }
            8 -> return true
        }

        return false
    }

    fun shoot(ball1: Ball, ball2:Ball, ball3:Ball){

        val order = lime.getOrderFromOrder(ball1,ball2,ball3)
        val timer = ElapsedTime()
        spindexer.position = Robotconstants.SpindexerPosition.ONE_INTAKE.pos1
        spindexer.write()
        runHood()
        launcher.write()
        hood.write()
        timer.reset()
        while (opModeIsActive() && timer.seconds()<1.2){
            launcher.write()
        }
        yeeter.effort = 1.0
        timer.reset()
        while (opModeIsActive() && timer.seconds()<1.9){
            yeeter.write()
        }
        spindexer.position = order.position1.pos1
        timer.reset()
        while (opModeIsActive() && timer.seconds()<1.5){
            spindexer.write()
        }
        spindexer.position = order.position2.pos1
        timer.reset()
        while (opModeIsActive() && timer.seconds()<1.5){
            spindexer.write()
        }
        spindexer.position = order.position3.pos1
        timer.reset()
        while (opModeIsActive() && timer.seconds()<0.8){
            spindexer.write()
        }
        launcher.effort = 0.0
        yeeter.effort = 0.0
        launcher.write()
        yeeter.write()
    }


    fun runHood(){

        lime.update()
        val p = (lime.distance+3.0)/39.37 - 3.55
        val angleInRadians = 0.436 + .14*p +0.02*p.pow(2) + 0.024*p.pow(3 )+ 0.01.pow(4) + 0.001*p.pow(5)
        val differenceInServo = 0.36 * angleInRadians * (180/(25*PI))
        hood.position = 0.82 - differenceInServo
        launcher.effort = 0.95 * 12.8/hardwareMap.voltageSensor.iterator().next().voltage
    }



}