package org.firstinspires.ftc.teamcode.FTC.autonomous

import com.pedropathing.geometry.Pose
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.FTC.mechanisms.Ball
import org.firstinspires.ftc.teamcode.FTC.mechanisms.BallColor
import org.firstinspires.ftc.teamcode.util.Robotconstants

@TeleOp(name = "BlueFar3Ball")
class BlueFar3Ball: PedroOpMode() {
    override var blue: Boolean = true


    var startPose = Pose()
    var shootPose: Pose = Pose(60.01976935749587,15.894563426688618,Math.toRadians(-66.0))
    var parkPose: Pose = Pose(59.308072487644125,37.2454695222405,Math.toRadians(180.0))

    enum class PathState(){
        START_SHOOT,
        SHOOTING,
        SHOOT_PARK
    }

    override fun runOpMode() {
        val startToShoot = buildPath(startPose,shootPose)
        val shootToPark = buildPath(shootPose,parkPose)
        var pathState = PathState.START_SHOOT
        waitForStart()
        initialize()
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
                            Ball(Robotconstants.SpindexerPosition.TWO_SHOOT, BallColor.PURPLE))

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
