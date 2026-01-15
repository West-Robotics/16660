package org.firstinspires.ftc.teamcode.architecture
import com.qualcomm.robotcore.hardware.Gamepad

class Gamepader(gamepad: Gamepad) {
    var gamepad = gamepad
    val a
        get() = Trigger{gamepad.a}
    val b
        get() = Trigger{gamepad.b}
    val x
        get() = Trigger{gamepad.x}
    val y
        get() = Trigger{gamepad.y}
    val dpad_down
        get() = Trigger{gamepad.dpad_down}
    val dpad_up
        get() = Trigger{gamepad.dpad_up}
    val dpad_left
        get() = Trigger{gamepad.dpad_left}
    val dpad_right
        get() = Trigger{gamepad.dpad_right}
    val right_bumper
        get() = Trigger{gamepad.right_bumper}
    val left_bumper
        get() = Trigger{gamepad.left_bumper}
    val left_stick_x :Double
        get() = gamepad.left_stick_x.toDouble()
    val left_stick_y :Double
        get() = gamepad.left_stick_y.toDouble()
    val right_stick_y :Double
        get() = gamepad.right_stick_y.toDouble()
    val right_stick_x :Double
        get() = gamepad.right_stick_x.toDouble()

    val right_trigger: Double
        get() = gamepad.right_trigger.toDouble()
    val left_trigger: Double
        get() = gamepad.left_trigger.toDouble()


}