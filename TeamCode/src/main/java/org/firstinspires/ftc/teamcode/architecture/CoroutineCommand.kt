package org.firstinspires.ftc.teamcode.architecture

import kotlinx.coroutines.*

abstract class CoroutineCommand(
    val func: suspend CoroutineScope.() -> Unit
) : Command(){
    private var job: Job? = null
    override fun initialize() {
        job = CoroutineScope(Dispatchers.Main).launch {
            func()
        }
    }

    override fun isFinished(): Boolean {
        return job?.isCompleted?:true
    }

    override fun end(interrupted: Boolean){
        job?.cancel()
    }

}