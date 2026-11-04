package com.example.ui.screens.learning

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.content.CuratedGeneticsData
import com.example.data.model.AppLanguage
import com.example.data.model.GlossaryTerm
import com.example.data.model.Lesson
import com.example.ui.components.GlassCard
import com.example.ui.theme.*
import com.example.ui.viewmodel.GeneLabViewModel

@Composable
fun LearningCenterScreen(
    viewModel: GeneLabViewModel,
    modifier: Modifier = Modifier
) {
    val language by viewModel.language.collectAsState()
    val isAr = language == AppLanguage.ARABIC

    val progressMap by viewModel.lessonProgress.collectAsState()
    val selectedLesson by viewModel.selectedLesson.collectAsState()

    var activeTab by remember { mutableStateOf(0) } // 0: Lessons, 1: Glossary
    var selectedTrack by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }
    var glossaryCategory by remember { mutableStateOf("All") }

    val allLessons = CuratedGeneticsData.LESSONS
    val filteredLessons = remember(selectedTrack) {
        if (selectedTrack == "All") allLessons
        else allLessons.filter { it.track.equals(selectedTrack, ignoreCase = true) }
    }

    val allGlossary = CuratedGeneticsData.GLOSSARY
    val filteredGlossary = remember(searchQuery, glossaryCategory) {
        allGlossary.filter { term ->
            val matchesCategory = glossaryCategory == "All" || term.category.equals(glossaryCategory, ignoreCase = true)
            val matchesSearch = searchQuery.isBlank() ||
                    term.termEn.contains(searchQuery, ignoreCase = true) ||
                    term.termAr.contains(searchQuery, ignoreCase = true) ||
                    term.definitionEn.contains(searchQuery, ignoreCase = true) ||
                    term.definitionAr.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Tab Bar: Lessons vs Glossary
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { activeTab = 0 }
                        .testTag("tab_learning_lessons"),
                    color = if (activeTab == 0) NeonCyan.copy(alpha = 0.2f) else Color.Transparent
                ) {
                    Text(
                        text = if (isAr) "مسارات الدروس العلمية" else "Curriculum Lessons",
                        modifier = Modifier.padding(vertical = 10.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        fontWeight = if (activeTab == 0) FontWeight.Bold else FontWeight.Normal,
                        color = if (activeTab == 0) NeonCyan else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 13.sp
                    )
                }

                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { activeTab = 1 }
                        .testTag("tab_learning_glossary"),
                    color = if (activeTab == 1) ElectricViolet.copy(alpha = 0.2f) else Color.Transparent
                ) {
                    Text(
                        text = if (isAr) "معجم المصطلحات (Glossary)" else "Scientific Glossary",
                        modifier = Modifier.padding(vertical = 10.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        fontWeight = if (activeTab == 1) FontWeight.Bold else FontWeight.Normal,
                        color = if (activeTab == 1) ElectricViolet else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 13.sp
                    )
                }
            }
        }

        if (activeTab == 0) {
            // Track Filter Chips
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(
                        "All" to (if (isAr) "الكل" else "All"),
                        "Beginner" to (if (isAr) "مبتدئ" else "Beginner"),
                        "Intermediate" to (if (isAr) "متوسط" else "Intermediate"),
                        "Advanced" to (if (isAr) "متقدم" else "Advanced")
                    ).forEach { (trackKey, trackLabel) ->
                        val isSelected = selectedTrack == trackKey
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) NeonCyan.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    1.dp,
                                    if (isSelected) NeonCyan else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { selectedTrack = trackKey }
                        ) {
                            Text(
                                text = trackLabel,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) NeonCyan else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }

            // Lessons List
            items(filteredLessons) { lesson ->
                val isCompleted = progressMap[lesson.id]?.isCompleted == true
                LessonCard(
                    lesson = lesson,
                    isCompleted = isCompleted,
                    isAr = isAr,
                    onClick = { viewModel.selectedLesson.value = lesson }
                )
            }
        } else {
            // Glossary Tab: Search & Category Filter
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(if (isAr) "ابحث عن مصطلح علمي أو رمز..." else "Search terms or definitions...")
                    },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth().testTag("glossary_search_input"),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }

            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(listOf("All", "Molecular", "Classical", "Cellular", "Biotech")) { cat ->
                        val isSelected = glossaryCategory == cat
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) ElectricViolet.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    1.dp,
                                    if (isSelected) ElectricViolet else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { glossaryCategory = cat }
                        ) {
                            Text(
                                text = cat,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) ElectricViolet else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }

            items(filteredGlossary) { term ->
                GlossaryTermCard(term = term, isAr = isAr)
            }
        }
    }

    // Lesson Detail & Knowledge Check Dialog
    selectedLesson?.let { lesson ->
        LessonReaderDialog(
            lesson = lesson,
            isAr = isAr,
            onDismiss = { viewModel.selectedLesson.value = null },
            onComplete = {
                viewModel.completeLesson(lesson)
                viewModel.selectedLesson.value = null
            }
        )
    }
}

