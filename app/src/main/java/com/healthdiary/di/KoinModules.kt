package com.healthdiary.di

import com.healthdiary.model.data.localstorage.LocalDataSource
import com.healthdiary.model.data.repositories.Repository
import com.healthdiary.ui.calendar.CalendarFragment
import com.healthdiary.ui.home.HomeFragment
import com.healthdiary.ui.profile.ProfileFragment
import com.healthdiary.ui.viewmodel.CalendarViewModel
import com.healthdiary.ui.viewmodel.HomeViewModel
import com.healthdiary.ui.viewmodel.ProfileViewModel
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module{
    single<Repository> { LocalDataSource }
}

val homeModule = module {
    viewModel { HomeViewModel(get()) }
    fragment { HomeFragment() }
}
val calendarModule = module {
    viewModel { CalendarViewModel() }
    fragment { CalendarFragment() }
}
val profileModule = module {
    viewModel { ProfileViewModel() }
    fragment { ProfileFragment() }
}