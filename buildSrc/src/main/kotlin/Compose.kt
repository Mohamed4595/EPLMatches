object Compose {
    private const val activityComposeVersion = "1.7.1"
    const val activity = "androidx.activity:activity-compose:$activityComposeVersion"

    const val kotlinCompilerComposeVersion = "1.4.6"
    const val composeVersion = "1.4.0"
    const val ui = "androidx.compose.ui:ui:${this.composeVersion}"
    const val material = "androidx.compose.material:material:${this.composeVersion}"
    const val tooling = "androidx.compose.ui:ui-tooling:${this.composeVersion}"
    const val util= "androidx.compose.ui:ui-util:${this.composeVersion}"
    private const val navigationVersion = "2.5.3"
    const val navigation = "androidx.navigation:navigation-compose:$navigationVersion"

    private const val hiltNavigationComposeVersion = "1.0.0"
    const val hiltNavigation = "androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion"
}

object ComposeTest {
    const val uiTestJunit4 = "androidx.compose.ui:ui-test-junit4:${Compose.composeVersion}"
    const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:${Compose.composeVersion}"
}