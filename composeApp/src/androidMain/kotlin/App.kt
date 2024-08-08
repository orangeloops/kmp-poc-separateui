
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview

sealed class Screen(val route: String, val icon: ImageVector, val title: String) {
    object Home : Screen("home", Icons.Outlined.Home, "Home")
    object Time : Screen("time", Icons.Outlined.DateRange, "Time")
    object Reports : Screen("reports", Icons.Outlined.List, "Reports")
    object Account : Screen("account", Icons.Outlined.AccountCircle, "Account")
    object EntryForm : Screen("entry_form", Icons.Outlined.Add, "Entry Form")
    object TaskListView : Screen("task_list_view", Icons.Outlined.Add, "Tasks List View")
    object ProjectListView : Screen("project_list_view", Icons.Outlined.Add, "Project List View")
    object ClientListView : Screen("client_list_view", Icons.Outlined.Add, "Client List View")
    object DeleteForm : Screen("delete_form", Icons.Outlined.Add, "Delete Form")



}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(Screen.Home, Screen.Time, Screen.Reports, Screen.Account)
    BottomNavigation(
        backgroundColor = Color.White,
    ) {
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = false,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
@SuppressLint("RememberReturnType")
@Composable
@Preview
fun App(
    navController: NavHostController = rememberNavController()
) {
    MaterialTheme {

        Scaffold(
            bottomBar = {
                BottomNavigationBar(navController = navController)
            }
        ) { innerPadding ->
            NavHost(navController, startDestination = Screen.Time.route, Modifier.padding(innerPadding)) {
                composable(Screen.Home.route) { HomeScreen() }
                composable(Screen.Time.route) { TimeScreen(navController) }
                composable(Screen.Reports.route) { ReportsScreen() }
                composable(Screen.Account.route) { AccountScreen() }
                composable(Screen.EntryForm.route) { EntryForm(navController) }
                composable(Screen.TaskListView.route) { TaskListView(navController) }
                composable(Screen.ProjectListView.route) { ProjectListView(navController) }
                composable(Screen.ClientListView.route) { ClientListView(navController) }
                composable("delete_form/{taskId}") { backStackEntry ->
                    val taskId = backStackEntry.arguments?.getString("taskId")
                    DeleteFormScreen(navController, taskId)
                }
            }
        }
    }
}