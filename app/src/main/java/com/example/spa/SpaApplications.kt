package com.example.spa

import android.app.Application
import com.example.spa.dependenciesinjection.JenisTrapiContainer
import com.example.spa.dependenciesinjection.PasienContainer
import com.example.spa.dependenciesinjection.SesiContainer
import com.example.spa.dependenciesinjection.TerapisContainer

class SpaApplications :Application(){
    lateinit var PasienContainer: PasienContainer
    lateinit var TerapisContainer: TerapisContainer
    lateinit var JenisTrapiContainer: JenisTrapiContainer
    lateinit var SesiContainer: SesiContainer

    override fun onCreate(){
        super.onCreate()
        PasienContainer = PasienContainer()
        TerapisContainer = TerapisContainer()
        JenisTrapiContainer = JenisTrapiContainer()
        SesiContainer = SesiContainer()
    }
}