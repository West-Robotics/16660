package org.firstinspires.ftc.teamcode.FTC.mechanisms.lime

import android.graphics.Color
import com.qualcomm.hardware.limelightvision.*
import com.qualcomm.robotcore.hardware.ColorSensor
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.robot.Robot
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.util.Robotconstants
import org.firstinspires.ftc.teamcode.FTC.mechanisms.BallColor
import org.firstinspires.ftc.teamcode.FTC.mechanisms.Ball
import org.firstinspires.ftc.teamcode.FTC.mechanisms.BallOrder
import kotlin.math.PI
import kotlin.math.tan
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.abs
class Limelight(val hardwareMap: HardwareMap, val telemetry: Telemetry) {

    val limelight: Limelight3A = hardwareMap.get("Limelight") as Limelight3A
    val colorSensor = hardwareMap.get("color") as ColorSensor
    lateinit var LLResult: LLResult
    private var initialized: Boolean = false

    fun init(pipeline:Int){
        limelight.pipelineSwitch(pipeline) //8 is blue // 9 is red // 6 is ordering
        limelight.start()
        initialized = true
    }

    val tx:Double
        get() = LLResult.tx
    val ty:Double
        get() = -LLResult.ty



    private val groundToLimelightDistance = 5.092  // in Inches
    private val limeLightToLauncherDistance = 5.629 // in Inches
    private val limeLightAngle = 15.0

    val distance: Double
        get() = (29.5 - groundToLimelightDistance)/tan(Math.toRadians(limeLightAngle + ty))

    val error: Double
        get() = if(tx>0.0){
                Math.toRadians(90.0)-asin(  (distance * sin(Math.toRadians(tx+90))  )
                /sqrt(distance.pow(2) + limeLightToLauncherDistance.pow(2) -2*distance*limeLightToLauncherDistance*cos(Math.toRadians(LLResult.tx+90)))   )
        } else{
            -Math.toRadians(90.0) + asin(  (distance * sin(Math.toRadians(tx+90))  )
                    /sqrt(distance.pow(2) + limeLightToLauncherDistance.pow(2) -2*distance*limeLightToLauncherDistance*cos(Math.toRadians(LLResult.tx+90)))   )
        }



    fun update(){
        LLResult = limelight.latestResult
        if (LLResult.isValid){
            telemetry.addLine("WORKED")
        } else{
            telemetry.addLine("NOT SEEING")
        }
    }

    fun addTelemetry(){
        telemetry.addData("TX",tx)
        telemetry.addData("TY",ty)
        telemetry.addData("TA",LLResult.ta)
    }

    private val kP = 0.023
    private val kI = 0.0
    private val kD = 0.0
    private var lasterror = 0.0
    private var lasttime = 0.0
    private var integral = 0.0
    private val timer = ElapsedTime()
    private var P = 0.0
    private var I = 0.0
    private var D = 0.0

    fun resetAlignment(){
        timer.reset()
        timer.startTime()
        lasterror = 0.0
        integral = 0.0
        lasttime = 0.0
    }

    fun updateAlignment(){
        if (LLResult.isValid){
            val error = tx + 5.4
            val time = timer.seconds()
            val dt = time - lasttime
            integral += error * dt
            P = kP * error
            I = kI * integral
            D = kD * (error - lasterror)/dt

            lasterror = error
            lasttime = time
        } else{
            telemetry.addLine("Not in limelight")
        }
    }

    fun updateAlignmentWithError(){
        if (LLResult.isValid){
            var erroring = 0.0
            if (LLResult.tx>0.0){
                erroring = error *(180/ PI)
            }else{
                erroring = error *(180/PI)
            }
            val time = timer.seconds()
            val dt = time - lasttime
            integral += erroring * dt
            P = kP * erroring
            I = kI * integral
            D = kD * (erroring - lasterror)/dt
            telemetry.addData("error",erroring)
            lasterror = erroring
            lasttime = time
        } else{
            telemetry.addLine("Not in limelight")
        }
    }

    val AlignmentPower:Double
        get() = P+I+D






