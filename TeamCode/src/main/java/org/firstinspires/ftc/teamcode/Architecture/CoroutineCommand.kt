package org.firstinspires.ftc.teamcode.Architecture

import kotlinx.coroutines.*

class CoroutineCommand(
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