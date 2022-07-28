package com.mfahimi.demo

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.*
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.mfahimi.demo.navigation.SearchParameters
import com.mfahimi.demo.navigation.SearchParametersType
import com.mfahimi.demo.navigation.NavArguments
import com.mfahimi.demo.navigation.NavRoutes
import com.mfahimi.demo.ui.main.navigation.DetailFragment
import com.mfahimi.demo.ui.main.MainFragment
import com.mfahimi.demo.ui.main.navigation.DeepLinkFragment
import com.mfahimi.demo.ui.main.navigation.DestinationActivity
import com.mfahimi.demo.ui.main.navigation.NestedDestinationFragment
import com.mfahimi.demo.ui.main.navigation.CustomArgumentFragment
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

class MainActivity : AppCompatActivity() {

    val baseUri = "http://www.example.com/plants"
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController

        setupNavFrag()

        findViewById<View>(R.id.btn).setOnClickListener {
//            navigateToFragmentWithPrimitiveArgument("first arg", "second arg")
//            navigateToFragmentWithCustomParameter()
//            navigateToActivityDestination()
//            navigateToNestedGraph("yyy", "nestedgraph")
            navigateViaDeeplink()
        }

    }

    private fun setupNavFrag() {
        /*==============================================================================================================================*/
        navController.graph = navController.createGraph(startDestination = NavRoutes.home) {
            //start destination
            fragment<MainFragment>(NavRoutes.home) {
                label = "HomeFragment"
            }

            /*=====================================  fragment destination with primitive arguments  =====================================*/

            fragment<DetailFragment>("${NavRoutes.plant_detail}/{${NavArguments.plant_id}}/{${NavArguments.plant_name}}") {
                label = "DetailFragment"
                argument(NavArguments.plant_id) {
                    type = NavType.StringType
                }
                argument(NavArguments.plant_name) {
                    type = NavType.StringType
                    nullable = true
                }
            }

            /*======================================== fragment destination with custom argument  =======================================*/

            fragment<CustomArgumentFragment>("${NavRoutes.plant_search}/{${NavArguments.search_parameters}}") {
                label = "ThirdFragment"
                argument(NavArguments.search_parameters) {
                    type = SearchParametersType()
                    defaultValue = SearchParameters("cactus", emptyList())
                }
            }

            /*========================================= activity destination  ============================================================*/

            activity("${NavRoutes.detail_activity}/{${NavArguments.activity_parameters}}") {
                label = "DetailActivityTitle"
                // arguments, deepLinks...
                activityClass = DestinationActivity::class
                argument(NavArguments.activity_parameters) {
                    type = NavType.StringType
                }
            }

            /*====================================== fragment destination with deeplink  =================================================*/

            /** how to support deep link with argument ?? */
//            fragment<DeepLinkFragment>("${NavRoutes.deep_link}/{${NavArguments.plant_id}}/{${NavArguments.plant_name}}") {
            fragment<DeepLinkFragment>("${NavRoutes.deep_link}") {
                label = "DeepLink Fragment"
                deepLink(//navDeepLink {
                    uriPattern = "${baseUri}/{${NavArguments.plant_id}}"
                )
                deepLink(//navDeepLink {
                    uriPattern = "${baseUri}/{${NavArguments.plant_id}}?name={${NavArguments.plant_name}}"
                )
            }

            /*========================================== Add nested graph ================================================================*/

            addDestination(createNewGraph())

            /*========================================== On progress  ====================================================================*/


//            deepLink {
//                uriPattern = "http://www.example.com/plants/"
//                action = "android.intent.action.MY_ACTION"
//                mimeType = "image/*"
//            }

//            navigation(createNewGraph(),  NavRoutes.route001) {
//            }


//            val customDestination = provider[CustomNavigator::class].createDestination().apply {
//                route = Graph.CustomDestination.route
//            }
//            addDestination(customDestination)

        }


    }

    private fun createNewGraph(): NavGraph {
        val graph = navController.createGraph(
            route = NavRoutes.route001,
            startDestination = NavRoutes.nested_destination
        ) {

            /** should we add start destination argument here as well ?? */
//            argument(NavArguments.plant_id) {
//                type = NavType.StringType
//            }
//            argument(NavArguments.plant_name) {
//                type = NavType.StringType
//                nullable = true
//            }

            /** how should the rout look lite to support argument ?? */
            fragment<NestedDestinationFragment>(
//                "${NavRoutes.nested_destination}/{${NavArguments.plant_id}}/{${NavArguments.plant_name}}"
                "${NavRoutes.nested_destination}"
            ) {
                label = "NestedDestinationFragment"
                argument(NavArguments.plant_id) {
                    type = NavType.StringType
                }
                argument(NavArguments.plant_name) {
                    type = NavType.StringType
                    nullable = true
                }
            }
        }
        return graph
    }

    /** it works*/
    private fun navigateViaDeeplink() {
        val deepLink = "${baseUri}/foo?name=bar"
        val uri = Uri.parse(deepLink)
        navController.navigate(uri)
    }

    /** not working with parameter.*/
    private fun navigateToNestedGraph(plantId: String, plantName: String) {
        navController.navigate("${NavRoutes.route001}")
//        navController.navigate("${NavRoutes.route001}/$plantId/$plantName")
//        navController.navigate(
//            "${NavRoutes.route001}",
//        )
    }

    /** it works*/
    private fun navigateToFragmentWithPrimitiveArgument(plantId: String, plantName: String) {
        navController.navigate("${NavRoutes.plant_detail}/$plantId/$plantName")
    }

    /** it works*/
    private fun navigateToFragmentWithCustomParameter() {
        val params = SearchParameters("rose", listOf("available", "new"))
        val searchArgument = Uri.encode(Json.encodeToString(params))
        navController.navigate("${NavRoutes.plant_search}/$searchArgument")
    }

    /** it works*/
    private fun navigateToActivityDestination() {
        val id = "2000"
        navController.navigate("${NavRoutes.detail_activity}/$id")
    }


}