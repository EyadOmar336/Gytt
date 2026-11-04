package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.AppLanguage
import com.example.ui.components.DnaDoubleHelixCanvas
import com.example.ui.components.GlassCard
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppNavDestination
import com.example.ui.viewmodel.GeneLabViewModel

@Composable
fun DashboardScreen(
    viewModel: GeneLabViewModel,
    modifier: Modifier = Modifier
) {
    val language by viewModel.language.collectAsState()
    val isAr = language == AppLanguage.ARABIC
    val lessonProgress by viewModel.lessonProgress.collectAsState()
    val experiments by viewModel.experiments.collectAsState()
    val quizAttempts by viewModel.quizAttempts.collectAsState()

    var showPrivacyDialog by remember { mutableStateOf(false) }

    val completedLessonsCount = lessonProgress.values.count { it.isCompleted }
    val avgScore = if (quizAttempts.isNotEmpty()) {
        (quizAttempts.map { (it.score.toFloat() / it.totalQuestions) * 100 }.average()).toInt()
    } else {
        0
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Biotechnology Banner
        item {
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("dashboard_hero_card"),
                glowing = true,
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.85f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = NeonCyan.copy(alpha = 0.15f),
                            modifier = Modifier.border(1.dp, NeonCyan.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                        ) {
                            Text(
                                text = if (isAr) "مختبر الأحياء الجزيئية والوراثة" else "Molecular & Genetic Lab",
                                color = NeonCyan,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = if (isAr) "استكشف أسرار الـ DNA والوراثة المندلية" else "Unravel the Code of Life & Inheritance",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 20.sp,
                                lineHeight = 26.sp
                            ),
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = if (isAr) "محاكاة تفاعلية لمربعات بانيت، الطفرات الجينية، ومساعد ذكاء اصطناعي متخصص."
                            else "Interactive Punnett simulations, DNA transcription, mutations, and AI assistant.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 13.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Button(
                            onClick = { viewModel.navigateTo(AppNavDestination.PUNNETT_SIMULATOR) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = NeonCyan,
                                contentColor = Color(0xFF00222B)
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("hero_start_experiment_button")
                        ) {
                            Icon(Icons.Default.Science, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isAr) "بدء تجربة التزاوج" else "Start Experiment",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }

                    // Mini DNA Visual Canvas
                    Box(
                        modifier = Modifier
                            .size(90.dp, 130.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF081226).copy(alpha = 0.5f))
                            .border(1.dp, NeonCyan.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                    ) {
                        DnaDoubleHelixCanvas(
                            modifier = Modifier.fillMaxSize(),
                            strandSequence = "ATGCGATAC"
                        )
                    }
                }
            }
        }

        // Student Stats Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard(
                    title = if (isAr) "الدروس المكتملة" else "Lessons Done",
                    value = "$completedLessonsCount / 9",
                    icon = Icons.Default.MenuBook,
                    tint = NeonCyan,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = if (isAr) "التجارب المحفوظة" else "Lab Records",
                    value = "${experiments.size}",
                    icon = Icons.Default.Biotech,
                    tint = ElectricViolet,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = if (isAr) "معدل الاختبارات" else "Quiz Average",
                    value = if (quizAttempts.isNotEmpty()) "$avgScore%" else "—",
                    icon = Icons.Default.EmojiEvents,
                    tint = BioEmerald,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Section Title: Interactive Lab Tools
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (isAr) "أدوات المختبر التفاعلية" else "Interactive Laboratory Tools",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = if (isAr) "5 أدوات علمية" else "5 Tools",
                    color = NeonCyan,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // Lab Tools Cards Grid
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    LabToolCard(
                        title = if (isAr) "مربع بانيت" else "Punnett Square",
                        desc = if (isAr) "محاكي التزاوج الأحادي والثنائي" else "Mono & Dihybrid cross solver",
                        icon = Icons.Default.GridOn,
                        tint = NeonCyan,
                        onClick = { viewModel.navigateTo(AppNavDestination.PUNNETT_SIMULATOR) },
                        modifier = Modifier.weight(1f)
                    )
                    LabToolCard(
                        title = if (isAr) "محلل الوراثة" else "Genetics Solver",
                        desc = if (isAr) "حل مسائل التوريث خطوة بخطوة" else "Step-by-step problem solver",
                        icon = Icons.Default.Calculate,
                        tint = ElectricViolet,
                        onClick = { viewModel.navigateTo(AppNavDestination.MENDELIAN_SOLVER) },
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    LabToolCard(
                        title = if (isAr) "مستكشف الـ DNA" else "DNA Explorer",
                        desc = if (isAr) "اللولب المزدوج وترجمة الشفرة" else "Double helix & codon translation",
                        icon = Icons.Default.Biotech,
                        tint = BioEmerald,
                        onClick = { viewModel.navigateTo(AppNavDestination.DNA_EXPLORER) },
                        modifier = Modifier.weight(1f)
                    )
                    LabToolCard(
                        title = if (isAr) "محاكي التزاوج" else "Breeding Lab",
                        desc = if (isAr) "محاكاة مونت كارلو واختبار χ²" else "Monte Carlo & Chi-square test",
                        icon = Icons.Default.Science,
                        tint = BioAmber,
                        onClick = { viewModel.navigateTo(AppNavDestination.EXPERIMENT_SIMULATOR) },
                        modifier = Modifier.weight(1f)
                    )
                }

                LabToolCard(
                    title = if (isAr) "مستكشف الطفرات الجينية" else "Genetic Mutation Explorer",
                    desc = if (isAr) "طفرات الاستبدال والإزاحة وتأثيرها على سلاسل البروتين" else "Substitutions, frameshifts & protein chain impact",
                    icon = Icons.Default.ChangeCircle,
                    tint = BioCoral,
                    onClick = { viewModel.navigateTo(AppNavDestination.MUTATION_EXPLORER) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // AI Assistant Quick Banner
        item {
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.navigateTo(AppNavDestination.AI_ASSISTANT) }
                    .testTag("dashboard_assistant_card"),
                backgroundColor = ElectricViolet.copy(alpha = 0.12f),
                borderColor = ElectricViolet.copy(alpha = 0.4f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(ElectricViolet.copy(alpha = 0.25f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Psychology,
                            contentDescription = null,
                            tint = ElectricViolet,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isAr) "المساعد الذكي GeneLab AI" else "GeneLab AI Assistant",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = if (isAr) "اسأل عن الـ DNA، حل المسائل، وتحليل الطفرات بدقة علمية"
                            else "Ask about DNA, solve problems, and analyze genetic traits",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Icon(
                        Icons.Default.ArrowForward,
                        contentDescription = null,
                        tint = ElectricViolet
                    )
                }
            }
        }

        // Learning & Quiz Quick Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                GlassCard(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { viewModel.navigateTo(AppNavDestination.LEARNING_CENTER) }
                        .testTag("dashboard_learning_card"),
                    backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
                ) {
                    Icon(Icons.Default.MenuBook, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (isAr) "مركز الدروس" else "Learning Center",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Text(
                        text = if (isAr) "9 دروس مع مسارات تدريبية ومصطلحات" else "9 structured lessons & glossary",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                GlassCard(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            viewModel.startNewQuiz()
                            viewModel.navigateTo(AppNavDestination.AI_QUIZ)
                        }
                        .testTag("dashboard_quiz_card"),
                    backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
                ) {
                    Icon(Icons.Default.Quiz, contentDescription = null, tint = BioEmerald, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (isAr) "اختبارات تفاعلية" else "Practice Quizzes",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Text(
                        text = if (isAr) "أسئلة ذكية مع مراجعة الإجابات الخاطئة" else "Instant explanations & reviews",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Footer & Privacy Notice Button
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(
                    onClick = { showPrivacyDialog = true },
                    modifier = Modifier.testTag("privacy_notice_button")
                ) {
                    Icon(Icons.Default.Security, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isAr) "إشعار الخصوصية ونظام التشغيل المحلي" else "Privacy Notice & Local Processing",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }

    if (showPrivacyDialog) {
        AlertDialog(
            onDismissRequest = { showPrivacyDialog = false },
            title = {
                Text(
                    text = if (isAr) "الخصوصية وبيئة العمل" else "Privacy & Local Operation",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = if (isAr)
                        "تطبيق GeneLab AI مصمم للأغراض التعليمية. يتم تخزين جميع بياناتك (الدروس المكتملة، نتائج الاختبارات، التجارب وسجل المحادثات) محلياً على جهازك باستخدام قاعدة بيانات Room المشفرة. إذا تم إدخال مفتاح Gemini API في لوحة الأسرار، يتم إرسال استفسارات الذكاء الاصطناعي بشكل آمن ومباشر دون وسيط. في حال عدم وجود مفتاح، يعمل التطبيق بكامل وظائفه عبر المحرك الحيوي المدمج GeneLab Biology Engine."
                    else
                        "GeneLab AI is designed for biology education. All student progress, quiz records, saved crosses, and chat history are securely stored locally on your device via Room Database. When a Gemini API key is configured, queries are sent directly over HTTPS. Without an API key, the app functions offline via the built-in GeneLab Biology Engine.",
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            },
            confirmButton = {
                TextButton(onClick = { showPrivacyDialog = false }) {
                    Text(if (isAr) "فهمت ذلك" else "Understood")
                }
            }
        )
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    icon: ImageVector,
    tint: Color,
    modifier: Modifier = Modifier
) {
    GlassCard(
        modifier = modifier,
        backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        borderColor = tint.copy(alpha = 0.25f)
    ) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = value,
            fontWeight = FontWeight.Black,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = title,
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1
        )
    }
}

@Composable
private fun LabToolCard(
    title: String,
    desc: String,
    icon: ImageVector,
    tint: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    GlassCard(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
        borderColor = tint.copy(alpha = 0.35f)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(tint.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(22.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(
                    text = desc,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    lineHeight = 14.sp
                )
            }
        }
    }
}
