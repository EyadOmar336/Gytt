package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.R
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
import com.example.ui.screens.web.WebViewScreen
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

            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val isWideScreen = maxWidth >= 720.dp

                if (isWideScreen) {
                    // DESKTOP & TABLET RESPONSIVE LAYOUT (Sidebar / NavigationRail + Wide Content Area)
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        // Permanent Desktop Sidebar
                        NavigationRail(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(220.dp)
                                .testTag("desktop_sidebar"),
                            containerColor = MaterialTheme.colorScheme.surface,
                            header = {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(46.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(
                                                Brush.linearGradient(
                                                    listOf(NeonCyan.copy(alpha = 0.2f), MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f))
                                                )
                                            )
                                            .border(1.dp, NeonCyan, RoundedCornerShape(12.dp)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.ic_genelab_logo),
                                            contentDescription = "GeneLab Logo",
                                            modifier = Modifier.size(32.dp).clip(CircleShape)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "GeneLab AI",
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 17.sp,
                                        color = NeonCyan
                                    )
                                    Text(
                                        text = if (language == AppLanguage.ARABIC) "مختبر الوراثة الذكي" else "AI Genetics Lab",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        ) {
                            val railDestinations = listOf(
                                Triple(AppNavDestination.DASHBOARD, Icons.Default.Dashboard, if (language == AppLanguage.ARABIC) "الرئيسية" else "Dashboard"),
                                Triple(AppNavDestination.PUNNETT_SIMULATOR, Icons.Default.GridOn, if (language == AppLanguage.ARABIC) "مربع بانيت" else "Punnett"),
                                Triple(AppNavDestination.MENDELIAN_SOLVER, Icons.Default.Calculate, if (language == AppLanguage.ARABIC) "محلل الوراثة" else "Solver"),
                                Triple(AppNavDestination.DNA_EXPLORER, Icons.Default.Biotech, if (language == AppLanguage.ARABIC) "مستكشف الـ DNA" else "DNA Explorer"),
                                Triple(AppNavDestination.EXPERIMENT_SIMULATOR, Icons.Default.Science, if (language == AppLanguage.ARABIC) "محاكي التزاوج" else "Breeding Lab"),
                                Triple(AppNavDestination.MUTATION_EXPLORER, Icons.Default.ChangeCircle, if (language == AppLanguage.ARABIC) "الطفرات الجينية" else "Mutations"),
                                Triple(AppNavDestination.LEARNING_CENTER, Icons.Default.MenuBook, if (language == AppLanguage.ARABIC) "مركز الدروس" else "Lessons"),
                                Triple(AppNavDestination.AI_QUIZ, Icons.Default.Quiz, if (language == AppLanguage.ARABIC) "الاختبارات" else "Quiz"),
                                Triple(AppNavDestination.AI_ASSISTANT, Icons.Default.Psychology, if (language == AppLanguage.ARABIC) "المساعد الذكي" else "AI Assistant"),
                                Triple(AppNavDestination.WEB_VIEW, Icons.Default.Language, if (language == AppLanguage.ARABIC) "الموقع التفاعلي" else "Web App View")
                            )

                            railDestinations.forEach { (dest, icon, label) ->
                                val isSelected = currentScreen == dest
                                NavigationRailItem(
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
                                            fontSize = 11.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                            color = if (isSelected) NeonCyan else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    },
                                    colors = NavigationRailItemDefaults.colors(
                                        indicatorColor = NeonCyan.copy(alpha = 0.2f)
                                    ),
                                    modifier = Modifier.padding(vertical = 2.dp)
                                )
                            }
                        }

                        // Wide Screen Main Content
                        Scaffold(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            topBar = {
                                GeneticsTopBar(
                                    title = when (currentScreen) {
                                        AppNavDestination.DASHBOARD -> if (language == AppLanguage.ARABIC) "لوحة التحكم" else "Dashboard"
                                        AppNavDestination.PUNNETT_SIMULATOR -> if (language == AppLanguage.ARABIC) "مربع بانيت" else "Punnett Square"
                                        AppNavDestination.MENDELIAN_SOLVER -> if (language == AppLanguage.ARABIC) "محلل الوراثة" else "Genetics Solver"
                                        AppNavDestination.DNA_EXPLORER -> if (language == AppLanguage.ARABIC) "مستكشف الـ DNA" else "DNA Explorer"
                                        AppNavDestination.EXPERIMENT_SIMULATOR -> if (language == AppLanguage.ARABIC) "محاكي التزاوج" else "Breeding Lab"
                                        AppNavDestination.MUTATION_EXPLORER -> if (language == AppLanguage.ARABIC) "مستكشف الطفرات" else "Mutation Explorer"
                                        AppNavDestination.LEARNING_CENTER -> if (language == AppLanguage.ARABIC) "مركز الدروس" else "Learning Center"
                                        AppNavDestination.AI_QUIZ -> if (language == AppLanguage.ARABIC) "الاختبارات الذكية" else "AI Quiz Practice"
                                        AppNavDestination.AI_ASSISTANT -> if (language == AppLanguage.ARABIC) "المساعد الوراثي" else "Genetics Assistant"
                                        AppNavDestination.WEB_VIEW -> if (language == AppLanguage.ARABIC) "موقع الويب التفاعلي المتجاوب" else "Responsive Web App View"
                                        else -> "GeneLab AI"
                                    },
                                    currentLanguage = language,
                                    onLanguageToggle = { viewModel.toggleLanguage() },
                                    darkTheme = isDarkTheme,
                                    onThemeToggle = { viewModel.toggleTheme() },
                                    onInfoClick = { showAboutDialog = true }
                                )
                            },
                            snackbarHost = { SnackbarHost(snackbarHostState) },
                            contentWindowInsets = WindowInsets.safeDrawing
                        ) { innerPadding ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding)
                                    .background(MaterialTheme.colorScheme.background)
                            ) {
                                ScreenContent(currentScreen = currentScreen, viewModel = viewModel)
                            }
                        }
                    }
                } else {
                    // COMPACT MOBILE LAYOUT (Bottom Bar + Mobile Header)
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
                                AppNavDestination.WEB_VIEW -> if (language == AppLanguage.ARABIC) "الموقع التفاعلي" else "Web App View"
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
                                    Triple(AppNavDestination.AI_ASSISTANT, Icons.Default.Psychology, if (language == AppLanguage.ARABIC) "المساعد" else "AI"),
                                    Triple(AppNavDestination.WEB_VIEW, Icons.Default.Language, if (language == AppLanguage.ARABIC) "الموقع" else "Web")
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
                            ScreenContent(currentScreen = currentScreen, viewModel = viewModel)
                        }
                    }
                }
            }

            if (showAboutDialog) {
                AlertDialog(
                    onDismissRequest = { showAboutDialog = false },
                    title = {
                        Text(
                            text = if (language == AppLanguage.ARABIC) "عن تطبيق GeneLab AI المتجاوب" else "About Responsive GeneLab AI",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    text = {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = if (language == AppLanguage.ARABIC)
                                    "GeneLab AI يدعم الآن التصميم المتجاوب بالكامل:\n\n" +
                                            "• نمط الشاشات الكبيرة (Desktop/Tablet): شريط جانبي دائم وتنسيق متعدد الأعمدة.\n" +
                                            "• نمط الهواتف الذكية (Mobile): شريط ملاحة سفلي وتجربة مريحة بيد واحدة.\n" +
                                            "• نمط موقع الويب التفاعلي (Web App View): نسخة ويب متجاوبة كاملة يمكن تصفحها داخل التطبيق أو نشرها على أي استضافة خارجية من مجلد /web/."
                                else
                                    "GeneLab AI now supports full responsive adaptive design:\n\n" +
                                            "• Expanded Screens (Desktop/Tablet): Persistent navigation sidebar and multi-column view.\n" +
                                            "• Compact Mobile: Ergonomic bottom navigation.\n" +
                                            "• Web App View: Standalone responsive web app viewable inside the app or deployable to any web host from /web/ folder.",
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

@Composable
private fun ScreenContent(
    currentScreen: AppNavDestination,
    viewModel: GeneLabViewModel
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
        AppNavDestination.WEB_VIEW -> WebViewScreen()
        else -> DashboardScreen(viewModel = viewModel)
    }
}
