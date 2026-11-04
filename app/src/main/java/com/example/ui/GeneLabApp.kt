package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.AppLanguage
import com.example.ui.components.GeneticsTopBar
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.assistant.AssistantScreen
import com.example.ui.screens.dna.DnaExplorerScreen
import com.example.ui.screens.learning.LearningCenterScreen
import com.example.ui.screens.mutation.MutationExplorerScreen
import com.example.ui.screens.punnett.PunnettSimulatorScreen
import com.example.ui.screens.quiz.QuizScreen
import com.example.ui.screens.simulation.ExperimentSimulatorScreen
import com.example.ui.screens.solver.MendelianSolverScreen
import com.example.ui.theme.BioEmerald
import com.example.ui.theme.GeneLabTheme
import com.example.ui.theme.NeonCyan
import com.example.ui.viewmodel.AppNavDestination
import com.example.ui.viewmodel.GeneLabViewModel

@Composable
fun GeneLabApp(
    viewModel: GeneLabViewModel = viewModel()
) {
    val language by viewModel.language.collectAsState()
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    val currentScreen by viewModel.currentScreen.collectAsState()
    val notification by viewModel.activeNotification.collectAsState()

    var showAboutDialog by remember { mutableStateOf(false) }

    // RTL for Arabic, LTR for English
    val layoutDirection = if (language == AppLanguage.ARABIC) LayoutDirection.Rtl else LayoutDirection.Ltr

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        GeneLabTheme(darkTheme = isDarkTheme) {
            // Android Back button handling
            BackHandler(enabled = currentScreen != AppNavDestination.DASHBOARD) {
                viewModel.navigateBack()
            }

            val snackbarHostState = remember { SnackbarHostState() }

            LaunchedEffect(notification) {
                notification?.let { n ->
                    snackbarHostState.showSnackbar(n.message)
                    viewModel.clearNotification()
                }
            }

            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                topBar = {
                    val title = when (currentScreen) {
                        AppNavDestination.DASHBOARD -> if (language == AppLanguage.ARABIC) "مختبر جين لآب" else "GeneLab AI"
                        AppNavDestination.PUNNETT_SIMULATOR -> if (language == AppLanguage.ARABIC) "مربع بانيت" else "Punnett Square"
                        AppNavDestination.MENDELIAN_SOLVER -> if (language == AppLanguage.ARABIC) "محلل الوراثة" else "Genetics Solver"
                        AppNavDestination.DNA_EXPLORER -> if (language == AppLanguage.ARABIC) "مستكشف الـ DNA" else "DNA Explorer"
                        AppNavDestination.EXPERIMENT_SIMULATOR -> if (language == AppLanguage.ARABIC) "محاكي التزاوج" else "Breeding Lab"
                        AppNavDestination.MUTATION_EXPLORER -> if (language == AppLanguage.ARABIC) "مستكشف الطفرات" else "Mutation Explorer"
                        AppNavDestination.LEARNING_CENTER -> if (language == AppLanguage.ARABIC) "مركز الدروس" else "Learning Center"
                        AppNavDestination.AI_QUIZ -> if (language == AppLanguage.ARABIC) "الاختبارات الذكية" else "AI Quiz Practice"
                        AppNavDestination.AI_ASSISTANT -> if (language == AppLanguage.ARABIC) "المساعد الوراثي" else "Genetics Assistant"
                        else -> "GeneLab AI"
                    }

                    GeneticsTopBar(
                        title = title,
                        currentLanguage = language,
                        onLanguageToggle = { viewModel.toggleLanguage() },
                        darkTheme = isDarkTheme,
                        onThemeToggle = { viewModel.toggleTheme() },
                        onInfoClick = { showAboutDialog = true }
                    )
                },
                bottomBar = {
                    NavigationBar(
                        modifier = Modifier
                            .navigationBarsPadding()
                            .testTag("main_bottom_nav"),
                        containerColor = MaterialTheme.colorScheme.surface,
                        tonalElevation = 8.dp
                    ) {
                        val navItems = listOf(
                            Triple(AppNavDestination.DASHBOARD, Icons.Default.Dashboard, if (language == AppLanguage.ARABIC) "الرئيسية" else "Dashboard"),
                            Triple(AppNavDestination.PUNNETT_SIMULATOR, Icons.Default.GridOn, if (language == AppLanguage.ARABIC) "بانيت" else "Punnett"),
                            Triple(AppNavDestination.DNA_EXPLORER, Icons.Default.Biotech, if (language == AppLanguage.ARABIC) "DNA" else "DNA"),
                            Triple(AppNavDestination.LEARNING_CENTER, Icons.Default.MenuBook, if (language == AppLanguage.ARABIC) "الدروس" else "Lessons"),
                            Triple(AppNavDestination.AI_ASSISTANT, Icons.Default.Psychology, if (language == AppLanguage.ARABIC) "المساعد" else "AI")
                        )

                        navItems.forEach { (dest, icon, label) ->
                            val isSelected = currentScreen == dest
                            NavigationBarItem(
                                selected = isSelected,
                                onClick = { viewModel.navigateTo(dest) },
                                icon = {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = label,
                                        tint = if (isSelected) NeonCyan else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                },
                                label = {
                                    Text(
                                        text = label,
                                        fontSize = 10.sp,
                                        color = if (isSelected) NeonCyan else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = NeonCyan.copy(alpha = 0.2f)
                                ),
                                modifier = Modifier.testTag("nav_item_${dest.name.lowercase()}")
                            )
                        }
                    }
                },
                snackbarHost = { SnackbarHost(snackbarHostState) },
                contentWindowInsets = WindowInsets.safeDrawing
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    when (currentScreen) {
                        AppNavDestination.DASHBOARD -> DashboardScreen(viewModel = viewModel)
                        AppNavDestination.PUNNETT_SIMULATOR -> PunnettSimulatorScreen(viewModel = viewModel)
                        AppNavDestination.MENDELIAN_SOLVER -> MendelianSolverScreen(viewModel = viewModel)
                        AppNavDestination.DNA_EXPLORER -> DnaExplorerScreen(viewModel = viewModel)
                        AppNavDestination.EXPERIMENT_SIMULATOR -> ExperimentSimulatorScreen(viewModel = viewModel)
                        AppNavDestination.MUTATION_EXPLORER -> MutationExplorerScreen(viewModel = viewModel)
                        AppNavDestination.LEARNING_CENTER -> LearningCenterScreen(viewModel = viewModel)
                        AppNavDestination.AI_QUIZ -> QuizScreen(viewModel = viewModel)
                        AppNavDestination.AI_ASSISTANT -> AssistantScreen(viewModel = viewModel)
                        else -> DashboardScreen(viewModel = viewModel)
                    }
                }
            }

            if (showAboutDialog) {
                AlertDialog(
                    onDismissRequest = { showAboutDialog = false },
                    title = {
                        Text(
                            text = if (language == AppLanguage.ARABIC) "عن تطبيق GeneLab AI" else "About GeneLab AI",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    text = {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = if (language == AppLanguage.ARABIC)
                                    "GeneLab AI هو منصة تعليمية تفاعلية لعلم الوراثة والأحياء الجزيئية.\n\n" +
                                            "الميزات الرئيسية:\n" +
                                            "• محاكي بانيت للتزاوج الأحادي والثنائي مع النسب المئوية\n" +
                                            "• مستكشف الـ DNA اللولبي وترجمة الكودونات إلى أحماض أمينية\n" +
                                            "• محاكي التزاوج العشوائي مع اختبار كاي تربيع الإحصائي\n" +
                                            "• مستكشف الطفرات وتأثيرها على سلاسل البروتين\n" +
                                            "• مساعد ذكاء اصطناعي ثنائي اللغة (عربي / إنجليزي)\n" +
                                            "• بنك أسئلة واختبارات تفاعلية ومعجم مصطلحات علمية"
                                else
                                    "GeneLab AI is an interactive educational genetics and molecular biology platform.\n\n" +
                                            "Key Capabilities:\n" +
                                            "• Monohybrid & Dihybrid Punnett Square simulator with step-by-step logic\n" +
                                            "• Double Helix DNA Explorer with transcription & amino acid translation\n" +
                                            "• Stochastic virtual breeding lab with Chi-Square goodness-of-fit\n" +
                                            "• Mutation Explorer with silent, missense, nonsense & frameshifts\n" +
                                            "• Bilingual AI Assistant with offline biology fallback engine\n" +
                                            "• Structured curriculum lessons & comprehensive scientific glossary",
                                fontSize = 12.sp,
                                lineHeight = 18.sp
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = { showAboutDialog = false }) {
                            Text(if (language == AppLanguage.ARABIC) "إغلاق" else "Close")
                        }
                    }
                )
            }
        }
    }
}
