package org.firstinspires.ftc.teamcode.FTC.mechanisms

import org.firstinspires.ftc.teamcode.util.Robotconstants

data class Ball(val position: Robotconstants.SpindexerPosition,var color: BallColor)

data class BallOrder(var position1: Robotconstants.SpindexerPosition, var position2: Robotconstants.SpindexerPosition, var position3: Robotconstants.SpindexerPosition)

enum class BallColor(){
    GREEN,
    PURPLE,
    EMPTY
}