    var intakePosOne = Ball(Robotconstants.SpindexerPosition.ONE_SHOOT, BallColor.EMPTY)
    var intakePosTwo = Ball(Robotconstants.SpindexerPosition.TWO_SHOOT, BallColor.EMPTY)
    var intakePosThree = Ball(Robotconstants.SpindexerPosition.THREE_SHOOT, BallColor.EMPTY)

    fun resetBalls(){
        intakePosOne.color = BallColor.EMPTY
        intakePosTwo.color = BallColor.EMPTY
        intakePosThree.color = BallColor.EMPTY

    }

    //Intake pos1 should equal Shoot pos1 and so on, if not configure it
    fun shootingOrder():List<Robotconstants.SpindexerPosition>{
        if (!hasReadObelisk){
            return listOf(Robotconstants.SpindexerPosition.ONE_SHOOT, Robotconstants.SpindexerPosition.TWO_SHOOT,
                Robotconstants.SpindexerPosition.THREE_SHOOT)
        } else{
            val returningBallOrder = BallOrder(Robotconstants.SpindexerPosition.ONE_SHOOT,
                Robotconstants.SpindexerPosition.TWO_SHOOT,
                Robotconstants.SpindexerPosition.THREE_SHOOT)
            val currentBallOrder = mutableListOf<Ball>(intakePosOne,intakePosTwo,intakePosThree)
            var indexer = 0
            when(order){
                ShootOrder.GPP ->{
                    for (i in currentBallOrder){
                        if (i.color == BallColor.GREEN){
                            returningBallOrder.position1 = i.position
                        } else{
                            if (indexer ==0){
                                returningBallOrder.position2 = i.position
                                indexer +=1
                            } else if (indexer ==1){
                                returningBallOrder.position3 = i.position
                            } else{
                                returningBallOrder.position1 = i.position
                            }
                        }
                    }
                }
                ShootOrder.PGP -> {
                    for (i in currentBallOrder){
                        if (i.color == BallColor.GREEN){
                            returningBallOrder.position2 = i.position
                        } else{
                            if (indexer ==0){
                                returningBallOrder.position1 = i.position
                                indexer +=1
                            } else if (indexer ==1){
                                returningBallOrder.position3 = i.position
                            } else{
                                returningBallOrder.position2 = i.position
                            }
                        }
                    }
                }
                ShootOrder.PPG -> {
                    for (i in currentBallOrder){
                        if (i.color == BallColor.GREEN){
                            returningBallOrder.position3 = i.position
                        } else{
                            if (indexer ==0){
                                returningBallOrder.position1 = i.position
                                indexer +=1
                            } else if (indexer ==1){
                                returningBallOrder.position2 = i.position
                            } else{
                                returningBallOrder.position3 = i.position
                            }
                        }
                    }
                }
            }
            return listOf(returningBallOrder.position1,returningBallOrder.position2,returningBallOrder.position3)
        }

    }

    fun ifSpindexerFull():Boolean{
        val listofBalls = listOf<Ball>(intakePosOne,intakePosTwo,intakePosThree)
        for (i in listofBalls){
            if(i.color == BallColor.EMPTY){
                return false
            }
        }
        return true
    }

    fun addColorTelemetry(){
        telemetry.addData("Ball1",intakePosOne.color)
        telemetry.addData("Ball2",intakePosTwo.color)
        telemetry.addData("Ball3",intakePosThree.color)
    }

    fun isPurple():Boolean{return isPurpleBall}
    fun isGreen():Boolean{return isGreenBall}

    fun resetColorTracking(){
        intakePosOne.color = BallColor.EMPTY
        intakePosTwo.color = BallColor.EMPTY
        intakePosThree.color = BallColor.EMPTY
    }

    var isGreenBall = false
    var isPurpleBall = false
    private val hsv = FloatArray(3)


