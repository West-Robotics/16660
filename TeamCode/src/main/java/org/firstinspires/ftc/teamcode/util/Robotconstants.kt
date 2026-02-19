package org.firstinspires.ftc.teamcode.util

class Robotconstants {
    public enum class SpindexerPosition(var pos1:Double){
        ONE_INTAKE(0.2),
        TWO_INTAKE(0.87),
        THREE_INTAKE(0.535),
        ONE_SHOOT(0.7),
        TWO_SHOOT(0.36),
        THREE_SHOOT(0.025),
    }
    public enum class OuttakeBallPower(var speedServo1:Double,var speedServo2:Double){
        ON(1.0,1.0),
        OFF(0.0,0.0)
    }

    enum class IntakeMotorPower(var power:Double){
        INTAKE(1.0),
        STOP(0.0),
        OUTTAKE(-0.5)
    }



}