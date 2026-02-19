package org.firstinspires.ftc.teamcode.FTC.autonomous

import com.pedropathing.geometry.Pose
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.FTC.mechanisms.Ball
import org.firstinspires.ftc.teamcode.FTC.mechanisms.BallColor
import org.firstinspires.ftc.teamcode.util.Robotconstants

@Autonomous(name = "RedFar3")
class RedFar3: PedroOpMode() {
    override var blue = false

    var startPose = Pose(87.653,8.0,Math.toRadians(270.0))
    var shootPose: Pose = Pose(87.870,17.944,Math.toRadians(252.0))
    var parkPose: Pose = Pose(85.424,46.341,Math.toRadians(0.0))

    enum class PathState(){
        START_SHOOT,
        SHOOTING,
        SHOOT_PARK
    }

    override fun looping() {
        val startToShoot = buildPath(startPose,shootPose)
        val shootToPark = buildPath(shootPose,parkPose)
        var pathState = PathState.START_SHOOT
        follower.pose = startPose
        waitForStart()
        while (opModeIsActive()){
            follower.update()
            when(pathState){
                PathState.START_SHOOT ->{
                    follower.followPath(startToShoot)
                    pathState = PathState.SHOOTING
                }
                PathState.SHOOTING -> {
                    if (!follower.isBusy){
                        shoot(Ball(Robotconstants.SpindexerPosition.ONE_SHOOT, BallColor.GREEN),
                            Ball(Robotconstants.SpindexerPosition.TWO_SHOOT, BallColor.PURPLE),
                            Ball(Robotconstants.SpindexerPosition.THREE_SHOOT, BallColor.PURPLE))

                        pathState = PathState.SHOOT_PARK
                    }
                }
                PathState.SHOOT_PARK -> {
                    follower.followPath(shootToPark)
                }
            }
        }
    }

}
