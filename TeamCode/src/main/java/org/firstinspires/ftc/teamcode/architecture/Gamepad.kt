package org.firstinspires.ftc.teamcode.architecture
import android.graphics.HardwareRenderer
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcontroller.external.samples.RobotHardware
import org.firstinspires.ftc.teamcode.setup.button

class Gamepad(gamepad: Gamepad) {

    val a = Trigger{gamepad.a}
    val b = Trigger{gamepad.b}
    val x = Trigger{gamepad.x}
    val y = Trigger{gamepad.y}
    val dpad_down = Trigger{gamepad.dpad_down}
    val dpad_up = Trigger{gamepad.dpad_up}
    val dpad_left = Trigger{gamepad.dpad_left}
    val dpad_right = Trigger{gamepad.dpad_right}
    val right_bumper = Trigger{gamepad.right_bumper}
    val left_bumper = Trigger{gamepad.left_bumper}
    val left_stick_x :Double = gamepad.left_stick_x.toDouble()
    val left_stick_y :Double = gamepad.left_stick_y.toDouble()
    val right_stick_y :Double = gamepad.right_stick_y.toDouble()
    val right_stick_x :Double = gamepad.right_stick_x.toDouble()

    val right_trigger: Double = gamepad.right_trigger.toDouble()
    val left_trigger: Double = gamepad.left_trigger.toDouble()


}