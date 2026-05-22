package com.example.ghibli

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.ghibli.ui.navigation.AppNavGraph
import com.example.ghibli.ui.navigation.NavigationAction
import com.example.ghibli.ui.navigation.Navigator
import com.example.ghibli.ui.navigation.ObserveAsEvents
import com.example.ghibli.ui.theme.GhibliTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            GhibliTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->

                    ObserveAsEvents(flow = navigator.navigationActions) { action ->
                        when (action) {
                            is NavigationAction.Navigate -> navController.navigate(
                                route = action.destination,
                            ) {
                                action.navOptions(this)
                            }

                            NavigationAction.NavigateUp -> navController.navigateUp()
                        }
                    }
                    AppNavGraph(
                        navController = navController,
                        startDestination = navigator.startDestination,
                    )
                }
            }
        }
    }
}
