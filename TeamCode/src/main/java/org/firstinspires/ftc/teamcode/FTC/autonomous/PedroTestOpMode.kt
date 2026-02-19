package org.firstinspires.ftc.teamcode.FTC.autonomous


import com.pedropathing.follower.Follower
import com.pedropathing.geometry.BezierLine
import com.pedropathing.geometry.Pose
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.architecture.subsystems.SpindexerSubsytem
import org.firstinspires.ftc.teamcode.FTC.autonomous.pedrotuning.Constants
import org.firstinspires.ftc.teamcode.setup.motorSetup
import org.firstinspires.ftc.teamcode.setup.servoSetup
import org.firstinspires.ftc.teamcode.util.ServoConstants

abstract class PedroTestOpMode() : LinearOpMode(){

    protected abstract var startPose:Pose
    protected abstract var shootPose: Pose
    protected abstract var parkPose:Pose

    enum class PathState(){
        START_SHOOT,
        SHOOTING,
        SHOOT_PARK
    }



    override fun runOpMode() {
        val hood = servoSetup(hardwareMap,"angle", ServoConstants.AXON)
        val spindexer = SpindexerSubsytem(hardwareMap)
        val yeeter = motorSetup(hardwareMap, "yeeter", DcMotorSimple.Direction.REVERSE, DcMotor.ZeroPowerBehavior.BRAKE)
        var pathState = PathState.START_SHOOT
        var follower : Follower = Constants.createFollower(hardwareMap)
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

        while (opModeIsActive()){
            follower.update()
            when(pathState){
                PathState.START_SHOOT ->{
                    follower.followPath(startToShoot,true)
                    pathState = PathState.SHOOTING
                }
                PathState.SHOOTING -> {
                    if (!follower.isBusy){
                        hood.position = 0.08
                        pathState = PathState.SHOOT_PARK
                    }
                }
                PathState.SHOOT_PARK -> {
                    follower.followPath(shootToPark,true)
                }
            }
            yeeter.write()

        }

    }
}