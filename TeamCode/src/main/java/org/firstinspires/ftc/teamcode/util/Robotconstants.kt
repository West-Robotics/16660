package org.firstinspires.ftc.teamcode.util

class Robotconstants {
    public enum class SpindexerPosition(var pos1:Double){
        ONE_INTAKE(0.98),
        TWO_INTAKE(0.64),
        THREE_INTAKE(0.3),
        ONE_SHOOT(0.8),
        TWO_SHOOT(0.47),
        THREE_SHOOT(0.125),
        ONE_COLOR(0.9),
        TWO_COLOR(0.55),
        THREE_COLOR(0.21)
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