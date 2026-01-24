package org.firstinspires.ftc.teamcode.autonomous


import com.pedropathing.follower.Follower
import com.pedropathing.geometry.BezierLine
import com.pedropathing.geometry.Pose
import com.pedropathing.paths.PathChain
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.pedropathing.util.Timer
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.architecture.subsystems.SpindexerSubsytem
import org.firstinspires.ftc.teamcode.architecture.subsystems.YeeterSubsystem
import org.firstinspires.ftc.teamcode.autonomous.pedrotuning.Constants
import org.firstinspires.ftc.teamcode.autonomous.lime.OrderDetection

abstract class PedroOpMode() : LinearOpMode(){

    protected abstract var startPose:Pose
    protected abstract var shootPose: Pose
    protected abstract var parkPose:Pose

    enum class PathState(){
        START_SHOOT,
        SHOOTING,
        SHOOT_PARK
    }



    override fun runOpMode() {
        val spindexer = SpindexerSubsytem(hardwareMap)
        val yeeter = YeeterSubsystem(hardwareMap)
        var ordering = OrderDetection(hardwareMap,spindexer,telemetry,this,yeeter)
        ordering.init()
        var pathState = PathState.START_SHOOT
        var follower:Follower = Constants.createFollower(hardwareMap)
        follower.pose = startPose
        var startToShoot = follower.pathBuilder()
            .addPath(BezierLine(startPose, shootPose))
            .setLinearHeadingInterpolation(startPose.heading, shootPose.heading)
            .build()
        var shootToPark = follower.pathBuilder()
            .addPath(BezierLine(shootPose, parkPose))
            .setLinearHeadingInterpolation(shootPose.heading, parkPose.heading)
            .build()
        waitForStart()
        ordering.fireOrder()
        while (opModeIsActive()){
            follower.update()
            when(pathState){
                PathState.START_SHOOT ->{
                    follower.followPath(startToShoot,true)
                    pathState = PathState.SHOOTING
                }
                PathState.SHOOTING -> {
                    if (!follower.isBusy){
                        ordering.shootOrder()
                        pathState = PathState.SHOOT_PARK
                    }
                }
                PathState.SHOOT_PARK -> {
                    follower.followPath(shootToPark,true)
                }
            }


        }

    }
}