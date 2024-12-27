package com.example.stickynotes

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.stickynotes.ui.theme.StickynotesTheme

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainViewModel
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel  = ViewModelProvider(this)[MainViewModel::class]
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            StickynotesTheme {
               window.statusBarColor = (resources.getColor(R.color.body))
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                Scaffold(modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        if(navController.currentBackStackEntryAsState().value ?.destination ?.route != "add") {
                            FloatingActionButton(
                                content = {
                                    Icon(Icons.Filled.Add, contentDescription = null)
                                },
                                onClick = {
                                    navController.navigate("add/false/${""}/${""}/${0}")
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    NavHost(navController = navController, startDestination = "itemDetails", modifier = Modifier.padding(innerPadding)) {

                        composable("itemDetails") {
                            ItemDetailsPage(viewModel, navController)
                        }
                        composable("add/{isEdit}/{title}/{message}/{id}"){
                            val isEdit = it.arguments?.getString("isEdit").toBoolean() ?:false
                            val title = it.arguments?.getString("title")
                            val message = it.arguments?.getString("message")
                            val id = it.arguments?.getString("id")?.toInt() ?:0
                            AddorEdit(viewModel, navController, isEdit,title,message,id)
                        }

                    }
                }
            }
        }
    }
}