    fun updateColorSensor(){
        val r = colorSensor.red()
        val g = colorSensor.green()
        val b = colorSensor.blue()
        Color.RGBToHSV(r * 255 / 800, g * 255 / 800, b * 255 / 800, hsv)
        val hue = hsv[0]
        val sat = hsv[1]
        val valV = hsv[2]

        /*
        HUE ranges
        NOTHING = ~129 hue,   ~0.3 Sat,   ~0.29valV
        PURPLE = ~229 hue,    ~0.58sat,    ~3.5 valV    ||    ~153-159hue,  0.375-0.428 sat, 0.14-0.16 valV
        GREEN = ~165hue,     ~ 0.74sat,  3.25 valV     ||     ~148-159hue,  0.46-0.55 sat, 0.14-0.16 valV
         */
        isGreenBall = ((hue in 161f..169f) && (valV in 2.0..5.7)) || ((hue in 148f..159f) && (valV in 0.0..0.17) &&(sat in 0.458..0.56))
        isPurpleBall = ((hue in 207f..229f) && (valV in 2.0..5.9)) || ((hue in 153f..159f) && (valV in 0.0..0.17) && (sat in 0.37..0.435))
        telemetry.addData("hue",hue)
        telemetry.addData("sat",sat)
        telemetry.addData("valV",valV)
    }

    fun hasBall():Boolean{
        var isBall = false
        if (isGreen()){
            isBall = true
        } else if (isPurple()){
            isBall = true
        }
        return isBall
    }

    fun updateBallColor(spindexerpos: Robotconstants.SpindexerPosition){
        var color = BallColor.EMPTY
        if (isPurple()){
            color = BallColor.PURPLE
        } else if (isGreen()){
            color = BallColor.GREEN
        }
        when(spindexerpos){
            Robotconstants.SpindexerPosition.ONE_INTAKE ->{
                intakePosOne.color = color
            }
            Robotconstants.SpindexerPosition.TWO_INTAKE -> {
                intakePosTwo.color = color
            }
            Robotconstants.SpindexerPosition.THREE_INTAKE -> {
                intakePosThree.color = color
            } else -> {
        }
        }

    }


    enum class ShootOrder(){
        GPP,
        PGP,
        PPG
    }

    var order = ShootOrder.GPP

    var hasReadObelisk = false

    fun readObelisk(){
        if (LLResult.isValid){
            val tags = LLResult.fiducialResults
            if(tags.isNotEmpty()){
                val tag = tags[0]
                if (tag.fiducialId == 21) {
                    order = ShootOrder.GPP
                } else if (tag.fiducialId==22) {
                    order = ShootOrder.PGP
                } else {
                    order = ShootOrder.PPG
                }
                hasReadObelisk = true
            }
        } else {
            telemetry.addLine("Couldn't read obelisk")
            hasReadObelisk = false
        }
    }

    fun getOrderFromOrder(ball1:Ball,ball2:Ball,ball3:Ball): BallOrder{
        val listOfBalls = listOf<Ball>(ball1,ball2,ball3)
        val ballOrder = BallOrder(Robotconstants.SpindexerPosition.ONE_SHOOT, Robotconstants.SpindexerPosition.TWO_SHOOT,
            Robotconstants.SpindexerPosition.THREE_SHOOT)
        var indexer = 0
        when (order){
            ShootOrder.GPP ->{
                for (i in listOfBalls) {
                    if(i.color == BallColor.GREEN){
                        ballOrder.position1 = i.position
                    } else if (i.color == BallColor.PURPLE && indexer ==0){
                        ballOrder.position2 = i.position
                        indexer+=1
                    } else{
                        ballOrder.position3 = i.position
                    }
                }
            }
            ShootOrder.PGP -> {
                for (i in listOfBalls) {
                    if(i.color == BallColor.GREEN){
                        ballOrder.position2 = i.position
                    } else if (i.color == BallColor.PURPLE && indexer ==0){
                        ballOrder.position1 = i.position
                        indexer+=1
                    } else{
                        ballOrder.position3 = i.position
                    }
                }
            }
            ShootOrder.PPG -> {
                for (i in listOfBalls) {
                    if(i.color == BallColor.GREEN){
                        ballOrder.position3 = i.position
                    } else if (i.color == BallColor.PURPLE && indexer ==0){
                        ballOrder.position1 = i.position
                        indexer+=1
                    } else{
                        ballOrder.position2 = i.position
                    }
                }
            }
        }
        return ballOrder
    }


}