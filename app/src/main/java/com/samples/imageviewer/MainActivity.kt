package com.samples.imageviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.samples.imageviewer.ui.detail.DetailScreen
import com.samples.imageviewer.ui.favourite.FavoriteScreen
import com.samples.imageviewer.ui.favourite.FavoriteViewModel
import com.samples.imageviewer.ui.home.HomeScreen
import com.samples.imageviewer.ui.home.HomeViewModel
import com.samples.imageviewer.ui.theme.ImageViewerTheme
import dagger.hilt.android.AndroidEntryPoint

const val home = "home"
const val fav = "favorites"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImageViewerTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainScaffold()
                }
            }
        }
    }

    @Composable
    fun MainScaffold() {
        val navController = rememberNavController()
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        Scaffold(
            bottomBar = {
                if (currentRoute == home || currentRoute == fav) {
                    NavigationBarBottom(navController)
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                ImageViewerNavHost(navController)
            }
        }
    }

    @Composable
    fun NavigationBarBottom(navController: NavController) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        NavigationBar {
            NavigationBarItem(
                icon = { Icon(Icons.Default.Home, contentDescription = home) },
                label = { Text(home) },
                selected = currentRoute == "home",
                onClick = {
                    navController.navigate("home") {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Default.Favorite, contentDescription = fav) },
                label = { Text("Favorites") },
                selected = currentRoute == "favorites",
                onClick = {
                    navController.navigate("favorites") {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }


    @Composable
    fun ImageViewerNavHost(navController: NavHostController) {
        NavHost(navController, startDestination = "home") {
            composable(home) {
                val homeViewModel: HomeViewModel = hiltViewModel()
                HomeScreen(homeViewModel, onImageClick = { image ->
                    navController.navigate("detail/${image.id}")
                })
            }
            composable("detail/{imageId}") { backStackEntry ->
                val imageId = backStackEntry.arguments?.getString("imageId") ?: ""
                DetailScreen(imageId = imageId)
            }
            composable(fav) {
                val favoriteViewModel: FavoriteViewModel = hiltViewModel()
                FavoriteScreen(favoriteViewModel, onImageClick = { image ->
                    navController.navigate("detail/${image.id}")
                })
            }
        }
    }
}

