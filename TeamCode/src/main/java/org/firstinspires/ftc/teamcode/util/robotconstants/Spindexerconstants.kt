package org.firstinspires.ftc.teamcode.util.robotconstants

class Spindexerconstants {
    public enum class SpindexerPosition(pos1:Double){
        ONE_INTAKE(0.0),
        TWO_INTAKE(0.0),
        THREE_INTAKE(0.0),
        ONE_SHOOT(0.0),
        TWO_SHOOT(0.0),
        THREE_SHOOT(0.0)
    }
    public enum class OuttakeBallPower(speedServo1:Double,speedServo2:Double){
        ON(0.0,0.0),
        OFF(0.0,0.0)
    }

    public enum class intakeMotorPower(power:Double){
        INTAKE(1.0),
        STOP(0.0),
        OUTTAKE(-0.5)
    }



}