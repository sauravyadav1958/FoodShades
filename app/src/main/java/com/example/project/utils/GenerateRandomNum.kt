package com.example.project.utils
//import kotlin.math.floor

//import java.lang.Math.floor
import java.util.Random
import kotlin.math.floor

class GenerateRandomNum {

    companion object {
        fun generateRandNum() : String{
            return (floor(Math.random() * 9000000000000L) + 1000000000000L).toString()
        }
    }

}