@Composable
private fun LessonCard(
    lesson: Lesson,
    isCompleted: Boolean,
    isAr: Boolean,
    onClick: () -> Unit
) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .testTag("lesson_card_${lesson.id}"),
        backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isCompleted) BioEmerald.copy(alpha = 0.2f) else NeonCyan.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Default.MenuBook,
                    contentDescription = null,
                    tint = if (isCompleted) BioEmerald else NeonCyan,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = MaterialTheme.colorScheme.surface
                    ) {
                        Text(
                            text = if (isAr) lesson.trackAr else lesson.track,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeonCyan,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "• ${lesson.durationMin} ${if (isAr) "دقائق" else "min"}",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = if (isAr) lesson.titleAr else lesson.titleEn,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = if (isAr) lesson.summaryAr else lesson.summaryEn,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    lineHeight = 15.sp
                )
            }

            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun GlossaryTermCard(
    term: GlossaryTerm,
    isAr: Boolean
) {
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = if (isAr) term.termAr else term.termEn,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = NeonCyan
                )
                if (isAr) {
                    Text(
                        text = term.termEn,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(6.dp),
                color = ElectricViolet.copy(alpha = 0.15f),
                modifier = Modifier.border(1.dp, ElectricViolet.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
            ) {
                Text(
                    text = term.category,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = ElectricViolet,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = if (isAr) term.definitionAr else term.definitionEn,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onBackground,
            lineHeight = 17.sp
        )

        term.formulaOrSymbol?.let { formula ->
            Spacer(modifier = Modifier.height(6.dp))
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = MaterialTheme.colorScheme.surface
            ) {
                Text(
                    text = formula,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BioEmerald,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun LessonReaderDialog(
    lesson: Lesson,
    isAr: Boolean,
    onDismiss: () -> Unit,
    onComplete: () -> Unit
) {
    var checkAnswerSelected by remember { mutableStateOf<Int?>(null) }
    var isAnswerEvaluated by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text(
                    text = if (isAr) lesson.titleAr else lesson.titleEn,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
                Text(
                    text = if (isAr) "المسار: ${lesson.trackAr} • ${lesson.durationMin} دقائق" else "Track: ${lesson.track} • ${lesson.durationMin} min",
                    fontSize = 12.sp,
                    color = NeonCyan
                )
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().heightIn(max = 420.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(lesson.sections) { sec ->
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = if (isAr) sec.headingAr else sec.headingEn,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = NeonCyan
                        )
                        Text(
                            text = if (isAr) sec.contentAr else sec.contentEn,
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        sec.keyTakeawayEn?.let {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surface,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.Lightbulb, contentDescription = null, tint = BioAmber, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = if (isAr) sec.keyTakeawayAr ?: "" else sec.keyTakeawayEn,
                                        fontSize = 11.sp,
                                        color = BioAmber,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }
                }

                // Knowledge Check Mini-Quiz
                if (lesson.quickQuiz.isNotEmpty()) {
                    val q = lesson.quickQuiz[0]
                    item {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = if (isAr) "اختبار فهم سريع لهذا الدرس:" else "Quick Knowledge Check:",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = ElectricViolet
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = if (isAr) q.questionAr else q.questionEn,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                val options = if (isAr) q.optionsAr else q.optionsEn
                                options.forEachIndexed { idx, opt ->
                                    val isSelected = checkAnswerSelected == idx
                                    val isCorrect = idx == q.correctIndex
                                    val bg = when {
                                        isAnswerEvaluated && isCorrect -> BioEmerald.copy(alpha = 0.25f)
                                        isAnswerEvaluated && isSelected -> BioCoral.copy(alpha = 0.25f)
                                        isSelected -> NeonCyan.copy(alpha = 0.2f)
                                        else -> MaterialTheme.colorScheme.surface
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = bg,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 3.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .clickable {
                                                if (!isAnswerEvaluated) checkAnswerSelected = idx
                                            }
                                    ) {
                                        Text(
                                            text = opt,
                                            fontSize = 11.sp,
                                            modifier = Modifier.padding(8.dp)
                                        )
                                    }
                                }

                                if (!isAnswerEvaluated) {
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Button(
                                        onClick = { isAnswerEvaluated = true },
                                        enabled = checkAnswerSelected != null,
                                        colors = ButtonDefaults.buttonColors(containerColor = ElectricViolet),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(if (isAr) "تحقق من الإجابة" else "Check Answer", fontSize = 11.sp)
                                    }
                                } else {
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = if (isAr) q.explanationAr else q.explanationEn,
                                        fontSize = 11.sp,
                                        color = BioEmerald
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onComplete,
                colors = ButtonDefaults.buttonColors(containerColor = BioEmerald)
            ) {
                Text(if (isAr) "إتمام الدرس وتسجيل النقاط" else "Complete Lesson", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(if (isAr) "إغلاق" else "Close")
            }
        }
    )
}
