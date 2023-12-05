package com.mobdeve.s15.gironasayasranario.cinereview.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory

class FeatureFactory: FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (loadFragmentClass(classLoader, className)) {
            MovieDetailFragment::class.java -> MovieDetailFragment()
            SchedulingFragment::class.java -> SchedulingFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}