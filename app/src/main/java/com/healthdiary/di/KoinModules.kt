package com.healthdiary.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.healthdiary.model.data.localstorage.LocalDataSource
import com.healthdiary.model.data.repositories.Repository
import com.healthdiary.ui.home.HomeFragment
import com.healthdiary.ui.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module{
    single<Repository> { LocalDataSource }
    factory { HomeFragment() }
}

@RequiresApi(Build.VERSION_CODES.O)
val homeModule = module {
    viewModel { HomeViewModel(get()) }
}