package com.arbonik.answerka.entity

import java.io.Externalizable
import java.io.ObjectInput
import java.io.ObjectOutput

class AKeySerializable() : AKey, Externalizable {

    override var workBefore: Long = 0
    override var order: List<String> = emptyList()

    constructor(
        wBefore: Long = 0,
        orde: List<String> = emptyList()
    ) : this() {
        workBefore = wBefore
        order = orde
    }

    override fun writeExternal(p0: ObjectOutput) {
        p0.writeLong(workBefore)
        p0.writeObject(order.joinToString { "," })
    }

    override fun readExternal(p0: ObjectInput) {
        workBefore = p0.readLong()
        order = (p0.readObject() as String).split(",")
    }
}