package com.healthdiary.di

import com.healthdiary.model.data.localstorage.DataBase
import com.healthdiary.model.data.localstorage.LocalDataSource
import com.healthdiary.model.data.repositories.Repository
import com.healthdiary.ui.viewmodel.CalendarViewModel
import com.healthdiary.ui.viewmodel.HomeViewModel
import com.healthdiary.ui.viewmodel.IndicatorViewModel
import com.healthdiary.ui.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module{

    single<Repository> { LocalDataSource }
    single<DataBase?> { DataBase.INSTANCE }
}

val homeModule = module {
    viewModel { HomeViewModel(get()) }
}

val calendarModule = module {
    viewModel { CalendarViewModel() }
}

val profileModule = module {
    viewModel { ProfileViewModel() }
}

val indicatorModule = module {
    viewModel { IndicatorViewModel(get()) }
}