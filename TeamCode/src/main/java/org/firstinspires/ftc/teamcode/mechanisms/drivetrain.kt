package org.firstinspires.ftc.teamcode.mechanisms

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.setup.motorSetup
import com.qualcomm.robotcore.hardware.DcMotorSimple
import kotlin.math.max
import kotlin.math.abs

class drivetrain (hardwareMap: HardwareMap, setupMecanum: Boolean){
    lateinit var frontLeft: motorSetup
    lateinit var frontRight: motorSetup
    lateinit var backLeft: motorSetup
    lateinit var backRight:motorSetup

    init{
        frontLeft = motorSetup(hardwareMap, "frontLeft",DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.FLOAT )
        frontRight = motorSetup(hardwareMap, "frontRight",DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.FLOAT )

        if (setupMecanum) {

            backLeft = motorSetup(
                hardwareMap,
                "backLeft",
                DcMotorSimple.Direction.REVERSE,
                DcMotor.ZeroPowerBehavior.FLOAT
            )
            backRight = motorSetup(
                hardwareMap,
                "backRight",
                DcMotorSimple.Direction.FORWARD,
                DcMotor.ZeroPowerBehavior.FLOAT
            )
        }
    }


    fun mecanumEffort(y:Double, x:Double, rx:Double){
        val denominator = max(abs(x)+abs(y)+abs(rx),1.0)
        frontLeft.effort = (y+x+rx)/denominator
        backLeft.effort = (y-x+rx)/denominator
        frontRight.effort = (y-x-rx)/denominator
        backRight.effort = (y+x-rx)/denominator
    }

    fun tankEffort(leftInput: Double, rightInput: Double){
        frontRight.effort = rightInput
        frontLeft.effort = leftInput
    }



    fun runMotors(){
        frontLeft.toPower()
        frontRight.toPower()
        backLeft.toPower()
        backRight.toPower()
    }

}